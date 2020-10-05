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

	//회원자신 정보읽기 
	@Select("SELECT  name, phone, id, pw, email, bod, gender,points FROM USER_TB WHERE id=#{id}")
	public User userInfo(@Param("id") String id);
	
	  //회원 아이디로 읽기 
	 @Select("SELECT user_no, name, phone, id, pw, email, bod, gender, rate, points, note FROM USER_TB WHERE id=#{id}"
	  ) public User selectById(@Param("id") String id);
	 
	 
	 //회원탈퇴 @끝 
	 @Update("UPDATE USER_TB SET name='#',phone='#', id='#', pw='#', email='#', bod='#' WHERE id=#{id} and pw=#{pw}")
	 public boolean delete(@Param("id")String id,@Param("pw") String pw);
	 
	 
	 
	 
	 
	  //회원 정보 수정 @ 끝
	 @Update("UPDATE USER_TB SET name=#{name}, phone=#{phone},id=#{id},pw=#{pw},email=#{email},bod=#{bod},gender=#{gender} WHERE id=#{id}")
	 public boolean updateUser(User user);
	  

	 
	 //아이디 중복체크 @끝
	 @Select("SELECT count(*) cnt FROM USER_TB WHERE id=#{id}")
	public  boolean idDuplicateCheck(String id);
	 
	 //비번일치 여부(회원정보 수정할 떄) @끝 
	 @Select("SELECT count(*) cnt FROM USER_TB WHERE pw=#{pw}")
	 public boolean pwMatch(@Param("pw")String pw);
	 
	
	//유저 목록( 관리자입장) @끝
	 
	@Select("SELECT user_no, name, phone, id, email, bod, gender, rate, points, note FROM USER_TB WHERE ID!='#' ORDER BY user_no")
	public List<User> userList();
	
	
	//회원가입 @끝
		@Insert("INSERT INTO USER_TB(name, phone, id, pw, email, bod, gender)"
		+ " VALUES (#{name, jdbcType=VARCHAR},#{phone},#{id},#{pw},#{email},#{bod},#{gender})")
		public boolean insertUser(User user);
		
	 
}
