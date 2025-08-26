package com.cal.service;

import java.util.List;

import com.cal.dto.BoardDto;
import com.cal.dto.ListDto;

public interface BoardService {
	public void boardRegister(BoardDto dto);

	BoardDto getBoardById(int id);

	void updateBoard(int id, BoardDto dto);

	public List<BoardDto> boardList(ListDto dto);
	public int getTotalCount(int productId);
	
	void deleteBoard(int id);
}