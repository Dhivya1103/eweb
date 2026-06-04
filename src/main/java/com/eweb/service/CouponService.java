package com.eweb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CouponRepository;
import com.eweb.dto.CouponDto;
import com.eweb.model.Coupon;
import com.eweb.model.Status;

import jakarta.transaction.Transactional;

@Service
public class CouponService {
	
	@Autowired
	CouponRepository couponRepository;
	
	
	
	@Transactional
	public ResponseEntity<?> createCoupon(CouponDto dto){
	    Coupon coupon = new Coupon(dto);	  
	    coupon.setUsedCount(0);
	    couponRepository.save(coupon);
	    return ResponseEntity.ok(new Status("200" ,"Coupon Created Successfully"));
	}
	
	public ResponseEntity<?> getAllCoupons(){
        List<Coupon> allCoupon = couponRepository.findAll();
        if(!allCoupon.isEmpty()) {
        	List<CouponDto> collect = allCoupon.stream().map(data->new CouponDto(data)).collect(Collectors.toList());
        	return ResponseEntity.ok(collect);
        }
        return null;
	}
	public ResponseEntity<?> getCouponById(Long id){
		 Optional<Coupon> byId = couponRepository.findById(id);
		 if(byId.isPresent()) {
			 Coupon coupon =byId.get();
			 CouponDto dto = new CouponDto(coupon);
			 return  ResponseEntity.ok(dto);
		 }
		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(new Status("404", "coupon not  present"));
	            
	}
	@Transactional
	public ResponseEntity<?> updateCoupon(CouponDto dto){
	    Optional<Coupon> coupon =couponRepository.findById(dto.getId());	         
	    if(coupon.isPresent()) {
	    	Coupon  model = coupon.get();
	    	model.setId(dto.getId());
	    	model.setCouponCode(dto.getCouponCode());
	    	model.setDiscountType(dto.getDiscountType());
	    	model.setDiscountValue(dto.getDiscountValue());
	    	model.setMaxDiscount(dto.getMaxDiscount());
	    	model.setMinOrderAmount(dto.getMinOrderAmount());
	    	model.setStatus(dto.getStatus());
	    	model.setUsageLimit(dto.getUsageLimit());
	    	model.setUsedCount(dto.getUsedCount());
	    	model.setValidFrom(dto.getValidFrom());
	    	model.setValidTo(dto.getValidTo());
	    	
	    	couponRepository.save(model);
	    }    
	    return ResponseEntity.ok(new Status("200" ,"Coupon updated Successfully"));
	}
	@Transactional
	public ResponseEntity<?> deleteCoupon(Long id){
	    Optional<Coupon> byId = couponRepository.findById(id);
	    if(byId.isPresent()) {
	    	Coupon coupon = byId.get();
	    	couponRepository.delete(coupon);
	    }
	    return ResponseEntity.ok(new Status("200" ,"Coupon deleted Successfully"));
	}
}
