package com.eweb.model;

import java.time.LocalDate;

import com.eweb.dto.CouponDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Coupon(CouponDto dto) {
		this.couponCode = dto.getCouponCode();
		this.discountType = dto.getDiscountType();
		this.discountValue = dto.getDiscountValue();
		this.minOrderAmount = dto.getMinOrderAmount();
		this.maxDiscount = dto.getMaxDiscount();
		this.validFrom = dto.getValidFrom();
		this.validTo = dto.getValidTo();
		this.usageLimit = dto.getUsageLimit();
		this.status = dto.getStatus();
	}
    
    
}
