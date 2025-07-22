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

    List<BoardDto> selectAll(); //게시글 전체(리스트)조회
    void delete(int id);// 게시글 삭제
}