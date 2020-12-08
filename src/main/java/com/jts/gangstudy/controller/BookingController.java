package com.jts.gangstudy.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.jts.gangstudy.service.BookingService;
import com.jts.gangstudy.service.PaymentService;
import com.jts.gangstudy.service.UserService;


@Controller
@RequestMapping("/booking")
public class BookingController {
	private final int room_no = 1;

	@Autowired
	private UserService userService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private PaymentService paymentService;
	
	// 30분 간격으로 예약 시간이 되면 상태 변경
	@Scheduled(cron="0 */10 * * * *" )
	public void bookTrigger() {
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		
		// 사용하는 예약의 상태변경
		List<Booking> usingList = bookingService.searchByDateTime(now);
		for(Booking book : usingList) {
			if(now.isEqual(book.getciDateTime()) && book.getState().equals("wait")) {		// 예약시간이 되면 use로 변경
				bookingService.changeState(book, "use");
				User user = userService.getUser(book.getUser_no());
				Integer amount = paymentService.selectPayment(book).getAmount();
				
				userService.plusPoints(user, (amount/100) * 5);
			} else if(now.isEqual(book.getcoDateTime()) && book.getState().equals("use")) {	// 예약이 종료되면 clear로 변경
				bookingService.changeState(book, "clear");
			}
		}
		
		
		// 결제 미완료 등으로 남겨진 에약의 제거
		List<Booking> unchargeList = bookingService.searchByState("uncharge");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		for(Booking book : unchargeList) {
			LocalDateTime requestDateTime = LocalDateTime.parse(book.getRequest_dt(), formatter).plusHours(9);
			if(book.getciDateTime().isBefore(now)) {										// 지금보다 이전의 예약 제거
				bookingService.removeBook(book);
			} else if(requestDateTime.plusMinutes(10).isBefore(now)) {						// 요청한지 오래된 예약 제거
				bookingService.removeBook(book);
			}
		}
	}

	// 예약가능한 시작시간
	@ResponseBody
	@RequestMapping(value = "/reqStartTime", method = RequestMethod.GET)
	public List<String> reqStartTime(HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("sUserId");
		String startDate = request.getParameter("startDate");
		List<Booking> books = new ArrayList<Booking>();
		if(user!=null) {
			books.addAll(bookingService.searchByUserState(user, "wait"));
			books.addAll(bookingService.searchByUserState(user, "uncharge"));
		}
		List<String> times = bookingService.getStartTimes(books, startDate);
		return times;
	}

	// 예약가능한 종료시간
	@ResponseBody
	@RequestMapping(value = "/reqEndTime", method = RequestMethod.GET)
	public List<String> reqEndTime(HttpServletRequest request, HttpSession session) {
		String startTime = request.getParameter("startTime");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		User user = (User)session.getAttribute("sUserId");
		Booking book = null;
		if(user!=null) {
			book = bookingService.searchByUserWait(user);
		}
		List<String> times = bookingService.getEndTimes(book, startDate, startTime, endDate);
		return times;
	}

	// 예약 취소가 가능한 시간인지 확인
	@ResponseBody
	@RequestMapping(value = "/canCheck", method = RequestMethod.GET)
	public boolean canCheck(HttpServletRequest request, HttpSession session) {
		User user = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByUserWait(user);
		
		boolean canCancel = LocalDateTime.now().plusDays(1).isBefore(book.getciDateTime());
		return canCancel;
	}


	
	// booking order page - 결제 확인 페이지
	@UserLoginCheck
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView bookCheck(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/order-details");
		
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByUserWait(sUserId);
		
		// payment.getPoint();
		
		if(book==null) {
			mav.addObject("hasBooking", "false");
		} else {
			// book_no와 연결되는 payment를 얻는다.
			Payment payment = paymentService.selectPayment(book);
			
			mav.addObject("hasBooking", "true")
			.addObject("start", book.getFormattedCI("M월 d일 H시 mm분"))
			.addObject("end", book.getFormattedCO("M월 d일 H시 mm분"))
			.addObject("people", book.getPeople())
			.addObject("timeInterval", bookingService.getTimeInterval(book))
			.addObject("hourPrice", bookingService.getAmountPerHour())
			.addObject("totalCharge", bookingService.getAmount(book))
			.addObject("usedpoint", payment.getPoint());
		}
		mav.addObject("name", sUserId.getName());
		return mav;
	}
	
	// booking shop page - 결제 직전 페이지
	@RequestMapping(value = "/make", method = RequestMethod.GET)
	public ModelAndView makeBook(HttpServletRequest request, HttpSession session,
			@RequestParam("startDateInput") String startDate, 	@RequestParam("startTimeInput") String startTime,
			@RequestParam("endDateInput") String endDate, 		@RequestParam("endTimeInput") String endTime,
			@RequestParam("userCountInput") String people) {
		ModelAndView mav = new ModelAndView("pages/makecart");
		User user = (User)session.getAttribute("sUserId");
		
		// login check
		if(user == null) {
			mav.setViewName("redirect:/signin");
			session.setAttribute("startDate", startDate);
			session.setAttribute("startTime", startTime);
			session.setAttribute("endDate", endDate);
			session.setAttribute("endTime", endTime);
			session.setAttribute("people", people);
			return mav;
		}
		// check a existing booking
		user.getUser_no();
		Booking book = bookingService.searchByUserWait(user);
		if(book!=null) {
			mav.setViewName("redirect:/booking/check");
		}
		// check a uncharge booking
		List<Booking> books = bookingService.searchByUserState(user, "uncharge");
		for(Booking item : books) {
			bookingService.removeBook(item);
		}
		
		// registry booking in session
		book = new Booking();
		book.setCheck_in(startDate, startTime);
		book.setCheck_out(endDate, endTime);
		book.setPeople(people);
		book.setUser_no(user.getUser_no());
		book.setRoom_no(room_no);
		book.setState("uncharge");
		session.setAttribute("book", book);
		
		// mav add
		String startDateTime = book.getFormattedCI("M월 d일 H시 mm분");
		String endDateTime = book.getFormattedCO("M월 d일 H시 mm분");
		String timeInterval = bookingService.getTimeInterval(book);
		int chargePerPeople = bookingService.getAmount(book) / book.getPeople();
		
		mav.addObject("startDateTime", startDateTime)
		.addObject("endDateTime", endDateTime)
		.addObject("timeInterval", timeInterval)
		.addObject("chargePerPeople", chargePerPeople)
		.addObject("userCount", people)
		.addObject("point", user.getPoints())
		.addObject("charge", chargePerPeople*Integer.parseInt(people));
		return mav;
	}

	// booking shop page - 결제 요청 시
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public String makeSubmit(HttpServletRequest request, HttpSession session,
			@RequestParam("people") String peoples, @RequestParam("point") String point) {
		User user = (User)session.getAttribute("sUserId");
		Booking book = (Booking)session.getAttribute("book");
		// validation check
		int people = Integer.parseInt(peoples);
		if(people < 1 || people > 6) {
			return "?error=people";
		}

		int usePoint = Integer.parseInt(point);
		int charge = bookingService.getAmount(book);
		if(usePoint > user.getPoints() || usePoint < 0) {
			return "?error=point";
		}
		if(charge > 0 && charge < usePoint) {
			return "?error=usePoint";
		}
		if(bookingService.allowsBooking(book) == false) {
			return "?error=date";
		}
		// point로 전액 결제 시 결제요금 청구 안함.
		
		// insert DB
		book.setPeople(people);
		String result = bookingService.insertBook(book);
		if(result.equals("duplicate")) {
			return "?booking=duplicate";
		}
		
		// session registry
		session.setAttribute("amount", charge);
		session.setAttribute("usePoint", usePoint);
		
		// 결제 페이지(선택페이지)로 이동
		return "/payment/kakaopay";
	}

	// 예약 완료 처리
	@UserLoginCheck
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public String complete(HttpServletRequest request, HttpSession session) {
		// 요청된 예약에 대해 예약번호를 얻고
		// 예약 번호로 된 결제가 있는지 확인한다.
		Booking book = (Booking)session.getAttribute("book");
		Payment payment = paymentService.selectPayment(book);
		// 결제가 있을 경우 해당 예약을 완료 상태로 놓는다.
		if(payment != null) {
			bookingService.changeState(book, "wait");
			session.removeAttribute("book");
			return "redirect:" + "/booking/check";
		} else {
			return "?booking=fail";
		}
	}
	
	// booking modify page - 결제 수정 페이지
	@UserLoginCheck
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView editBook(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("pages/modify");
		User sUserId = (User)session.getAttribute("sUserId");
		Booking book = bookingService.searchByUserWait(sUserId);
		
		if(book == null) {	// 수정 불가능한 경우
			mav.setViewName("?booking=null");
		} else {			// 수정 가능한 경우
			Payment payment = paymentService.selectPayment(book);
			
			int charge = bookingService.getAmount(book);
			int usedPoint = payment.getPoint();

			// mav add
			String timeInterval = bookingService.getTimeInterval(book);
			
			mav.addObject("startDateInput", book.getciDate())
			.addObject("startTimeInput", book.getciTime())
			.addObject("endDateInput", book.getcoDate())
			.addObject("endTimeInput", book.getcoTime())
			.addObject("people", book.getPeople())
			.addObject("timeInterval", timeInterval)
			.addObject("charge", charge)
			.addObject("usedPoint", usedPoint);
		}
		
		return mav;
	}
	
	// booking modify page - 결제 직전 페이지
	@UserLoginCheck
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public ModelAndView editSubmit(HttpServletRequest request, HttpSession session,
			@RequestParam("startDateInput") String startDate, @RequestParam("endDateInput") String endDate,
			@RequestParam("startTimeInput") String startTime, @RequestParam("endTimeInput") String endTime,
			@RequestParam("people") String people) {
		ModelAndView mav = new ModelAndView("pages/modifycart");
		User user = (User)session.getAttribute("sUserId");
		Booking oldBook = bookingService.searchByUserWait(user);
		Booking newBook = new Booking();
		newBook.setUser_no(user.getUser_no());
		newBook.setRoom_no(room_no);
		newBook.setCheck_in(startDate, startTime);
		newBook.setCheck_out(endDate, endTime);
		newBook.setPeople(people);
		newBook.setState("uncharge");
		
		// 예약 신청이 기존 예약과 동일한 경우
		if(newBook.getciDateTime().isEqual(oldBook.getciDateTime()) &&
			newBook.getcoDateTime().isEqual(oldBook.getcoDateTime()) &&
			newBook.getPeople() == oldBook.getPeople()) {
			
			mav.setViewName("redirect:/booking/check");
			return mav;
		}
		// registry booking in session
		session.setAttribute("oldBook", oldBook);
		session.setAttribute("newBook", newBook);
		
		// add view attribute
		String startDateTime = newBook.getFormattedCI("M월 d일 H시 mm분");
		String endDateTime = newBook.getFormattedCO("M월 d일 H시 mm분");
		String timeInterval = bookingService.getTimeInterval(newBook);
		Payment payment = paymentService.selectPayment(oldBook);
		int cancelAmount = payment.getAmount();
		int addedAmount = bookingService.getAmount(newBook) - bookingService.getAmount(oldBook);
		
		mav.addObject("startDateTime", startDateTime)
		.addObject("endDateTime", endDateTime)
		.addObject("timeInterval", timeInterval)
		.addObject("userCount", people)
		.addObject("point", user.getPoints())
		.addObject("cancelAmount", cancelAmount)
		.addObject("addedAmount", addedAmount)
		.addObject("repayAmount", cancelAmount+addedAmount);
		return mav;
	}
	
	// booking modify bridge - 예약 변경 시 처리 브릿지
	@UserLoginCheck
	@ResponseBody
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public String changeBridge(HttpServletRequest request, HttpSession session, @RequestParam("point") String point) {
		User user = (User)session.getAttribute("sUserId");
		Booking oldBook = (Booking)session.getAttribute("oldBook");
		Booking newBook = (Booking)session.getAttribute("newBook");

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
			bookingService.changeState(oldBook, "cancel");
			paymentService.changeState(oldPayment, "cancelled");
			
			// 기존 결제로 예약-결제추가
			bookingService.changeState(newBook, "wait");
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
			return "/booking/check";
		} else if(paidMoney == 0) {									// 예전 결제 금액이 0원 (포인트 처리 등) 추가금액 결제
			bookingService.changeState(oldBook, "cancel");
			paymentService.changeState(oldPayment, "cancel");
			
			// 추가 결제금액으로 예약-결제 추가
			session.setAttribute("amount", addedCharge - usePoint);
			session.setAttribute("usePoint", oldPayment.getPoint() + usePoint);
			session.setAttribute("book", newBook);
			return "redirect:/payment/kakaopay";
		} else {													// 일반적인 결제
			session.setAttribute("oldBook", oldBook);
			session.setAttribute("newBook", newBook);
			session.setAttribute("usePoint", usePoint);
			
			if(addedCharge > 0) {									// 추가 요금을 받아야 하는 경우
				session.setAttribute("amount", paidMoney + addedCharge - usePoint);	// 지불했던 금액 + 추가된 금액 - 사용한 포인트
				return "/payment/cancelAndBooking";
			} else {												// 취소 요금을 줘야 하는 경우
				session.setAttribute("amount", addedCharge);
				return "/payment/cancelAndChange";
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
		bookingService.changeState(oldBook, "cancel");
		String result = bookingService.insertBook(newBook);
		if(result.equals("duplicate")) {
			return "?booking=duplicate";
		}
		payment.setBook_no(newBook.getBook_no());
		paymentService.insertPayment(payment);

		// 새 예약 등록
		bookingService.changeState(newBook, "wait");

		session.removeAttribute("oldBook");
		session.removeAttribute("newBook");
		session.removeAttribute("amount");
		session.removeAttribute("usePoint");
		session.removeAttribute("payment");
		// 변경 결과 페이지
		return "redirect:/booking/check";
	}
	
}
