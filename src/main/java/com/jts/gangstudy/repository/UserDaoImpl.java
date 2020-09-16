package com.jts.gangstudy.repository;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.mapper.UserMapper;
 
@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
	private UserMapper userMapper;

	@Override
	public String insertUser(User user) { 
		return userMapper.insertUser(user); 
	}

	@Override
	public User userInfo(Integer user_no) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pwMatch(String pw) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String id, String pw) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User findId(String email, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findPw(String id, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User temporaryPw(String pw, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> UserList() {
		// TODO Auto-generated method stub
		return userMapper.userList();
	}

	@Override
	public List<User> findUserList(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	
	}
	
	
	



