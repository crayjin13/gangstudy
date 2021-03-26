package com.jts.gangstudy.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking.State;
import com.jts.gangstudy.service.AdminService;
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.PaymentService;
import com.jts.gangstudy.service.UserService;


@Controller
@RequestMapping("/booking")
public class BookingController {
	private final int room_no = 1;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private PaymentService paymentService;

	// 예약가능한 시작시간
	@ResponseBody
	@RequestMapping(value = "/startTime", method = RequestMethod.GET)
	public List<String> startTime(HttpServletRequest request, HttpSession session,
								@RequestParam("book_no") Integer book_no, @RequestParam("date") String dateParam) {
		LocalDate date = LocalDate.parse(dateParam);
		// 요청한 날짜를 KST로 변환
		LocalDateTime ldt = LocalDateTime.of(date, LocalTime.MIN);
		// 해당일에 존재하는 예약목록
		List<Booking> books = bookingService.selectByDateTime(ldt);
		// 요청한 book_no가 있었다면 리스트에서 제거
		if(book_no != null) { books.removeIf(obj -> obj.getBook_no() == book_no); }
		// 예약된 시간을 제외한 시간목록 생성
		List<String> times = bookingService.getStartTimes(date, books);
		return times;
	}

	// 예약가능한 종료시간
	@ResponseBody
	@RequestMapping(value = "/endTime", method = RequestMethod.GET)
	public List<String> endTime(HttpServletRequest request, HttpSession session,
							@RequestParam("book_no") Integer book_no, @RequestParam("date") String dateParam, @RequestParam("startTime") String timeParam) {
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(dateParam), LocalTime.parse(timeParam));
		// book_no를 제외한 해당일에 존재하는 다음 예약
		Booking nextBook = bookingService.searchNextBook(dateTime, book_no);
		// 최대로 선택가능한 시간목록 생성
		List<String> times = bookingService.getEndTimes(dateTime, nextBook);
		return times;
	}
	
	@ResponseBody
	@RequestMapping(value = "/submitBook", method = RequestMethod.GET)
	public String submitBook(HttpServletRequest request, HttpSession session,
			@RequestParam("date") String dateParam, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
		
		return null;
	}
	
	// 홈페이지에서 예약일, 인원 선택 후 결제 전에 보여주는 페이지
	@RequestMapping(value = "/make", method = RequestMethod.GET)
	public ModelAndView makeBook(HttpServletRequest request, HttpSession session,
			@RequestParam(value="dateInput", 	required = false) String dateParam, @RequestParam(value="startTimeInput",	required = false) String startTime,
			@RequestParam(value="endTimeInput",	required = false) String endTime, 	@RequestParam(value="people",			required = false) String people) {
		ModelAndView mav = new ModelAndView("booking/payment");
		User user = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");	// 기존 예약이 있으면 가져옴
		
		if(dateParam != null) {	// 기존 예약이 없으면 생성
			book = new Booking(dateParam, startTime, endTime, Integer.parseInt(people), Booking.State.uncharge, room_no);
		}
		if(user == null) {		// 로그인이 안되었을시
			session.setAttribute("book", book);
			mav.setViewName("redirect:/signin");
			return mav;
		} else {
			book.setUser_no(user.getUser_no());
		}
		
		// mav add
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY년 M월 d일");
		String date = book.getCheck_in().format(formatter);
		String timeInterval = book.getCheck_in().toLocalTime() + " - " + book.getCheck_out().toLocalTime() + " (총" + bookingService.getTimeInterval(book) + ")";
		
		mav.addObject("date", date)
		.addObject("timeInterval", timeInterval)
		.addObject("people", book.getPeople())
		.addObject("point", user.getPoints())
		.addObject("singlePrice", bookingService.getAmount(book) / book.getPeople());
		
		// session registry
		session.setAttribute("book", book);
		return mav;
	}
	
	// 유저의 예약 목록을 보여주는 페이지
	@UserLoginCheck
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("_User/user_bookingList");
		
		User user = (User)session.getAttribute("sUserId");
		List<Booking> books = bookingService.searchByUser(user);
		
		JSONArray array = new JSONArray();
		for(Booking book : books) {
			int point = 0;
			int amount = 0;
			if(!book.getState().equals(Booking.State.uncharge) && !book.getState().equals(Booking.State.error)) {
				Payment payment = paymentService.selectPayment(book);
				point = payment.getPoint();
				amount = payment.getAmount();
			}
			
			array.put(
					new JSONArray()
					.put(book.getBook_no())
					.put(book.getCheck_in().toLocalDate().toString())
					.put(book.getCheck_in().toLocalTime().toString() + " - " + book.getCheck_out().toLocalTime().toString() + "<br>"
					 + "(" + bookingService.getTimeInterval(book) + ")")
					.put(book.getPeople() + "명")
					.put(point)
					.put(String.format("%,d", amount))
					.put(new JSONObject()
							.put("state", book.getState())
							.put("UI", book.getState().getUIValue())
							.put("req_dt", book.getRequest_dt().plusMinutes(11).withSecond(0).toString())
							)
					.put(new JSONObject()
							.put("book_no", book.getBook_no())
							.put("state", book.getState())
							)
					);
		}

		mav.addObject("books", array);
		return mav;
	}

	// 미결제 예약 취소
	@ResponseBody
	@RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public String cancel(HttpServletRequest request, HttpSession session, @RequestParam("book_no") int book_no) {
		User user = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByBookNo(book_no);
		if(book.getUser_no() == user.getUser_no()) {
			bookingService.removeBook(book);
			return  "예약이 삭제되었습니다.";
		} else {
			return "잘못된 요청입니다.";
		}
	}    

	// 미결제 예약 결제하기
	@UserLoginCheck
	@RequestMapping(value = "/uncharge", method = RequestMethod.GET)
	public String uncharge(HttpServletRequest request, HttpSession session, @RequestParam("book_no") int book_no) {
		User user = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByBookNo(book_no);
		if(book.getUser_no() == user.getUser_no()) {
			session.removeAttribute("book");
			session.setAttribute("book", book);
			return "redirect:" + "/booking/make";
		} else {
			return "redirect:" + "?msg=error";
		}
	}
	
	
	
	
	// booking modify page - 결제 수정 페이지
	@UserLoginCheck
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView editBook(HttpServletRequest request, HttpSession session, @RequestParam("book_no") int book_no) {
		ModelAndView mav = new ModelAndView("booking/change_booking");
		User user = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByBookNo(book_no);
		
		if(book == null || book.getUser_no() != user.getUser_no()) {
			mav.setViewName("redirect:" + "/");
			mav.addObject("msg", "잘못된 접근입니다.");
		} else if(!book.getState().equals(Booking.State.wait)) {
			mav.setViewName("redirect:" + "/");
			mav.addObject("msg", "수정이 불가능한 예약입니다.");
		} else {
			session.setAttribute("oldBook", book);
			Payment payment = paymentService.selectPayment(book);
			
			int charge = bookingService.getAmount(book);
			int usedPoint = payment.getPoint();

			// mav add
			String timeInterval = bookingService.getTimeInterval(book);
			
			mav.addObject("book_no", book_no)
			.addObject("date", book.getCheck_in().toLocalDate())
			.addObject("startTime", book.getCheck_in().toLocalTime())
			.addObject("endTime", book.getCheck_out().toLocalTime())
			.addObject("people", book.getPeople())
			.addObject("timeInterval", timeInterval)
			.addObject("charge", charge)
			.addObject("amountPerHour", bookingService.getAmountPerHour())
			.addObject("usedPoint", usedPoint);
		}
		
		return mav;
	}
	
	// booking shop page - 결제 요청 시
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public String makeSubmit(HttpServletRequest request, HttpSession session,
			@RequestParam("people") String peoples,
			@RequestParam("point") String point,
			@RequestParam("pg_name") String pg_name) {
		User user = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");

		// validation check
		int people = Integer.parseInt(peoples);
		if(people < 1 || people > 6) {
			return "?error=people";
		}

		
		// 뒤로가기 등으로 세션에서 예약이 제거된 경우
		if(book == null) {
			return "?error=bookIsNull";
		} else if(bookingService.isTimeLegal(book) == false) {
			return "?error=date";
		} else if(book.getRequest_dt() != null) {
			// 미결제 예약 재결제
		} else {
			// insert DB
			book.setPeople(people);
			String result = bookingService.insertBook(book);
			if(result.equals("duplicate")) {
				return "?booking=duplicate";
			}
		}
		
		int usePoint = Integer.parseInt(point);
		int charge = bookingService.getAmount(book);
		if(usePoint > user.getPoints() || usePoint < 0) {
			return "?error=point";
		}
		if(charge > 0 && charge < usePoint) {
			return "?error=usePoint";
		}
		// point로 전액 결제 시 결제요금 청구 안함.
		if(charge == usePoint) {
			paymentService.payByPoint(book, usePoint);
			bookingService.changeState(book, Booking.State.wait);
			userService.minusPoints(user, usePoint);
			return "/booking/check";
		}
		
		// session registry
		session.setAttribute("amount", charge);
		session.setAttribute("usePoint", usePoint);
		   
		// 결제 페이지(선택페이지)로 이동
		if(pg_name.equals(Payment.PGName.KakaoPay.toString())) {
			return "/payment/kakaopay";
		} else if(pg_name.equals(Payment.PGName.Danal.toString())) {
			/* 아임포트 merchant_uid에 우리 부킹넘버를 보내서 관리하기 편하기위함. 
			   return으로 보내서 비동기방식으로 paybyDanal 매소드를 호출하였을시 
			   js에서 결과값  전역변수에 저장가능 */
			
			
		
		String bookno =	 String.valueOf(book.getBook_no());
			 return bookno;   
		} else {
			return "?error=pg_name";
		}
	}
	
	//  카카오페이 예약 완료 처리
	@UserLoginCheck 
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public String complete(HttpServletRequest request, HttpSession session) {
		// 요청된 예약에 대해 예약번호를 얻고
		// 예약 번호로 된 결제가 있는지 확인한다.
		User user = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");
		Payment payment = paymentService.selectPayment(book);
		session.removeAttribute("book");
		// 결제가 있을 경우 해당 예약을 완료 상태로 놓는다.
		if(payment != null && payment.getState() == Payment.State.paid) {
			String msg = user.getName() +"님이"+ book.getCheck_in()+" ~ "+book.getCheck_out() +"예약했습니다.";
			adminService.MMSCall(msg); 
		return "redirect:" + "/booking/check";
		} else {
			return "redirect:" + "/?booking=fail";
		}
	}
	
	
	
	// makeBook 요청 시 요청한 user의 uncharge 상태 예약 모두 제거
	@ResponseBody
	@RequestMapping(value="/remove_uncharge", method = RequestMethod.GET)
	public void removeUnchargeBooks(HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("sUserId");
		if(user==null) return;
		List<Booking> books = bookingService.searchByUserState(user, "uncharge");
		for(Booking book : books) {
			int result = bookingService.removeBook(book);
			if(result==0) {
				System.err.println("#BookingController::removeUnchargeBooks:: remove fail");
				System.err.println("#BookingController::removeUnchargeBooks:: book : " + book);
				System.err.println("#BookingController::removeUnchargeBooks:: user_no : " + user.getUser_no());
			}
		}
	}
	

	@ResponseBody
	@RequestMapping(value = "danalCheck", method = RequestMethod.GET)
	public String danalCheck(HttpServletRequest request, HttpSession session, @RequestParam("book_no") int book_no) {
		
		User user = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByBookNo(book_no);
		Payment payment = paymentService.selectPayment(book);
		if(payment.getPg_name().equals("KakaoPay")) { 
			return "false";
		}else {
			return "true";         
		}
		
	}


	
	// booking modify bridge - 예약 변경 시 처리 브릿지
	@UserLoginCheck
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public String changeBridge(HttpServletRequest request, HttpSession session,
			@RequestParam(value="dateInput") String dateParam, @RequestParam(value="startTimeInput") String startTime,
			@RequestParam(value="endTimeInput") String endTime, 	@RequestParam(value="people") String people) {
		String point = "0";
		User user = (User)session.getAttribute("sUserId");

		Booking oldBook = (Booking)session.getAttribute("oldBook");
		Booking newBook = new Booking(user.getUser_no(), dateParam, startTime, endTime, Integer.parseInt(people), Booking.State.uncharge, room_no);
		
		if(bookingService.isSameOptions(oldBook, newBook)) {
			return "?error=same";
		}
		
		Payment oldPayment = paymentService.selectPayment(oldBook);									// 이전 결제
		int paidMoney = oldPayment.getAmount();														// 이전 결제 지불 금액
		int addedCharge = bookingService.getAmount(newBook) - bookingService.getAmount(oldBook);	// 추가요금
		
		// validation check
		String pointNums = point.replaceAll("[^0-9]","");
		int usePoint = Integer.parseInt(pointNums);
		if(user.getPoints() < usePoint || usePoint < 0) {
			return "?error=point";
		}
		if(addedCharge > 0 && usePoint > addedCharge) {
			return "?error=usePoint";
		}
		
		// 결제 요청 브릿지
		if(addedCharge < 0 && paidMoney < -1*addedCharge) {			// 돌려줘야 할 금액이 결제된 금액보다 큰 경우
			return "?payment=cancelFail";
		} else if (addedCharge == usePoint) {						// 추가금액을 포인트로 결제 or 변경 후 추가금액이 없음
			// 이전 결제 취소
			bookingService.changeState(oldBook, Booking.State.cancel);
			paymentService.changeState(oldPayment, Payment.State.cancelled);
			
			// 기존 결제로 예약-결제추가
			newBook.setState(Booking.State.wait);
			String result = bookingService.insertBook(newBook);
			if(result.equals("duplicate")) {
				return "?booking=duplicate";
			}
			
			// 기존 결제를 기반으로 새 결제 복사
			oldPayment.setState("paid");
			oldPayment.setBook_no(newBook.getBook_no());
			oldPayment.setPoint(oldPayment.getPoint()+usePoint);
			paymentService.insertPayment(oldPayment);
			
			// 포인트 사용값 처리
			userService.minusPoints(user, usePoint);
			return "redirect:" + "/booking/check";
		} else if(paidMoney == 0) {									// 예전 결제 금액이 0원 (포인트 처리 등) 추가금액 결제
			bookingService.changeState(oldBook, Booking.State.cancel);
			paymentService.changeState(oldPayment, Payment.State.cancelled);
			
			// 추가 결제금액으로 예약-결제 추가
			session.setAttribute("amount", addedCharge - usePoint);
			session.setAttribute("usePoint", oldPayment.getPoint() + usePoint);
			session.setAttribute("book", newBook);
			return "redirect:" + "/payment/kakaopay";
		} else {													// 일반적인 결제
			session.setAttribute("oldBook", oldBook);
			session.setAttribute("newBook", newBook);
			session.setAttribute("usePoint", usePoint);
			
			if(addedCharge > 0) {									// 추가 요금을 받아야 하는 경우
				session.setAttribute("amount", paidMoney + addedCharge - usePoint);	// 지불했던 금액 + 추가된 금액 - 사용한 포인트
				return "redirect:" + "/payment/cancelAndBooking";
			} else {												// 취소 요금을 줘야 하는 경우
				session.setAttribute("amount", addedCharge);
				return "redirect:" + "/payment/cancelAndChange";
			}
		}
	}
	
	// booking more payment - 예약 변경으로 인한 추가 결제 처리
	@UserLoginCheck
	@RequestMapping(value = "/cancelAndBooking", method = RequestMethod.GET)
	public String cancelAndBooking(HttpServletRequest request, HttpSession session) {
		Booking oldBook = (Booking)session.getAttribute("oldBook");
		Booking newBook = (Booking)session.getAttribute("newBook");
		int usePoint = (int)session.getAttribute("usePoint");
		
		// session registry
		session.setAttribute("book", newBook);

		session.removeAttribute("oldBook");
		session.removeAttribute("newBook");
		// 새 예약에 대한 요청
		return "redirect:/payment/kakaopay";
	}
	
	// booking less payment - 예약 변경으로 인한 차액 환불 처리
	@UserLoginCheck
	@RequestMapping(value = "/cancelAndChange", method = RequestMethod.GET)
	public String cancelAndChange(HttpServletRequest request, HttpSession session) {
		Booking oldBook = (Booking)session.getAttribute("oldBook");
		Booking newBook = (Booking)session.getAttribute("newBook");
		int usePoint = (int)session.getAttribute("usePoint");
		Payment payment = (Payment)session.getAttribute("payment");

		// 결제 취소 성공시
		// 기존 예약을 취소로 변경
		bookingService.changeState(oldBook, Booking.State.cancel);
		String result = bookingService.insertBook(newBook);
		if(result.equals("duplicate")) {
			return "?booking=duplicate";
		}
		payment.setBook_no(newBook.getBook_no());
		paymentService.insertPayment(payment);

		// 새 예약 등록
		bookingService.changeState(newBook, Booking.State.wait);

		session.removeAttribute("oldBook");
		session.removeAttribute("newBook");
		session.removeAttribute("amount");
		session.removeAttribute("usePoint");
		session.removeAttribute("payment");
		// 변경 결과 페이지
		return "redirect:/booking/check";
	}

	
	
	
	
	
	// 1분 간격으로 예약 시간이 되면 상태 변경
	@Scheduled(cron="0 */1 * * * *" )
	public void bookTrigger() {
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		// 사용하는 예약의 상태변경
		List<Booking> usingList = bookingService.selectByDateTime(now);
		for(Booking book : usingList) {
			if(now.isEqual(book.getCheck_in()) && book.getState().equals(State.wait)) {			// 예약시간이 되면 use로 변경
				bookingService.changeState(book, State.use);
				User user = userService.getUser(book.getUser_no());
				Integer amount = paymentService.selectPayment(book).getAmount();
				
				userService.plusPoints(user, (amount/100) * 5);
			} else if(now.isEqual(book.getCheck_out()) && book.getState().equals(State.use)) {	// 예약이 종료되면 clear로 변경
				bookingService.changeState(book, State.clear);
			}
		}
		
		// 결제 미완료 등으로 남겨진 에약의 제거
		List<Booking> unchargeList = bookingService.searchByState(State.uncharge);
		for(Booking book : unchargeList) {
			LocalDateTime requestDateTime = book.getRequest_dt();
			if(book.getCheck_in().isBefore(now)) {											// 지금보다 이전의 예약 제거
				bookingService.removeBook(book);
			} else if(requestDateTime.plusMinutes(10).isBefore(now)) {						// 요청한지 오래된 예약 제거
				bookingService.removeBook(book);
			}
		}
	}
}
