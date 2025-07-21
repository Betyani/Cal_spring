package com.cal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cal.dto.BoardDto;
import com.cal.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequiredArgsConstructor // @AllArgsConstructor
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {

	private final BoardService service;

	@GetMapping("/list")
	public List<BoardDto> getBoardList() {
		List<BoardDto> list = service.getBoardList();
		log.info("게시글 목록 조회: " + list);
		return list;
	}

	@GetMapping("/detail/{id}")
	public BoardDto getBoardById(@PathVariable int id) {
		BoardDto dto = service.getBoardById(id);
		log.info("게시글 상세 조회 ID: " + id + ", 내용: " + dto);
		return dto;
	}

	@PostMapping("/register")
	public void insertBoard(@RequestBody BoardDto dto) {
		service.insertBoard(dto);
		log.info("게시글 등록: " + dto);
	}

	@PutMapping("/update")
	public void updateBoard(@RequestBody BoardDto dto) {
		service.updateBoard(dto);
		log.info("게시글 수정: " + dto);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteBoard(@PathVariable int id) {
		service.deleteBoard(id);
		log.info("게시글 삭제 ID: " + id);
	}
}