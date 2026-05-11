package com.eweb.dto;

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
}
