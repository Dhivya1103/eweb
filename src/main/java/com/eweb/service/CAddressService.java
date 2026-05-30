package com.eweb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CAddressRepository;
import com.eweb.dao.CartRepository;
import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dto.CAddressDto;
import com.eweb.dto.CheckOutDto;
import com.eweb.dto.CheckOutResponse;
import com.eweb.model.CAddress;
import com.eweb.model.Cart;
import com.eweb.model.ProductVariant;
import com.eweb.model.Products;
import com.eweb.model.Status;

@Service
public class CAddressService {
	@Autowired
	CAddressRepository cAddressRepository;
	@Autowired
	   private  CartRepository cartRepository;
	@Autowired
	    private  ProductsRepository productRepository;
	@Autowired
	ProductVariantRepository productVariantRepository;

	public ResponseEntity<?> addAddress(CAddressDto request) {
		CAddress address = new CAddress(request);
                address.setIsDefault(false);
                cAddressRepository.save(address);
                return ResponseEntity.ok(new Status("200", "User address added successfully!"));
	}
	 public ResponseEntity<?> getAddresses(Long userId) {

	         List<CAddress> byUserId = cAddressRepository.findByUserId(userId);
	         if(!byUserId.isEmpty()) {
	        	 List<CAddressDto> collect = byUserId.stream().map(data->{
	        	 CAddressDto dto = new CAddressDto(data);
	        	 return dto;
	        	 }).collect(Collectors.toList());
	        	 return new ResponseEntity<>(collect, HttpStatus.OK);
	         }
	         return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body(new Status("404", "Address  not found"));	    }
	 public ResponseEntity<?> updateAddress(CAddressDto request ) {

	        Optional<CAddress> address = cAddressRepository.findById(request.getId());
	                if(address.isPresent()) {
	                	if(address.get().getUserId().equals(request.getUserId())) {
	                		CAddress add = address.get();
	                		add.setFullName(request.getFullName());
	                		add.setPhone(request.getPhone());
	                		add.setAddressLine1(request.getAddressLine1());
	                		add.setAddressLine2(request.getAddressLine2());
	                		add.setCity(request.getCity());
	                		add.setState(request.getState());
	                		add.setCountry(request.getCountry());
	                		add.setPincode(request.getPincode());
	                		cAddressRepository.save(add);
	                		return ResponseEntity.ok(new Status("200", "User address updated successfully!"));
	                	}
	                	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                            .body(new Status("404", "Address  not belong to the user"));
	                }


	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new Status("404", "Address  not found"));
	    }

	    // Delete
	    public ResponseEntity<?> deleteAddress(Long id,Long userId) 
	    {
	    	 Optional<CAddress> address = cAddressRepository.findById(id);
             if(address.isPresent()) {
             	if(address.get().getUserId().equals(address.get().getUserId())) {
             		cAddressRepository.delete(address.get());
	        return ResponseEntity.ok(new Status("200", "User address updated successfully!"));
            	}
            	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Status("404", "Address  not belong to the user"));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Status("404", "Address  not found"));

	    }

	    // Set Default
	    public ResponseEntity<?> setDefaultAddress( Long id, Long userId) {
	        List<CAddress> addresses =
	        		cAddressRepository.findByUserId(userId);
	        if(addresses.isEmpty()) {
	        	 return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                     .body(new Status("404", "Address  not found"));
	        }
	        for (CAddress address : addresses) {
	            address.setIsDefault(false);
	            if (address.getId().equals(id)) {
	                address.setIsDefault(true);
	            }
	            cAddressRepository.save(address);	            
	        }
	        return ResponseEntity.ok(new Status("200", "User address updated successfully!"));
	    }
	    
	    
	    public ResponseEntity<?> checkout(CheckOutDto request) {
	        // 1. Get Cart Items
	        List<Cart> cartItems =cartRepository.findByUserId( request.getUserId());
	        if (cartItems.isEmpty()) {	           
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404", "Cart is empty" ));
	        }
	        // 2. Validate Address
	        Optional<CAddress> address =cAddressRepository.findById(request.getAddressId());
	        if (address.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404","Address is invalid"));
	        }
	        // 3. Calculate Total
	        List<Map<String, Object>> items = new ArrayList<>();
	        double total = 0;
	        double discount = 0;
	        for (Cart item : cartItems) {
	            Optional<Products> product =productRepository.findById(item.getProductId());
	            if (product.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new Status("404", "Product not found"));
	            }
	            Optional<ProductVariant> variant = productVariantRepository.findById(item.getVariantId());
	            if (variant.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new Status("404","Variant not found"));
	            }
	            // Stock Check
	            if (variant.get().getStock()< item.getQuantity()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                        .body(new Status("400","Out of stock for size " + variant.get().getSize()));
	            }
	            // Discount %
	            double discountPercent =product.get().getDiscount();
	            // Original Price
	            double originalPrice =variant.get().getPrice();
	            // Selling Price
	            double sellingPrice =originalPrice -((originalPrice* discountPercent) / 100);
	            // Sub Total
	            double subTotal =sellingPrice* item.getQuantity();
	            total += subTotal;
	            // Item Response
	            Map<String, Object> obj =new HashMap<>();
	            obj.put("productId",product.get().getId());
	            obj.put("productName",product.get().getName());
	            obj.put("variantId", variant.get().getId());
	            obj.put( "size",variant.get().getSize());
	            obj.put("quantity",item.getQuantity());
	            obj.put("originalPrice",originalPrice);
	            obj.put("discountPercent",discountPercent);
	            obj.put("sellingPrice",sellingPrice);
	            obj.put("subTotal",subTotal);
	            items.add(obj);
	        }
	        // 4. Delivery Charge
	        double deliveryCharge =total > 1000 ? 0 : 50;
	        // 5. Final Amount
	        double finalAmount =total + deliveryCharge;
	        // 6. Build Response
	        Map<String, Object> response = new HashMap<>();
	        response.put("userId",request.getUserId());
	        response.put("addressId",request.getAddressId() );
	        response.put("items",items);
	        response.put("totalAmount",total);
	        response.put( "deliveryCharge",deliveryCharge);
	        response.put("finalAmount",finalAmount);
	        response.put( "paymentMethod", request.getPaymentMethod());
	        // 7. Final Response
	        return ResponseEntity.ok(
	                new CheckOutResponse(true,"Checkout created successfully",response ));
	    }
	
}
