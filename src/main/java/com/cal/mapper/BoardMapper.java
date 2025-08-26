package com.cal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cal.dto.BoardDto;
import com.cal.dto.ListDto;

@Mapper
public interface BoardMapper {
	public void boardRegister(BoardDto dto);
	int updateBoard(BoardDto dto);
	BoardDto selectBoardById(int id);
	public List<BoardDto> boardList(ListDto dto);
	public int getTotalCount(int productId);
    void delete(int id);// 게시글 삭제
}