package com.cal.mapper;

import com.cal.dto.ProductDto;

public interface ProductMapper {
	public void productRegister(ProductDto dto);
	public int productDelete(int id); //void에서 int로 변경함
}
