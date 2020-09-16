package com.jts.gangstudy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jts.gangstudy.exception.UserNotFoundException;
import com.jts.gangstudy.repository.UserDao;
import com.jts.gangstudy.exception.PasswordMismatchException;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.exception.UserNotFoundException;


@Service
public class UserServiceImpl implements UserService {
 
	@Autowired
	private UserDao userDao;
	
	//로그인
	@Override
	public User signIn(String id, String pw) throws Exception, PasswordMismatchException, UserNotFoundException  {
		User user= userDao.selectById(id);
		if(user == null) {
			throw new UserNotFoundException(id + "는 없는 아이디 입니다.");
		}
		if(!user.isMatchPassword(pw)) {
			throw new PasswordMismatchException("패스워드가 일치하지 않습니다.");
		}
		return user;
	}

	@Override
	public String insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertUser(user);
	}

	@Override
	public User userInfo(Integer user_no) {
		// TODO Auto-generated method stub
		return userDao.userInfo(user_no);
	}

	@Override
	public User selectById(String id) {
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
	public com.jts.gangstudy.domain.User User( String name, String phone, String id, String pw,
			String email, String bod, String gender, int rate, String points, String note) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	
	
	
	
}
