package com.jts.gangstudy.controller;



import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.IamportService;

import com.jts.gangstudy.service.KakaoPayService;
import com.jts.gangstudy.service.PaymentService;
import com.jts.gangstudy.service.UserService;





@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private KakaoPayService kakaoPayService;
	@Autowired
	private IamportService iamportService;

	// 예약 취소 요청
	@UserLoginCheck
	@ResponseBody   
	@RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public String cancel(HttpServletRequest request, HttpSession session, @RequestParam("book_no") int book_no) {
		User user = (User) session.getAttribute("sUserId");
		final String cancelMessage = "예약이 취소되었습니다.";
		
		// 잘못된 경로로 요청된 예약에 대한 예외처리
		Booking book = bookingService.searchByBookNo(book_no);
		if (book == null) {
			return "해당하는 예약이 없습니다.";
		} else if (!book.getState().equals("wait")) {
			return "취소 불가능한 예약입니다.";
		} else if (book.getUser_no() != user.getUser_no()) {
			return "잘못된 예약 요청입니다.";
		}
		boolean canCancel = LocalDateTime.now().plusDays(1).isBefore(book.getCheck_in());
		if (!canCancel) {
			return "24시간 이내에는 예약을 취소할 수 없습니다.";
		}

		// 결제 취소 처리
		Payment payment = paymentService.selectPayment(book);
		String tid = payment.getTid();
		Integer cancel_amount = payment.getAmount();
		
		if (payment.getPg_name().equals("KakaoPay")) { 	// 카카오페이
			HashMap<String, String> map = kakaoPayService.cancel(tid, cancel_amount.toString());
			if (map == null) {
				return "결제 취소 오류(카카오페이)";
			}
			if (map.get("status").equals("PART_CANCEL_PAYMENT") || map.get("status").equals("CANCEL_PAYMENT")) {
				// 이전 결제 정보 취소 처리
				paymentService.changeState(payment, "cancelled");
				// 기존 예약을 취소로 변경
				bookingService.changeState(book, "cancel");
				return cancelMessage;
			}
			System.out.println(" # 카카오페이 결제 환불 되지 않는 이유:  " + map.get("status"));
			return "결제 취소 실패(카카오페이)";
		} else {										// 다날
			HashMap<String, String> map = iamportService.cancel(tid, cancel_amount.toString());
			if (map == null) {
				System.out.println("결제 취소 오류(아임포트)");
			} else {
				if (map.get("code").equals("0")) {
					// 이전 결제 정보 취소 처리
					paymentService.changeState(payment, "cancelled");
					// 기존 예약을 취소로 변경
					bookingService.changeState(book, "cancel");
					return cancelMessage;
				}
			}
			// 코드가 0이 아니면 message 확인
			System.out.println(" # 아임포트 결제 환불 되지 않는 이유:  " + map.get("message"));
			return "결제 취소 실패(아임포트)";
		}
	}
		
		
	// KakaoPay를 통한 결제 요청
	@UserLoginCheck
	@RequestMapping(value = "/kakaopay", method = RequestMethod.GET)
	public String kakaopay(HttpServletRequest request, HttpSession session) {
		Booking book = (Booking)session.getAttribute("book");		// 사용한 포인트
		int amount = (int)session.getAttribute("amount");			// 결제 비용
		int usePoint = (int)session.getAttribute("usePoint");		// 사용한 포인트
		String deviceType = paymentService.getDeviceType(request);	// 결제 요청 장비
		
		HashMap<String, String> map = kakaoPayService.ready(deviceType, amount-usePoint, book.getBook_no());
		if(map == null) {
			return "?ready=fail";
		}
		session.setAttribute("tid", map.get("tid"));
		session.setAttribute("book_no", book.getBook_no());
		session.removeAttribute("book");
		return "redirect:" + map.get("url");
	}

	// KakaoPay에서 결제 준비상태. 결제 처리하기
	@UserLoginCheck
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public String complete(HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("sUserId");
		String tid = (String)session.getAttribute("tid");
		int book_no = (int)session.getAttribute("book_no");
		int amount = (int)session.getAttribute("amount");
		int usePoint = (int)session.getAttribute("usePoint");
		String pg_token = request.getParameter("pg_token");
		
		Booking book = bookingService.searchByBookNo(book_no);
		
		// 대기중인 예약이 존재하지 않는 경우(
		if(book == null) {
			return "redirect:" + "/?payment=BookIsNull";
		}
		
		// 결제 완료 요청
		HashMap<String, String> payInfo = kakaoPayService.getPayInfo(tid, pg_token);

		// 결제 정보를 저장한다.
		Payment payment = new Payment();
		payment.setAmount(amount-usePoint);
		payment.setPoint(usePoint);
		payment.setPg_name("KakaoPay");
		payment.setTid(tid);
		payment.setPay_type(payInfo.get("pay_type"));
		payment.setState("paid");
		payment.setBook_no(book.getBook_no());
		paymentService.insertPayment(payment);
		
		userService.minusPoints(user, usePoint);

		session.setAttribute("book", book);
		session.removeAttribute("tid");
		session.removeAttribute("book_no");
		session.removeAttribute("usePoint");
		session.removeAttribute("amount");
		
		// 완료 페이지로 이동한다.
		return "redirect:" + "/booking/complete";
	}
	@UserLoginCheck
	@RequestMapping(value = "/beready", method = RequestMethod.GET)
	public String beready( HttpServletRequest request, HttpSession session ) {
		//,@RequestParam("imp_uid") String imp_uid  아임포트 고유번호 불러오고싶을때 파라미터에넣기 
		User user = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");
		/* String tid = (String)session.getAttribute("merchant_uid"); */
		int amount = (int)session.getAttribute("amount");
		int usePoint = (int)session.getAttribute("usePoint");
	
		
		
		// 대기중인 예약 시간초과 예외처리
		if(bookingService.searchByBookNo(book.getBook_no()) == null) {
			
			return "redirect:" + "/?payment=timeout";
		}
		int bookno = book.getBook_no();
		String book_No = String.valueOf(bookno);
		
		// 결제 완료 요청    
		
		// 결제 정보를 저장한다.
		Payment payment = new Payment();
		payment.setAmount(amount-usePoint);
		payment.setPoint(usePoint);
		payment.setPg_name("Danal");
	//	payment.setTid(imp_uid); // imp_uid로 할때 
		payment.setTid(book_No); // merchant_uid 지금 cancel할때 merchant_uid로 불러오기해놓음. iamportServiceimpl.cancel
		payment.setPay_type("card");
		payment.setState("paid");
		payment.setBook_no(book.getBook_no());
		paymentService.insertPayment(payment);
		
		userService.minusPoints(user, usePoint);
		
		session.removeAttribute("tid");
		session.removeAttribute("usePoint");
		session.removeAttribute("amount");
		
		// 완료 페이지로 이동한다.
		System.out.println("2차 페이매소드 ");
		return "redirect:" + "/booking/complete";
	}
	

	// kakaopay실패 url
	@RequestMapping(value = "/fail", method = RequestMethod.GET)
	public ModelAndView fail(HttpServletRequest request, HttpSession session) {
		Booking book = (Booking)session.getAttribute("book");
		bookingService.removeBook(book);
		ModelAndView mav = new ModelAndView("redirect:" + "/booking/make");
		System.out.println("test : " + book.getCheck_in().toLocalDate());
		mav.addObject("startDateInput", book.getCheck_in().toLocalDate().toString());
		mav.addObject("startTimeInput", book.getCheck_in().toLocalTime().toString());
		mav.addObject("endDateInput", book.getCheck_out().toLocalDate().toString());
		mav.addObject("endTimeInput", book.getCheck_out().toLocalTime().toString());
		mav.addObject("people", book.getPeople());
		mav.addObject("msg", "결제가 취소되었습니다.");
		session.removeAttribute("book");
		session.removeAttribute("tid");
		session.removeAttribute("usePoint");
		session.removeAttribute("amount");
		return mav;
	}
	
	// 전액 취소 후 다시 예약 에서 취소 단계
	// 기존 예약에 대한 취소 처리
	@RequestMapping(value = "/cancelAndBooking", method = RequestMethod.GET)
	public String cancelAndBooking(HttpServletRequest request, HttpSession session) {
		Booking oldBook = (Booking)session.getAttribute("oldBook");
		Booking newBook = (Booking)session.getAttribute("newBook");
		int usePoint = (int)session.getAttribute("usePoint");
		
		Payment payment = paymentService.selectPayment(oldBook);
		String tid = payment.getTid();
		Integer paidMoney = payment.getAmount();
		
		HashMap<String, String> map = kakaoPayService.cancel(tid, paidMoney.toString());	// 전액 취소 요청

		if(map==null) {
			return "redirect:" + "/?cancelAndBooking=fail";
		}
		if(map.get("status").equals("CANCEL_PAYMENT")) {									// 전액 취소 성공
			// 이전 결제 정보 취소 처리
			paymentService.changeState(payment, "cancelled");
			// 기존 예약을 취소로 변경
			bookingService.changeState(oldBook, "cancel");
			String result = bookingService.insertBook(newBook);
			if(result.equals("duplicate")) {
				return "?booking=duplicate";
			}
			return "redirect:" + "/booking/cancelAndBooking";
		}
		return "redirect:" + "/?cancelAndBooking=fail";
		
	}
	
	// 차액 취소 후 예약변경 에서 취소 단계
	// 기존 예약에 대한 취소 처리
	@RequestMapping(value = "/cancelAndChange", method = RequestMethod.GET)
	public String cancelAndChange(HttpServletRequest request, HttpSession session) {
		Booking oldBook = (Booking)session.getAttribute("oldBook");
		Booking newBook = (Booking)session.getAttribute("newBook");
		Integer amount = -(int)session.getAttribute("amount");
		Integer usePoint = (int)session.getAttribute("usePoint");
		System.out.println("is null? " + newBook);
		String tid = paymentService.selectPayment(oldBook).getTid();
		
		HashMap<String, String> map = kakaoPayService.cancel(tid, amount.toString());		// 차액 취소 요청
		if(map==null) {
			return "redirect:" + "/?cancelAndChange=fail";
		}
		if(map.get("status").equals("PART_CANCEL_PAYMENT") ||
		   map.get("status").equals("CANCEL_PAYMENT")) {									// 차액 취소 성공
			// 이전 결제 정보 취소 처리
			paymentService.changeState(paymentService.selectPayment(oldBook), "cancelled");
			
			// 해당 결제 정보 저장
			Payment payment = new Payment();
			payment.setAmount(bookingService.getAmount(newBook));
			payment.setPoint(0); // 취소시 사용된 포인트를 돌려줘야 할지도?
			payment.setPg_name("KakaoPay");
			payment.setTid(tid);
			payment.setPay_type(map.get("pay_type"));
			payment.setState("paid");
			session.setAttribute("payment", payment);
			
			return "redirect:" + "/booking/cancelAndChange";
		}
		
		return "redirect:" + "?cancelAndChange=fail";
	}
}