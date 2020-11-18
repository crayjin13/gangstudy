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
	
	// 회占쏙옙 占쏙옙호占쏙옙 占싻깍옙
		@Select("SELECT user_no, name, phone, id, pw, email, bod, gender, rate, points, note FROM USER_TB WHERE user_no=#{user_no}")
		public User getUser(@Param("user_no") int user_no);

	// 占쏙옙占싱듸옙 찾占쏙옙
	@Select("SELECT id FROM USER_TB WHERE email=#{email} and name=#{name}")
	public User find_id(@Param("email") String email, @Param("name") String name);

	// 占쏙옙橘占싫� 占쏙옙占쏙옙
	@Update("UPDATE USER_TB SET pw=#{pw} where id=#{id}")
	public boolean update_pw(@Param("id") String id);

	// 占쏙옙橘占싫� 찾占쏙옙
	@Select("SELECT pw FROM USER_TB WHERE id=#{id} and email=#{email}")
	public User findPw(@Param("id") String id, @Param("email") String email);

	// 회占쏙옙 占쏙옙占싱듸옙占� 占싻깍옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 
	@Select("SELECT user_no, name, phone, id, pw, email, bod, gender, rate, points, note FROM USER_TB WHERE id=#{id}")
	public User selectById(@Param("id") String id);

	// 회占쏙옙탈占쏙옙 @占쏙옙
	@Update("UPDATE USER_TB SET name='#',phone='#', id='#', pw='#', email='#', bod='#' WHERE id=#{id} and pw=#{pw}")
	public boolean delete(@Param("id") String id, @Param("pw") String pw);

	// 회占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 @ 占쏙옙
	@Update("UPDATE USER_TB SET name=#{name}, phone=#{phone},id=#{id},pw=#{pw},email=#{email},bod=#{bod},gender=#{gender} WHERE id=#{id}")
	public boolean updateUser(User user);

	// 占쏙옙占싱듸옙 占쌩븝옙체크 @占쏙옙
	@Select("SELECT count(*) cnt FROM USER_TB WHERE id=#{id}")
	public boolean idDuplicateCheck(String id);

	// 占쏙옙占쏙옙占식� 占쏙옙占쏙옙(회占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙) @占쏙옙
	@Select("SELECT count(*) cnt FROM USER_TB WHERE pw=#{pw}")
	public boolean pwMatch(@Param("pw") String pw);

	// 占쏙옙占쏙옙 占싯삼옙
	@Select("SELECT A.* FROM(SELECT user_no, name, phone, id, email, bod, gender, rate, points, note FROM USER_TB WHERE UPPER(ID) LIKE UPPER('%${search}%') or NAME LIKE '%${search}%' ORDER BY id ) A WHERE rownum <13")
	public List<User> findUserList(@Param("search") String search);

	// 占쏙옙占쏙옙 占쏙옙占�( 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙) @占쏙옙
	@Select("SELECT user_no, name, phone, id, email, bod, gender, rate, points, note FROM USER_TB WHERE ID!='#' ORDER BY user_no")
	public List<User> userList();

	// 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 (占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙)
	@Select("SELECT name, b.*, rate FROM BOOKING_TB b, USER_TB u WHERE ID!='#'")
	public List<User> userBookingList();

	// 회占쏙옙占쏙옙占쏙옙 @占쏙옙
	@Insert("INSERT INTO USER_TB(name, phone, id, pw, email, bod, gender)"
			+ " VALUES (#{name, jdbcType=VARCHAR},#{phone},#{id},#{pw},#{email},#{bod},#{gender})")
	public boolean insertUser(User user);
	
	// 회占쏙옙占쏙옙占쏙옙 @占쏙옙
	@Insert("INSERT INTO USER_TB(name, id, pw)"
			+ " VALUES (#{name, jdbcType=VARCHAR},#{id},#{pw})")
	public boolean insertKakaoUser(User user);


}
