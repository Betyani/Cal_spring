package com.cal.dto;

import lombok.Data;

@Data
public class ListDto {
    private String category;
    private String keyword;                       //검색밑에
    private String sort; //                    //종류,찾기
    private int page = 1;                        //페이징
    private int size = 8;                         //페이지 몇개까지 허용되는지 대강 보면 암

	private int startIndex;
	private int pageSize = 5;
	private int totalCount;
	private int totalPage;
	private int blockSize = 5;
	private int startPage;
	private int endPage;
	private boolean hasPrev;
	private boolean hasNext;
	
	//productList 검색 및 정렬용
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
    
    
    
    public int getOffset() {
        return Math.max((page - 1) * size, 0);
    }
}
