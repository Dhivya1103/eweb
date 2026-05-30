package com.eweb.dto;

import java.util.ArrayList;
import java.util.List;

import com.eweb.model.ProductVariant;
import com.eweb.model.Products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {	 
		private Long id;
	    private String name;
	    private String description;	  
	    private Long categoryId;
	    private Long subCategoryId;	   
	    private Double price;
	    private Double discount; 	   
	    private Long stock;
	    private String imageUrl;
	    List<VariantDto> variants =new ArrayList<>();
		public ProductDto(Products dto) {			
			this.id = dto.getId();
			this.name = dto.getName();
			this.description = dto.getDescription();
			this.categoryId = dto.getCategory();
			this.subCategoryId = dto.getSubCategory();
			this.price = dto.getPrice();
			this.discount = dto.getDiscount();
			this.stock = dto.getStock();
			this.imageUrl = dto.getImageUrl();
		}
	    
	    
}
