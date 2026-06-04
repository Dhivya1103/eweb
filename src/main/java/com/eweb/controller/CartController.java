package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.CustomerRepository;
import com.eweb.dto.ApplyCoupon;
import com.eweb.model.Cart;
import com.eweb.model.Customer;
import com.eweb.service.CartService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartController {
	
	@Autowired	
	CustomerRepository customerRepository;
	@Autowired
	CartService cartService;
	
	@PostMapping("/saveCart")
	  public ResponseEntity<?> saveCart(@RequestBody Cart dto ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
		  return cartService.addToCart(dto);
	  }
	
	@GetMapping("/findCartList")
	  public ResponseEntity<?> saveCategory(@RequestParam (value ="userId") Long userId ,Authentication authentication,Pageable pageable) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
		  return cartService.getCart(userId , pageable);
	  }
	@GetMapping("/findAllSize")
	  public ResponseEntity<?> findAllSize(@RequestParam (value = "productId") Long productId ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
		  return cartService.findAllSize(productId );
	  }
	@PostMapping("/updateCart")
	  public ResponseEntity<?> updateCart(@RequestBody Cart dto ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
		  return cartService.updateCart(dto);
	  }
	@DeleteMapping("/deleteCart")
	public ResponseEntity<?> deleteCartItem(@RequestBody Cart dto ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
		  return cartService.deleteCartItem(dto);
	  }
	
	@GetMapping("/getCartSummary")
	  public ResponseEntity<?> getCartSummary(@RequestParam (value = "userId") Long userId ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
	         Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
		  return cartService.getCartSummary(userId );
	  }
//	Cart apply
	@PostMapping("/coupons/apply")
	public ResponseEntity<?> applyCoupon(@RequestBody ApplyCoupon dto,Authentication authentication){
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
	    return cartService.applyCoupon(dto);
	}
	
	
}
