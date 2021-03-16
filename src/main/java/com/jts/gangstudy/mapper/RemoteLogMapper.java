package com.jts.gangstudy.mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.jts.gangstudy.domain.RemoteLog;

public interface RemoteLogMapper {
	public List<RemoteLog> selectAll();
	public void insertLog(RemoteLog log);
	public List<RemoteLog> selectByDateTime(LocalDateTime date);
}
