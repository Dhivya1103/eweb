package com.eweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponApplyResponse {
	 private String couponCode;

	    private Double orderAmount;

	    private Double discount;

	    private Double finalAmount;
}
