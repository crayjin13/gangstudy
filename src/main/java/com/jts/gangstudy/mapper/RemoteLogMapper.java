package com.jts.gangstudy.mapper;

import java.util.List;

import com.jts.gangstudy.domain.RemoteLog;

public interface RemoteLogMapper {
	public List<RemoteLog> selectAll();
	public void insertLog(RemoteLog log);
}
