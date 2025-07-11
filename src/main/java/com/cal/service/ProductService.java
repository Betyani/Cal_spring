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
	
	 // 상품 1개 조회 (수정 시 기존 데이터 가져오기)
    public ProductDto selectProductById(int id);

    // 상품 수정
    public void updateProduct(int id, ProductDto product);

}
