package com.cal.mapper;
import java.util.List;

import com.cal.dto.ListDto;
import com.cal.dto.ProductDto;

public interface ProductMapper {
	public void productRegister(ProductDto dto);
	public int productDelete(int id); //void에서 int로 변경함
	List<ProductDto> findAll();
    ProductDto findById(int id);
    List<ProductDto> findWithCriteria(ListDto criteria);
    int getProductCount(ListDto criteria);
    void insertProduct(ProductDto product);
	int countWithCriteria(ListDto criteria);
	 // 상품 1개 조회 (수정할 상품 불러오기용)
    public ProductDto selectProductById(int id);
    // 상품 수정
    public int updateProduct(ProductDto product);
}