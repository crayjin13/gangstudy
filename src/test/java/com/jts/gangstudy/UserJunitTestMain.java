package com.jts.gangstudy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.repository.UserDao;
import com.jts.gangstudy.repository.UserDaoImpl;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class UserJunitTestMain {
	
	
	 public static void main(String[] args) {

	      ApplicationContext applicationContext = 
	            new FileSystemXmlApplicationContext("/src/main/webapp/WEB-INF/spring/root-context.xml");
	      UserDao userDao = applicationContext.getBean(UserDaoImpl.class);
	}

	 @Autowired
	   private ApplicationContext applicationContext;
	   private UserDaoImpl userDao;
	   //private MemberServiceImpl memberService;
	   
	   @Before
	   public void setUp() {
	      userDao=this.applicationContext.getBean(UserDaoImpl.class);
	      //memberService=this.applicationContext.getBean(MemberServiceImpl.class); 

	   }
	
	
	   @Test //회원가입
	   @Ignore
	   public void insertMember() {
	      User newUser = new User();
	      boolean user = userDao.insertUser(newUser);
	      System.out.println("## 회원가입(멤버 추가) :"+user);
	   }
	   

	@Test// 회원 아이디로 검색
	   public void selectById() {
	      User user = userDao.selectById("testId");
	      System.out.println("유저 아이디로 검색"+user);
	   }
	
	
	

}
 