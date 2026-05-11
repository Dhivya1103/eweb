package com.eweb.dto;

import com.eweb.model.Products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashboardproductDto {
	private Long id;
    private String name;
    private String imageUrl;
    private String categoryName;
    private String subCategoryName;
    private Long stock;
    private Double price;
    private Double discount;
    private Double savings;
    private Double favoritesCount;
    private Double reviewsCount;
//    private List<FavoriteDto> favorites;
//    private List<ReviewDto> reviews;
	public DashboardproductDto(Products model) {
		super();
		this.id = model.getId();
		this.name = model.getName();
		this.imageUrl = model.getImageUrl();
		this.stock = model.getStock();
		this.price = model.getPrice();
		this.discount = model.getDiscount();
	
	}
    
    
}
