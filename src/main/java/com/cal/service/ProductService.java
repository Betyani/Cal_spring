package com.cal.service;

import com.cal.dto.ProductDto;

public interface ProductService {
	public void productRegister(ProductDto dto);

	public void productDelete(int id);
}