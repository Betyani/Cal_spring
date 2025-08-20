package com.cal.dto;

import lombok.Data;

@Data
public class ListDto1 {
	
	//produtList 페이징 처리용
	private int page;
	private int startIndex;
	private int pageSize = 8;
	private int totalCount;
	private int totalPage;
	private int blockSize = 5;
	private int startPage;
	private int endPage;
	private boolean hasPrev;
	private boolean hasNext;
	
	//productList 검색 및 정렬용
	private String category;
	private String keyword;
	private boolean desc = true;

	//boardList 상품 연결용
	private int productId;
	
	
	//올림 처리(ceil)
	public void setTotalPage() {
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}
	
	public void setPage(int page) {
		this.page = page;
		this.startIndex = (page - 1) * pageSize;
		this.startPage = ((page - 1) / blockSize) * blockSize + 1 ;
		this.endPage = Math.min(startPage + blockSize - 1, totalPage);
		this.hasPrev = startPage > 1;
		this.hasNext = endPage < totalPage;
	}
	
	//공백 제거
	public void setKeyword() {
		if(keyword != null) {
			this.keyword = keyword.trim();
		}
	}
	
	
}
