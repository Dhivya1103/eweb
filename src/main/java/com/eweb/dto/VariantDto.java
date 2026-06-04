package com.eweb.dto;

import com.eweb.model.ProductVariant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VariantDto {
	private Long id;
	  private String color;
	    private String size;
	    private Long stock;
	    private Double price;
	    private Long productId;
	    private Long quantity;
	    private Double total;
	    private String pName;
	    public VariantDto(ProductVariant dto) {
			this.id=dto.getId();
			this.color = dto.getColor();
			this.size = dto.getSize();
			this.stock = dto.getStock();
			this.price = dto.getPrice();
			this.productId = dto.getProductId();
		}
	    
	    
}
