package com.cal.dto;

import lombok.Data;

@Data
public class ProductDto {
	private int id;
	private String name;
	private int price;
	private String category;
	private String imageUrl;
}
