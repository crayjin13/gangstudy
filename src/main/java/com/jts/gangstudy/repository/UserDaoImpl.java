package com.jts.gangstudy.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;

import com.jts.gangstudy.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean insertUser(User user) {
		return userMapper.insertUser(user);
	}
	@Override
	public boolean insertKakaoUser(User user) {
		return userMapper.insertUser(user);
	}

	@Override
	public User selectById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectById(id);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	@Override
	public boolean idDuplicateCheck(String id) {

		return userMapper.idDuplicateCheck(id);
	}

	@Override
	public boolean pwMatch(String pw) {

		return userMapper.pwMatch(pw);
	}

	@Override
	public boolean deleteUser(String id, String pw) {
		// TODO Auto-generated method stub
		return userMapper.delete(id, pw);
	}

	@Override
	public User findId(String email, String name) {
		// TODO Auto-generated method stub
		return userMapper.find_id(email, name);
	}

	@Override
	public User findPw(String id, String email) {
		// TODO Auto-generated method stub
		return userMapper.findPw(id, email);
	}

	@Override
	public User temporaryPw(String pw, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> UserList() {

		return userMapper.userList();
	}

	@Override
	public List<User> findUserList(String search) {
		// TODO Auto-generated method stub
		return userMapper.findUserList(search);
	}

	@Override
	public boolean deleteMember(String pw, String email, String retire) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accountOn(String mRetire, String mEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> userBookingList() {
		// TODO Auto-generated method stub
		return userMapper.userBookingList();
	}

	@Override
	public User getUser(int user_no) {
		// TODO Auto-generated method stub
		return userMapper.getUser(user_no);
	}
	
	@Override
	public boolean updatePoints(int user_no, Float points) {
		// TODO Auto-generated method stub
		return userMapper.updatePoints(user_no, points);
	}

}
