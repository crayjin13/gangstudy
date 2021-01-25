package com.jts.gangstudy.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.repository.UserDao;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;
import com.jts.gangstudy.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	
	// �α׾ƿ�
		@Override
		public void logout(HttpServletResponse response) throws Exception {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("location.href='/';");
			out.println("</script>");
			out.close();
		}
	
	
	
	

	///////////////////////// ������ ////////////////////////////////
	@Override
	public List<User> UserList() {
		// TODO Auto-generated method stub
		return userDao.UserList();
	}

	@Override
	public List<User> findUserList(String search) {
		// TODO Auto-generated method stub
		return userDao.findUserList(search);
	}

	@Override
	public List<User> userBookingList() {
		// TODO Auto-generated method stub
		return userDao.userBookingList();
	}

	///////////////////////// *************************////////////////////////////////

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}
	
	// 회원 로그인
	@Override
	public User signIn(String id, String pw) throws Exception, PasswordMismatchException, UserNotFoundException {
		User user = userDao.selectById(id);
		if (user == null) {
			throw new UserNotFoundException(id + "찾을수없는 유저입니다.");
		}
		if (!user.isMatchPassword(pw)) {
			throw new PasswordMismatchException("정보가 일치하지않습니다.");
		}
		return user;
	}
	
	

	
	
	
	
	@Override
	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertUser(user);
	}
	@Override
	public boolean insertKakaoUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertKakaoUser(user);
	}

	@Override
	public User selectById(String id) throws Exception {
		// TODO Auto-generated method stub
		return userDao.selectById(id);
	}
	@Override
	public User selectAdmin(String id) throws Exception {
		// TODO Auto-generated method stub
		return userDao.selectById(id);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

	@Override
	public boolean idDuplicateCheck(String id) {
		// TODO Auto-generated method stub
		return userDao.idDuplicateCheck(id);
	}

	@Override
	public boolean pwMatch(String pw) {
		// TODO Auto-generated method stub
		return userDao.pwMatch(pw);
	}

	@Override
	public boolean deleteUser(String id, String pw) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(id, pw);
	}

	@Override
	public User findId(String email, String name) {
		// TODO Auto-generated method stub
		return userDao.findId(email, name);
	}

	@Override
	public User findPw(String id, String email) {
		// TODO Auto-generated method stub
		return userDao.findPw(id, email);
	}

	@Override
	public User temporaryPw(String pw, String id) {
		// TODO Auto-generated method stub
		return userDao.temporaryPw(pw, id);
	}

	@Override
	public com.jts.gangstudy.domain.User User(String name, String phone, String id, String pw, String email, String bod,
			String gender, int rate, String points, String note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(int user_no) {
		// TODO Auto-generated method stub
		return userDao.getUser(user_no);
	}

	@Override
	public boolean plusPoints(User user, float point) {
		// TODO Auto-generated method stub
		user.setPoints(user.getPoints() + point);
		return userDao.updatePoints(user.getUser_no(), user.getPoints() + point);
	}
	@Override
	public boolean minusPoints(User user, float point) {
		// TODO Auto-generated method stub
		user.setPoints(user.getPoints() - point);
		return userDao.updatePoints(user.getUser_no(), user.getPoints() - point);
	}






	@Override
	public boolean deleteUser(Integer user_no) {
		return userDao.deleteUser(user_no);
	}





	@Override
	public boolean updateNote(Integer user_no, String note) {
		return userDao.updateNote(user_no, note);
	}





	@Override
	public boolean updateRate(Integer user_no, float rate) {
		return userDao.updateRate(user_no, rate);
	}





	@Override
	public User getUserByNo(int user_no) {
		// TODO Auto-generated method stub
		return userDao.getUserByNo(user_no);
	}





	@Override
	public String getPw(String id) {
		// TODO Auto-generated method stub
		return userDao.getPw(id);
	}





	@Override
	public boolean changePw(String id, String pw) throws Exception {
		// TODO Auto-generated method stub
		return userDao.changePw(id, pw);
	}










	

	

}
