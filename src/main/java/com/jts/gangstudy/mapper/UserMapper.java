package com.jts.gangstudy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jts.gangstudy.domain.User;

@Mapper
public interface UserMapper {
	
	// 모든 유저 정보
	@Select("SELECT * FROM USER_TB WHERE id != #{id}")
	public List<User> selectAll(@Param("id") String id);
	
	// 유저넘버로얻기
		@Select("SELECT user_no, name, phone, id, pw, email, bod, gender, rate, points, note FROM USER_TB WHERE user_no=#{user_no}")
		public User getUser(@Param("user_no") int user_no);

	// 아이디찾기
	@Select("SELECT id FROM USER_TB WHERE email=#{email} and name=#{name}")
	public User find_id(@Param("email") String email, @Param("name") String name);

	// 비번 변경
	@Update("UPDATE USER_TB SET pw=#{pw} where id=#{id}") 
	public boolean update_pw(@Param("id") String id);

	// 비번찾기
	@Select("SELECT pw FROM USER_TB WHERE id=#{id} and email=#{email}")
	public User findPw(@Param("id") String id, @Param("email") String email);

	//아이디로 찾기
	@Select("SELECT user_no, name, phone, id, pw, email, bod, gender, rate, points, note FROM USER_TB WHERE id=#{id}")
	public User selectById(@Param("id") String id);
	
	//관리자 찾기
	@Select("SELECT u.user_no, u.name, u.phone, u.id, u.pw, u.email, u.bod, u.gender, u.rate, u.points, u.note FROM USER_TB u, ADMIN_TB a WHERE id=#{id} and u.user_no = a.user_no")
	public User selectAdmin(@Param("id") String id);
	
         
	// 회원탈퇴
	@Update("UPDATE USER_TB SET name='#',phone='#', id='#', pw='#', email='#' WHERE id=#{id} and pw=#{pw}")
	public boolean delete(@Param("id") String id, @Param("pw") String pw);

	// 유저수정
	@Update("UPDATE USER_TB SET name=#{name}, phone=#{phone},id=#{id},pw=#{pw},email=#{email},bod=#{bod},gender=#{gender} WHERE id=#{id}")
	public boolean updateUser(User user);

	// 중복체크
	@Select("SELECT count(*) cnt FROM USER_TB WHERE id=#{id}")
	public boolean idDuplicateCheck(String id);

	// 비번매치
	@Select("SELECT count(*) cnt FROM USER_TB WHERE pw=#{pw}")
	public boolean pwMatch(@Param("pw") String pw);

	// 유저검색(관리자입장)
	@Select("SELECT A.* FROM(SELECT user_no, name, phone, id, email, bod, gender, rate, points, note FROM USER_TB WHERE UPPER(ID) LIKE UPPER('%${search}%') or NAME LIKE '%${search}%' ORDER BY id ) A WHERE rownum <13")
	public List<User> findUserList(@Param("search") String search);

	// 유저목록(관리자입장)
	@Select("SELECT user_no, name, phone, id, email, bod, gender, rate, points, note FROM USER_TB WHERE ID!='#' ORDER BY user_no")
	public List<User> userList();

	// 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 (占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙)
	@Select("SELECT name, b.*, rate FROM BOOKING_TB b, USER_TB u WHERE ID!='#'")
	public List<User> userBookingList();

	// 회원가입
	@Insert("INSERT INTO USER_TB(name, phone, id, pw, email, bod, gender)"
			+ " VALUES (#{name, jdbcType=VARCHAR},#{phone, jdbcType=VARCHAR},#{id, jdbcType=VARCHAR},#{pw, jdbcType=VARCHAR},#{email, jdbcType=VARCHAR},#{bod, jdbcType=VARCHAR},#{gender, jdbcType=VARCHAR})")
	public boolean insertUser(User user);
	   
   
	@Insert("INSERT INTO USER_TB(name, id, pw)"
			+ " VALUES (#{name, jdbcType=VARCHAR},#{id},#{pw})")
	public boolean insertKakaoUser(User user);
	
	// 포인트 변경
	@Update("UPDATE USER_TB SET points=#{points} WHERE user_no=#{user_no}")
	public boolean updatePoints(@Param("user_no") Integer user_no, @Param("points") Float points);

	@Update("UPDATE USER_TB SET name='#',phone='#', id='#', pw='#', email='#' WHERE user_no=#{user_no}")
	public boolean deleteByUserNo(Integer user_no);

	@Update("UPDATE USER_TB SET note=#{note} WHERE user_no=#{user_no}")
	public boolean updateNote(@Param("user_no") Integer user_no, @Param("note") String note);

	@Update("UPDATE USER_TB SET rate=#{rate} WHERE user_no=#{user_no}")
	public boolean updateRate(@Param("user_no") Integer user_no, @Param("rate") Float rate);
	
}
