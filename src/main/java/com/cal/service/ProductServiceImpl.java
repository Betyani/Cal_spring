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


	}
	
	