/*
 * package com.jts.gangstudy.mapper;
 * 
 * import org.apache.ibatis.annotations.Insert; import
 * org.apache.ibatis.annotations.Mapper; import
 * org.apache.ibatis.annotations.Param; import
 * org.apache.ibatis.annotations.Select; import
 * org.apache.ibatis.annotations.SelectKey;
 * 
 * 
 * import com.jts.gangstudy.domain.User;
 * 
 * @Mapper public interface UserMapper {
 * 
 * //회원가입
 * 
 * @Insert("INSERT INTO USR_TB VALUES (#{user_no},#{name},#{phone},#{id},#{pw},#{Email}),#{bod},#{gender},#{rate},#{points},#{note}"
 * )
 * 
 * @SelectKey(statement ="select user_no_seq.NEXTVAL from dual", keyProperty =
 * "user_no", before = true, resultType = Integer.class) public boolean
 * insertUser(User user);
 * 
 * 
 * //회원 정보 읽기
 * 
 * 
 * 
 * }
 */