package com.cal.service;

import java.util.List;

import com.cal.dto.BoardDto;

public interface BoardService {
	public void boardRegister(BoardDto dto);

	BoardDto getBoardById(int id);

	void updateBoard(int id, BoardDto dto);

	List<BoardDto> getBoardList();
	
	List<BoardDto> getBoardList1();
	
	void deleteBoard(int id);
}