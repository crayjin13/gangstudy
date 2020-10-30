package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;

public interface UserService {

	/*************** ������ ���� *********************/

	List<User> userBookingList();

	// ȸ�� ���
	List<User> UserList();

	// ȸ���� �˻�
	List<User> findUserList(String search);
	
	
	User getUser(int user_no);

	/*********************************************/

	// �α���
	User signIn(String id, String pw) throws Exception;

	// ȸ�� ���� Create
	boolean insertUser(User user);

	// ȸ�� ���̵�� �˻�
	User selectById(String id) throws Exception;

	// ȸ�� ����
	boolean updateUser(User user);

	// ���̵� �ߺ�üũ
	boolean idDuplicateCheck(String id);

	// ��й�ȣ ��ġ���� üũ
	boolean pwMatch(String pw);

	// ȸ�� Ż��
	boolean deleteUser(String id, String pw);

	// ���̵� ã��
	User findId(String email, String name);

	// ��й�ȣ ã��
	User findPw(String id, String email);

	// �ӽú�й�ȣ �߱�
	User temporaryPw(String pw, String id);

	User User(String name, String phone, String id, String pw, String email, String bod, String gender, int rate,
			String points, String note);

	boolean insertKakaoUser(User kakaoUser);

}
