package com.eweb.dto;

import java.util.List;

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
    private Long cId;
    private Long sId;
    private String description;
    private List<FvoriteDto> favorites;
    private List<ReviewDto> reviews;
    private List<VariantDto> variants;
	public DashboardproductDto(Products model) {
		super();
		this.id = model.getId();
		this.name = model.getName();
		this.imageUrl = model.getImageUrl();
		this.stock = model.getStock();
		this.price = model.getPrice();
		this.discount = model.getDiscount();
		this.cId=model.getCategory();
		this.sId= model.getSubCategory();
	
	}
	public DashboardproductDto(ProductList list) {
	
		this.id = list.getPId();
		this.name = list.getPName();
		this.imageUrl = list.getImage();
		this.categoryName = list.getCName();
		this.subCategoryName = list.getSName();
		this.stock = list.getStock();
		this.price = list.getPrice();
		this.discount = list.getDiscount();			
		this.cId = list.getCId();
		this.sId = list.getSCid();
		this.description=list.getDescription();
		
	}
    
    
}
