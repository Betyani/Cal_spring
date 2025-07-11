package com.cal.service;

import java.util.List;

import com.cal.dto.ListDto;
import com.cal.dto.ProductDto;

public interface ProductService {
	public void productRegister(ProductDto dto);

	public void productDelete(int id);

	List<ProductDto> getAllProducts();

	ProductDto getProductById(int id);

	List<ProductDto> getProductsByCriteria(ListDto criteria);

	int getProductCount(ListDto criteria);


}
