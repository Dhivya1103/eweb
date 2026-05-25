package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
	private Long productId;
    private String productName;
    private String pImage;
    private Double pPrice;

    private Long variantId;
    private String size;
    private String color;

    private Double price;
    private Long quantity;
    private Double total;
    private Double discount;
	public CartDto(CartList list) {		
		this.productId = list.getPId();
		this.productName = list.getPName();
		this.pImage = list.getImage();
		this.pPrice = list.getPPrice();
		this.variantId = list.getVariantId();
		this.size = list.getSize();
		this.color = list.getColor();
		this.price = list.getBasedamount();
		this.quantity = list.getQuantity();
		this.discount=list.getDiscount();
	}
    
    
}
