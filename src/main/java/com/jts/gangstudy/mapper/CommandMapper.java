package com.jts.gangstudy.mapper;

import java.util.List;

import com.jts.gangstudy.domain.Command;

public interface CommandMapper {
	public List<Command> selectAll();
	public void insertCommand(Command command);
	public void deleteCommand(int command_no);

}
