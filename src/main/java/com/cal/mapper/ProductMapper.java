package com.cal.mapper;
import java.util.List;
import java.util.Map;

import com.cal.dto.ListDto;
import com.cal.dto.ProductDto;

public interface ProductMapper {
	public void productRegister(ProductDto dto);
	public void productDelete(int id);
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
    
    // ✅ 추가된 부분: 상품 추천(좋아요) 관련
    int insertLike(Map<String, Object> params);     // 추천 기록 저장
    int deleteLike(Map<String, Object> params);     // 추천 기록 삭제
    int incrementLikeCount(int productId);          // products.like_count + 1
    int decrementLikeCount(int productId);          // products.like_count - 1
    // 로그인 사용자 좋아요 여부 포함 목록 조회
    List<ProductDto> findAllWithLike(Map<String, Object> params); 
    // 로그인 사용자 좋아요 여부 포함 단건 조회
    ProductDto findByIdWithLike(Map<String, Object> params);
}