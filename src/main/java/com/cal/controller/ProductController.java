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
	// 주석 테스트
	
	// 상품 등록(React에서 받아온 json 데이터를 dto에 저장)
	@RequestMapping("/register")
	public void productRegister(@RequestBody ProductDto dto) {
		service.productRegister(dto);
		log.info("받아온 상품: " + dto);
	};

	@DeleteMapping("/delete/{id}")
	public void productDelete(@PathVariable int id) {
		service.productDelete(id);
		log.info("삭제된 상품 ID: " + id);
	};

	// 내가 정한 상품 상세히 조회가능
	@GetMapping("/detail/{id}")
	public ProductDto getProduct(@PathVariable int id) {
		return service.getProductById(id);
	}

	// 상품 목록 조회 및 검색
	@GetMapping("/list")
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

	// 1) 상품 조회 - 수정 화면에서 기존 데이터 불러올 때 사용
    @GetMapping("/test/{id}")
    public ResponseEntity<ProductDto> selectProduct(@PathVariable int id) {
        ProductDto product = service.selectProductById(id);
        log.info(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // 2) 상품 수정 - PUT 요청 처리
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        service.updateProduct(id, productDto);
        return ResponseEntity
        		.ok()
        		.header("Content-Type", "text/plain; charset=UTF-8") //???로 깨져보여서 추가
        		.body("상품이 성공적으로 수정되었습니다.");
    }
}