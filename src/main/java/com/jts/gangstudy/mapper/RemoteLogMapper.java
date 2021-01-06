package com.jts.gangstudy.mapper;

import java.time.LocalDate;
import java.util.List;

import com.jts.gangstudy.domain.RemoteLog;

public interface RemoteLogMapper {
	public List<RemoteLog> selectAll();
	public void insertLog(RemoteLog log);
	public List<RemoteLog> selectByDate(LocalDate date);
}
