package com.jts.gangStudy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jts.gangstudy.domain.User;
import com.jts.gangstudy.repository.UserDao;
import com.jts.gangstudy.repository.UserDaoImpl;

@ComponentScan("com.jts.gangstudy.*")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserUnitTestMain {
	//@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")

	/*
	 * public static void main(String[] args) {
	 * 
	 * ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
	 * "/src/main/webapp/WEB-INF/spring/root-context.xml"); UserDao userDao =
	 * applicationContext.getBean(UserDaoImpl.class); }
	 */
	
	  @Autowired
	   private ApplicationContext applicationContext;
	   private UserDaoImpl userDao;
	
	
	   @Before
	   public void setUp() {
	      userDao=this.applicationContext.getBean(UserDaoImpl.class);
	      //userService=this.applicationContext.getBean(userServiceImpl.class); 

	   }
	/*
	 * @Test
	 * 
	 * @Ignore public void insertUser() { User newUser = new
	 * User("테스트","01022224444","JTS","test1@naver.com","960814",'F',0,5.6,"");
	 * boolean user = userDao.insertUser(newUser);
	 * System.out.println("새로운회원이 가입했습니다."+user); }
	 */
	
	@Test
	@Ignore
	public void selectById() {
		User user= userDao.selectById("testId");
		System.out.println("### 회원 아이디로 검색"+user);
	}
	
	
	
	
	

}
