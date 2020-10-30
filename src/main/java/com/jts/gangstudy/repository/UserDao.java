package com.jts.gangstudy.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.jts.gangstudy.domain.Admin;
import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.domain.Booking;

public interface UserDao {

	// *********** ������ ���� ***************************//

	// ȸ�� ���
	List<User> UserList();

	// ȸ�� ���� ���
	List<User> userBookingList();

	// ȸ���� �˻�
	List<User> findUserList(String search);

	/*****************************************************/
	
	//���� ��� 
	User getUser(int user_no);

	// ȸ�� ���� Create
	boolean insertUser(User user);
	
	boolean insertKakaoUser(User user);

	// ȸ�� ���̵�� �˻�
	User selectById(String id);

	/* ���� ��Ȱ��ȭ (mRetire ON --> OFF) */
	boolean deleteMember(String pw, String email, String retire);

	/* ���� Ȱ��ȭ (mRetire OFF --> ON) */
	boolean accountOn(String mRetire, String mEmail);

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

}
