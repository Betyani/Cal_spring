package com.cal.mapper;

import java.util.List;

import com.cal.dto.BoardDto;

public interface BoardMapper {
	public void boardRegister(BoardDto dto);
	int updateBoard(BoardDto dto);
	BoardDto selectBoardById(int id);
	List<BoardDto> selectAllBoards();
	
}