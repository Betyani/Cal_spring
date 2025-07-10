package com.cal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cal.dto.ListDto;
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

    // 내가 정한 상품 상세히 조회가능 
@GetMapping("/{id}")
public ProductDto getProduct(@PathVariable int id) {
return service.getProductById(id);
}
// 상품 목록 조회 및 검색
@GetMapping
public Map<String, Object> searchProducts(
@RequestParam(required = false) String keyword,
@RequestParam(required = false) String category,
@RequestParam(defaultValue = "new") String sort,
@RequestParam(defaultValue = "1") int page,
@RequestParam(defaultValue = "8") int size) {

ListDto criteria = new ListDto();
criteria.setKeyword(keyword);
criteria.setCategory(category);
criteria.setSort(sort);
criteria.setPage(page);
criteria.setSize(size);

List<ProductDto> products = service.getProductsByCriteria(criteria);
int total = service.getProductCount(criteria);

Map<String, Object> result = new HashMap<>();
result.put("products", products);
result.put("total", total);
result.put("page", page);
result.put("size", size);
return result;
}
@PostMapping
public void insertProduct(@RequestBody ProductDto product) {
service.insertProduct(product);
}


	
	
	
}
