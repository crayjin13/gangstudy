package com.jts.gangstudy.repository;

import java.util.List;

import com.jts.gangstudy.domain.Notice;

public interface NoticeDao {

	//글작성
	int insert(Notice notice);
	
	//글 삭제
	int delete(Integer nNo);
	
	//글수정
	int update(Notice notice);
	
	//글목록 
	List<Notice> selectList();
	
	//작성자에따른 글 
	List<Notice> ListById(String admin_id);
	
	//글조회수 
	int viewCount(Integer notice_no);
	
	
	
	
	
}
