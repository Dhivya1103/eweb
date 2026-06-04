package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.AdminRepository;
import com.eweb.dto.CouponDto;
import com.eweb.dto.VendorPaymentRequest;
import com.eweb.model.Admin;
import com.eweb.service.CouponService;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class CouponController {
	@Autowired
	CouponService couponService;
	@Autowired
	AdminRepository adminRepository;
	
	
	@PostMapping("/createCoupon")
    public ResponseEntity<?> createCoupon(@RequestBody CouponDto request,Authentication authentication) {
	  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		  
		return   couponService.createCoupon(request);
    }
	
	@GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons(Authentication authentication) {
	  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		  
		return   couponService.getAllCoupons();
    }
	
	@GetMapping("/getCouponById")
    public ResponseEntity<?> getCouponById(@RequestParam (value= "id" , required = true) Long id,Authentication authentication) {
	  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		  
		return   couponService.getCouponById(id);
    }
	
	@PutMapping("/updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestBody CouponDto request,Authentication authentication) {
	  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		  
		return   couponService.updateCoupon(request);
    }
	@DeleteMapping("/deleteCoupon")
    public ResponseEntity<?> deleteCoupon(@RequestParam (value= "id" , required = true) Long id,Authentication authentication) {
	  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		  
		return   couponService.deleteCoupon(id);
    }
	
}
