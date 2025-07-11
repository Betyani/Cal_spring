package com.cal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cal.dto.ListDto;
import com.cal.dto.ProductDto;
import com.cal.mapper.ProductMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	private ProductMapper mapper;

	@Override
	public void productRegister(ProductDto dto) {
		mapper.productRegister(dto);
	};

	@Override
	public void productDelete(int id) {
		mapper.productDelete(id);
	}
	@Override
	public ProductDto getProductById(int id) {
		return mapper.findById(id);
	}

	@Override
	public List<ProductDto> getProductsByCriteria(ListDto criteria) {
		return mapper.findWithCriteria(criteria);
	}

	@Override
	public int getProductCount(ListDto criteria) {
		return mapper.countWithCriteria(criteria);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	 @Override
	   public ProductDto selectProductById(int id) {
	       // 일단 DB 말고 하드코딩된 데이터로 연결 확인
	       return mapper.selectProductById(id);
	   }

	    @Override
	    public void updateProduct(int id, ProductDto dto) {
	        dto.setId(id); // URL 경로의 id와 DTO id 동기화
	        if (mapper.updateProduct(dto) == 0) {
	            System.out.println("⚠️ 업데이트 실패: 상품 없음");
	        }
	    }

	}
	
	