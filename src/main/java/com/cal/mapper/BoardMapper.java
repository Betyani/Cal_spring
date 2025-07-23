package com.cal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cal.dto.BoardDto;

@Mapper
public interface BoardMapper {
	public void boardRegister(BoardDto dto);
	int updateBoard(BoardDto dto);
	BoardDto selectBoardById(int id);
	List<BoardDto> selectAllBoards();
    void delete(int id);// 게시글 삭제
}