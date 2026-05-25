package com.eweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CartRepository;
import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dto.CartDto;
import com.eweb.dto.CartList;
import com.eweb.dto.CartSummaryDto;
import com.eweb.dto.DashboardproductDto;
import com.eweb.dto.PageDataDto;
import com.eweb.dto.VariantDto;
import com.eweb.model.Cart;
import com.eweb.model.ProductVariant;
import com.eweb.model.Products;
import com.eweb.model.Status;

@Service
public class CartService {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductVariantRepository productVariantRepository;
	@Autowired
	ProductsRepository productsRepository;
	
	public ResponseEntity<?> addToCart(Cart dto) {
		 Cart existing =cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getVariantId());
			    if(existing != null){
			        existing.setQuantity(
			            existing.getQuantity() + dto.getQuantity()
			        );
			        cartRepository.save(existing);			       
			    }else {
			     cartRepository.save(dto);}
			    return ResponseEntity.ok(new Status("200", "Product Added To Cart successfully!"));
	}
	
	public ResponseEntity<?> getCart(Long userId,Pageable pageable){
		Page<CartList> cartList = cartRepository.findCartList(userId, pageable);
		
		List<CartDto> collect = cartList.stream().map(data -> {
			CartDto dto = new CartDto(data);
			dto.setTotal(data.getBasedamount() * data.getQuantity());
			return dto;
		}).collect(Collectors.toList());
		PageDataDto<CartDto> pageData = new PageDataDto<>(collect, cartList);
		return new ResponseEntity<>(pageData, HttpStatus.OK);
	  
	}

	public ResponseEntity<?> findAllSize(Long productId) {
		List<ProductVariant> byProduct = productVariantRepository.findByProduct(productId);
		  List<VariantDto> list = new ArrayList<>();
		    if (!byProduct.isEmpty()) {
		        for (ProductVariant model : byProduct) {
		            VariantDto dto = new VariantDto(model);
		            list.add(dto);
		            }
		        return new ResponseEntity<>(list, HttpStatus.OK);
		    }
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	public ResponseEntity<?> updateCart(Cart cart){
	    Cart existing =cartRepository.findByUserIdAndProductIdAndVariantId(cart.getUserId(), cart.getProductId(),cart.getVariantId());
	    if(existing!=null ) {	    	
	    	if (cart.getQuantity() == 0) {
	            cartRepository.delete(existing);
	            return ResponseEntity.ok(
	                    new Status("200", "Product removed from cart successfully!")
	            );
	        }
	    existing.setVariantId(cart.getVariantId());
	    existing.setQuantity(cart.getQuantity());
	   cartRepository.save(existing);
	   return ResponseEntity.ok(new Status("200", "cart updated  successfully!"));
	   }else {
		   Cart dto = new Cart();
		   dto.setId(cart.getId());
		   dto.setProductId(cart.getProductId());
		   dto.setQuantity(cart.getQuantity());
		   dto.setUserId(cart.getUserId());
		   dto.setVariantId(cart.getVariantId());
		   
           cartRepository.save(dto);
           cartRepository.delete(existing);
           return ResponseEntity.ok(new Status("200", "cart updated  successfully!"));
	   }
	    
	}


	

	public ResponseEntity<?> deleteCartItem(Cart dto) {
		 Cart existing =cartRepository.findByUserIdAndProductIdAndVariantId(dto.getUserId(), dto.getProductId(), dto.getVariantId());
			    if (existing != null) {
			        cartRepository.delete(existing);
			        return ResponseEntity.ok(new Status("200","Cart item removed successfully!"));
			    }
			    return ResponseEntity.ok(new Status("400","Cart item not found!") );
	}
	
	public ResponseEntity<?> getCartSummary(Long userId){
	    List<Cart> carts =cartRepository.findByUserId(userId);
	    double subtotal = 0;
	    Long totalItems = 0L;
	    Double discount=0.0;	    
	    for(Cart cart : carts){	    	
	        Optional<ProductVariant> variant =productVariantRepository.findById(cart.getVariantId());
	            if(variant.isEmpty()) {
	            	variant = null;
	            }
	            Optional<Products> byId = productsRepository.findById(cart.getProductId());
	            if(byId.isEmpty()) {
	            	byId = null;
	            }
	        double price = variant.get().getPrice();
	        subtotal += price * cart.getQuantity();
	        totalItems += cart.getQuantity();	
	        discount +=cart.getQuantity() *byId.get().getDiscount();
	    }
	    double delivery = subtotal > 500 ? 0 : 50;
	    double finalAmount = subtotal + delivery - discount;
	    CartSummaryDto dto = new CartSummaryDto();
	    dto.setTotalItems(totalItems);
	    dto.setSubTotal(subtotal);
	    dto.setDeliveryCharge(delivery);
	    dto.setDiscount(discount);
	    dto.setFinalAmount(finalAmount);
	    return ResponseEntity.ok(dto);
	}
}
