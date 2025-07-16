package com.cal.service;

import java.util.List;

import com.cal.dto.BoardDto;

public interface BoardService {

	List<BoardDto> getBoardList();

	BoardDto getBoardById(int id);

	void insertBoard(BoardDto dto);

	void updateBoard(BoardDto dto);

	void deleteBoard(int id);
}