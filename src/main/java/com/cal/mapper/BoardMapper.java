package com.cal.mapper;

import java.util.List;

import com.cal.dto.BoardDto;

public interface BoardMapper {

	List<BoardDto> getBoardList();

	BoardDto getBoardById(int id);

	void insertBoard(BoardDto dto);

	void updateBoard(BoardDto dto);

	void deleteBoard(int id);
}