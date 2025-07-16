package com.cal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cal.dto.BoardDto;
import com.cal.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;

	@Override
	public List<BoardDto> getBoardList() {
		return mapper.getBoardList();
	}

	@Override
	public BoardDto getBoardById(int id) {
		return mapper.getBoardById(id);
	}

	@Override
	public void insertBoard(BoardDto dto) {
		mapper.insertBoard(dto);
	}

	@Override
	public void updateBoard(BoardDto dto) {
		mapper.updateBoard(dto);
	}

	@Override
	public void deleteBoard(int id) {
		mapper.deleteBoard(id);
	}
}