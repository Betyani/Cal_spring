package com.cal.dto;

import lombok.Data;

@Data
public class ListDto {
    private String category;
    private String keyword;                       //검색밑에
    private String sort; //                    //종류,찾기
    private int page = 1;                        //페이징
    private int size = 8;                         //페이지 몇개까지 허용되는지 대강 보면 암

    public int getOffset() {
        return (page - 1) * size;
    
	}
}
