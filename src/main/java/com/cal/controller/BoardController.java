package com.cal.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cal.dto.BoardDto;
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
}