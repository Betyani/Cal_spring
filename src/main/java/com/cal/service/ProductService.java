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
    
    
    // 상품 추천(좋아요) 등록
    boolean addLike(int productId, String userId);

    // 상품 추천(좋아요) 취소
    boolean removeLike(int productId, String userId);
    
    // 로그인한 사용자의 좋아요 여부 반영해서 상품 목록 조회
    List<ProductDto> getProductsByCriteriaWithLike(ListDto criteria, String userId);

    // 로그인한 사용자의 좋아요 여부 반영해서 상품 단건 조회
    ProductDto getProductByIdWithLike(int productId, String userId);

}
