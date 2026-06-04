package com.eweb.dto;

import java.time.LocalDate;

import com.eweb.model.Coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponDto {
	private Long id;
	private String couponCode;

    private String discountType;

    private Double discountValue;

    private Double minOrderAmount;

    private Double maxDiscount;

    private LocalDate validFrom;

    private LocalDate validTo;

    private Integer usageLimit;
    private Integer usedCount;

    private String status;
    
    public CouponDto(Coupon dto) {
    	this.id = dto.getId();
		this.couponCode = dto.getCouponCode();
		this.discountType = dto.getDiscountType();
		this.discountValue = dto.getDiscountValue();
		this.minOrderAmount = dto.getMinOrderAmount();
		this.maxDiscount = dto.getMaxDiscount();
		this.validFrom = dto.getValidFrom();
		this.validTo = dto.getValidTo();
		this.usageLimit = dto.getUsageLimit();
		this.status = dto.getStatus();
		this.usedCount =dto.getUsedCount();
	}
    
}
