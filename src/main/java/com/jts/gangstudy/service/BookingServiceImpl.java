package com.jts.gangstudy.service;

import java.text.DecimalFormat;
import java.time.*; 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.domain.Payment.State;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.mapper.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingMapper mapper;
	
	private final int dateSize = 7;			// 예약 신청이 가능한 기간
	private final int minimumSize = 120;	// 에약 신청 최소 단위
	private final int minuteSize = 30;		// 에약 신청 시간 단위
	private final int amount = 500;			// 예약 신청 시간 당 필요 요금
	
	
	/*		데이터베이스 연결	*/
	
	// datetime으로 검색
	@Override
	public List<Booking> selectByDateTime(LocalDateTime dateTime) {
		return mapper.selectByDateTime(dateTime);
	}
	
	// 유저의 uncharge를 제외한 해당일에 존재하는 다음 예약
	@Override
	public Booking searchNextBook(LocalDateTime dateTime, User user) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer user_no = user == null ? 0 : user.getUser_no();
		map.put("user_no", user_no);
		map.put("dateTime", dateTime);
		return mapper.selectNextBook(map);
	}
	
	
	
	
	
	/*		컨트롤러 연결	*/
	
	// 시작시간 목록
	@Override
	public List<String> getStartTimes(LocalDate date, List<Booking> books) {
		// select options 목록 생성
	    List<String> timeList = new ArrayList<>();
		// 시작지점
	    LocalTime localTime = LocalTime.MIN;
	    if(LocalDate.now().isEqual(date)) {	// 오늘인 경우
	    	int hourOffset = LocalTime.now().getHour();
	    	int minOffset = LocalTime.now().getMinute();
	    	localTime = localTime.plusHours(hourOffset);
		    if(hourOffset == 22) { 								// 22시 지나갔음.
		    	timeList.add("예약할 수 있는 시간이 없습니다.");
		    	return timeList;
		    }
		    localTime = minOffset < 30 ? localTime.plusHours(1).withMinute(0) : localTime.plusHours(1).withMinute(30);
	    }
	    
	    // select options 목록 채우기
		LocalDateTime start_dt = LocalDateTime.of(LocalDate.MIN, localTime);
		LocalDateTime end_dt = LocalDateTime.of(LocalDate.MIN, LocalTime.MIN).plusDays(1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for(; start_dt.isBefore(end_dt); start_dt = start_dt.plusMinutes(minuteSize)) {
			timeList.add(dtf.format(start_dt));
		}
		
		// select options 목록 비우기
		for(Booking book : books) {
			// 예약의 시작일시 -2시간
			LocalDateTime checkIn = book.getCheck_in().minusMinutes(minimumSize).plusMinutes(minuteSize);
			// 예약의 종료일시
			LocalDateTime checkOut = book.getCheck_out();
			
			if(!date.equals(checkIn.toLocalDate())) {			// date 이전에 시작된다면 -> checkIn  을 0시로 설정
				checkIn = checkIn.plusDays(1);
				checkIn = checkIn.with(LocalTime.MIN);
			} else if(!date.equals(checkOut.toLocalDate())) {	// date 이후에 종료된다면 -> checkOut 을 24시로 설정
				checkOut = checkOut.with(LocalTime.MIN);
			}

			for(; !checkIn.isEqual(checkOut); checkIn = checkIn.plusMinutes(minuteSize)) {
				String time = checkIn.toLocalTime().toString();
				timeList.remove(time);
			}
		}
		return timeList;
	}
	
	// 종료시간 목록
	@Override
	public List<String> getEndTimes(LocalDateTime dateTime, Booking book) {
		// select options 목록 생성
	    List<String> timeList = new ArrayList<String>();
	    LocalDateTime checkOut = dateTime.plusMinutes(minimumSize);
	    LocalDateTime max_dt = null;
	    
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
	    
	    // 시작시간~mdt 전까지 구하되 book이 존재하면 그 전까지만 구함.
		if(book == null) {
			max_dt = dateTime.plusDays(1);
		} else {
			max_dt = book.getCheck_in().plusMinutes(minuteSize);
		}
	    
	    for(; checkOut.isBefore(max_dt); checkOut = checkOut.plusMinutes(minuteSize)) {
	    	timeList.add(dtf.format(checkOut));
	    }
	    return timeList;
	}
	
	// 사용시간(00시간00분)
	@Override
	public String getTimeInterval(Booking book) {
		DecimalFormat df = new DecimalFormat("#.#");
		Duration duration = Duration.between(book.getCheck_in(), book.getCheck_out());
		float hour = (float) duration.toMinutes() / 60;
		return df.format(hour) + "시간";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 예약 추가(sync)
	public synchronized String insertBook(Booking book) {
		int result = searchDuplicate(book);
		if(result == 0) {
			mapper.insertBook(book);
			return "done";
		} else if(result > 1){
			System.err.println("BookingController:insertDB: duplicate booking is exist.");
			return "critical";
		} else {
			return "duplicate";
		}
	}
	
	// 예약 제거
	@Override
	public int removeBook(Booking book) {
		return mapper.deleteBook(book.getBook_no());
	}

	// 상태 변경
	@Override
	public int changeState(Booking book, Booking.State state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("book_no", Integer.toString(book.getBook_no()));
		map.put("state", state.toString());
		
		return mapper.updateState(map);
	}
	
	// 모든 예약 검색
	public List<Booking> searchAll() {
		return mapper.selectAll();
	}
	
	// 중복 검색
	public int searchDuplicate(Booking book) {
		HashMap<String, LocalDateTime> map = new HashMap<String, LocalDateTime>();
		map.put("check_in", book.getCheck_in());
		map.put("check_out", book.getCheck_out());
		System.out.println("check_in : " + book.getCheck_in() + ", check_out : " + book.getCheck_out());
		List<Booking> books = mapper.selectDuplicate(map);
		for(Booking b : books) {
			System.out.println("book_no : " + b.getBook_no() + ", check_in : " + b.getCheck_in() + ", check_out : " + b.getCheck_out());
		}
		
		return books.size();
	}

	@Override
	public List<Booking> searchByUser(User user) {
		return mapper.selectByUser(user.getUser_no());
	}

	// 해당일에 이미 존재하는 예약의 목록
	@Override
	public List<Booking> searchAlreadyBooked(Integer book_no, String date) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(book_no == null) { book_no = 0; }					// 예약 수정 시 필요함.
		map.put("book_no", book_no);
		map.put("date", date);
		List<Booking> books = mapper.selectAlreadyBooked(map);
		if(books == null) {
			return new ArrayList<Booking>();
		} else {
			return books;
		}
	}
	
	// 시간 유효성 체크
	@Override
	public boolean allowsBooking(Booking book) {
		LocalDateTime CIDT = book.getCheck_in();
		LocalDateTime CODT = book.getCheck_out();
		LocalDateTime now = LocalDateTime.now();
		if(now.isBefore(CIDT) && now.plusDays(8).with(LocalTime.MIN).isBefore(CODT)) return false;
		if(CODT.getDayOfYear() - CIDT.getDayOfYear() > 1) return false;
		if(CIDT.isAfter(CODT)) return false;
		return true;
	}
	
	
	
	// 상태로 검색
	@Override
	public List<Booking> searchByState(String state) {
		return mapper.selectByState(state);
	}

	// 유저의 특정 상태로 검색
	@Override
	public List<Booking> searchByUserState(User user, String state) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("user_no", user.getUser_no().toString());
		map.put("state", state);
		List<Booking> books = mapper.selectByUserState(map);
		if(books == null) {
			return new ArrayList<Booking>();
		} else {
			return books;
		}
	}

	// 유저의 대기중인 예약
	@Override
	public Booking searchByUserWait(User user) {
		List<Booking> books = searchByUserState(user, "wait");
		Booking book = null;
		if(books.size() > 1) {
			System.err.println("BookingServiceImpl:getUserWaitBooking: booking 'wait' state is least one more.");
		} else if(books.size() == 1) {
			book = books.get(0);
		}
		return book;
	}

	// 예약 번호로 검색
	@Override
	public Booking searchByBookNo(int book_no) {
		return mapper.selectByBookNo(book_no);
	}

	/*						*/
	/*		페이지 정보 기능	*/
	/*						*/
	// 날짜 목록
	@Override
	public ArrayList<String> makeDates() {
		ArrayList<String> dates = new ArrayList<String>();
		LocalDate localDate = LocalDate.now();
		
		for(int i = 0; i < dateSize; i++) {
			dates.add(localDate.toString());
			localDate = localDate.plusDays(1);
		}
		
		return dates;
	}


	// 예약 요금
	@Override
	public int getAmount(Booking book) {
		Duration duration = Duration.between(book.getCheck_in(), book.getCheck_out());
		int slot = (int)duration.toMinutes() / minuteSize;
		// 단위 수 * 단위 금액 * 인원
		return slot * amount * book.getPeople();
	}
	
	// 시간당 요금
	@Override
	public int getAmountPerHour() {
		return (60 / minuteSize) * amount;
	}




}