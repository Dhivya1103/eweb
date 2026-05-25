package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartSummaryDto {
	 private Long totalItems;

	    private Double subTotal;

	    private Double deliveryCharge;

	    private Double discount;

	    private Double finalAmount;
}
