package com.jts.gangstudy.service;

import java.util.List;

import com.jts.gangstudy.domain.Command;

public interface AdminService {
	public void sendMessage(String message);

	public void insertCommand(Command command);

	public void deleteCommand(Integer command_no);

	public List<Command> selectAll();
}
