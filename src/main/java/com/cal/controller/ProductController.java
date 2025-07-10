package com.cal.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cal.dto.ProductDto;
import com.cal.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@RequestMapping("/product/*")
@RestController
public class ProductController {

	private ProductService service;

//	상품 등록(React에서 받아온 json 데이터를 dto에 저장) 
	@RequestMapping("/register")
	public void productRegister(@RequestBody ProductDto dto) {
		service.productRegister(dto);
		log.info("받아온 상품: " + dto);
	};

	@RequestMapping("/delete") //(value = "/delete/{id}", method = RequestMethod.DELETE) 강사님이 RestController인 이거 쓰면 멋져보인다고 추천해주셨어요.
	public void productDelete(@PathVariable int id) {	//(@RequestBody ProductDto dto)
		service.productDelete(id);
		log.info("삭제된 상품 ID: " + id);
	};
}
