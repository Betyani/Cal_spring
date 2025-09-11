package com.cal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
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

	// ✅ 수정된 부분: 상품 추천(좋아요)
	@Override
	public boolean addLike(int productId, String userId) {
		try {
			log.info("[addLike] 실행됨: productId=" + productId + ", userId=" + userId);

			// (1) Map으로 포장해서 Mapper에 전달
			Map<String, Object> params = new HashMap<>();
			params.put("productId", productId);
			params.put("userId", userId);

			int inserted = mapper.insertLike(params); // DB insert 실행
			log.info("[addLike] insertLike 실행 결과: " + inserted);
			if (inserted > 0) {
				mapper.incrementLikeCount(productId); // products.like_count + 1
				log.info("[addLike] incrementLikeCount 실행 결과: " + inserted);
				return true;
			}
			return false;
		} catch (DuplicateKeyException e) {
			log.warn("이미 추천한 상품입니다. productId=" + productId + ", userId=" + userId);
			return false;
		}
	}

	// ✅ 수정된 부분: 상품 추천(좋아요) 취소
	@Override
	public boolean removeLike(int productId, String userId) {
		log.info("[removeLike] 실행됨: productId=" + productId + ", userId=" + userId);

		// (2) 여기서도 동일하게 Map으로 포장
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("userId", userId);

		int deleted = mapper.deleteLike(params); // DB insert 실행
		log.info("[removeLike] deleteLike 실행 결과: " + deleted);
		if (deleted > 0) {
			mapper.decrementLikeCount(productId); // Like_count - 1
			log.info("[removeLike] decrementLikeCount 실행 결과: " + deleted);
			return true;
		}
		return false;
	}

	// 로그인한 사용자 좋아요 여부 반영 상품 목록 조회 +  keyword/category 필터 유지
    @Override
    public List<ProductDto> getProductsByCriteriaWithLike(ListDto criteria, String userId) {
        Map<String, Object> params = new HashMap<>();
        // criteria 객체를 Map에 직접 넣지 않고 개별 필드로 분리
        params.put("keyword", criteria.getKeyword()); // keyword 검색 유지
        params.put("category", criteria.getCategory()); // category 필터 유지
        params.put("sort", criteria.getSort());
        params.put("page", criteria.getPage());
        params.put("size", criteria.getSize());
        params.put("userId", userId); // 로그인 사용자 ID 추가
        return mapper.findAllWithLike(params); // Mapper XML id와 일치
    }

    // 로그인한 사용자 좋아요 여부 반영 상품 단건 조회
    @Override
    public ProductDto getProductByIdWithLike(int productId, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("userId", userId);
        return mapper.findByIdWithLike(params);
    }
}
