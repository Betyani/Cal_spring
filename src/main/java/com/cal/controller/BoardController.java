package com.cal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cal.dto.BoardDto;
import com.cal.dto.ListDto;
import com.cal.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
@RestController
public class BoardController {

	private BoardService service;

	@RequestMapping("/register")
	public void boardRegister(@RequestBody BoardDto dto) {
		service.boardRegister(dto);
		log.info("쓴 리뷰: " + dto);
	}

	@GetMapping("/list")
	public Map<String, Object> boardList(@RequestParam("productId") int productId,
			@RequestParam(defaultValue = "1", value = "page") int page) {

		ListDto dto = new ListDto();
		dto.setProductId(productId);
		int totalCount = service.getTotalCount();
		dto.setTotalCount(totalCount);
		dto.setTotalPage();
		dto.setPage(page);

		List<BoardDto> reviews = service.boardList(dto);
		log.info("받아온 리뷰: " + reviews);
		Map<String, Object> result = new HashMap<>();
		result.put("reviews", reviews);
		result.put("pageInfo", dto);

		return result;
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<BoardDto> getBoard(@PathVariable int id) {
		BoardDto board = service.getBoardById(id);
		if (board == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(board);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateBoard(@PathVariable int id, @RequestBody BoardDto dto) {
		service.updateBoard(id, dto);
		return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다."); // ← 메시지 반환!
	}
	
	// 게시글 삭제
    @DeleteMapping("/delete")
    public void deleteBoard(@RequestParam int id) {
        service.deleteBoard(id);
    }
    
}