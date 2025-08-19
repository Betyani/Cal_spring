package com.cal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cal.dto.BoardDto;
import com.cal.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper mapper;

	@Override
	public void boardRegister(BoardDto dto) {
		mapper.boardRegister(dto);
	}

	@Override
	public List<BoardDto> getBoardList() {
		return mapper.selectAllBoards();
	}

	@Override
	public BoardDto getBoardById(int id) {
		log.info("🔍 게시글 요청 id: " + id);
		return mapper.selectBoardById(id);
		
	}

	@Override
	  public void updateBoard(int id, BoardDto dto) {
	    dto.setId(id);
	    if (mapper.updateBoard(dto) == 0) {
	      System.out.println("⚠️ 수정 실패: 게시글 없음");
	    }
	    
	}
	
	 @Override
	    public void deleteBoard(int id) {
	        mapper.delete(id);
	    }
}