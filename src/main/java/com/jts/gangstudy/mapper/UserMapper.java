package com.jts.gangstudy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jts.gangstudy.domain.User;
 
@Mapper
public interface UserMapper {

	//회원가입 
	@Insert("INSERT INTO USER_TB VALUES (#{user_no.NEXTVAL},#{name},#{phone},#{id},#{pw},#{Email}),#{bod},#{gender},#{rate},#{points},#{note}")
	public boolean insertUser(User user);
	
	
	  //회원 아이디로 읽기 
	 @Select("SELECT user_no, name, phone, id, pw, email, bod, gender, rate, points, note FROM USER_TB WHERE id=#{id}"
	  ) public User selectById(@Param("id") String id);
	 
	 
	  //회원 정보 수정
	 @Update("UPDATE USER_TB SET name=#{name}, phone=#{phone},id=#{id},pw=#{pw},email=#{email},bod=#{bod},gender=#{gender}"
	  + ",rate=#{rate},points=#{points},note=#{note} WHERE id=#{id}")
	 public boolean updateUser(User user);
	 
	 
	 
	 //아이디 중복체크
	public  boolean idDuplicateCheck(String id);
	 
	
	
}
