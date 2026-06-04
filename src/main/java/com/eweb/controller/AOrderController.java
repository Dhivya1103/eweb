package com.eweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.AdminRepository;
import com.eweb.dao.CustomerRepository;
import com.eweb.dto.OrderDto;
import com.eweb.model.Admin;
import com.eweb.model.Customer;
import com.eweb.model.ProductVariant;
import com.eweb.service.AOrderSerivice;
import com.eweb.service.OrderService;
import com.eweb.service.shiprocketService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AOrderController {
	
	@Autowired
	 private  AOrderSerivice aOrderService;
	
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	shiprocketService hiprocketService;
	
	@PutMapping("/updateOrderStatus")
    public ResponseEntity<?> updateOrderStatus( @RequestParam(value = "orderId" , required =true) Long  orderId,@RequestParam(value = "status" ,required =true) String status ,@RequestParam(value = "remarks" ,required =true) String remarks , Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
			return aOrderService.updateOrderStatus(orderId , status ,remarks);
    }
	@GetMapping("/getAllorder")
    public ResponseEntity<?> getAllorder( Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			return aOrderService.getAllorder();
    }
	@GetMapping("/getOrderDetails")
    public ResponseEntity<?> getOrderDetails(@RequestParam(value = "orderId" , required =true) Long  orderId, Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			return aOrderService.getOrderDetails(orderId);
    }
	
//	@GetMapping("/getToken")
//    public String getToken( Authentication authentication) {
//		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
//		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
//			return hiprocketService.getToken();
//    }
	
	
	@GetMapping("/low-stock")
	public ResponseEntity<?> getLowStockProducts(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
		return  aOrderService.getLowStockProducts(10L);
	   
	}
	 @GetMapping("/sales-chart/7-days")
	    public ResponseEntity<?> getLast7DaysSales() {

		 return ResponseEntity.ok( aOrderService.getLast7DaysSales());
	    }

	    @GetMapping("/sales-chart/30-days")
	    public ResponseEntity<?> getLast30DaysSales() {

	         return ResponseEntity.ok( aOrderService.getLast30DaysSales());
	       
	    }
//	    all customers for admin
	    @GetMapping("/customers")
		public ResponseEntity<?> customers( @RequestParam(value = "name" , required =false) String name , @RequestParam(value = "mobile" , required =false) String mobile , @RequestParam(value = "email" , required =false) String email , Pageable pageable ,Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			return  aOrderService.getAllcustomers( name ,  mobile ,  email ,pageable);
		   
		}
	    
	    @GetMapping("/getCustomerDetails")
		public ResponseEntity<?> getCustomerDetails( @RequestParam(value = "cId" , required =true) Long cId ,Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			return  aOrderService.getCustomerDetails( cId);
		   
		}
	    
	    @PutMapping("/blockCustomer")
		public ResponseEntity<?> blockCustomer( @RequestParam(value = "cId" , required =true) Long cId ,Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			return  aOrderService.blockCustomer( cId);		   
		}
	    
	    @PutMapping("/unBlockCustomer")
		public ResponseEntity<?> unBlockCustomer( @RequestParam(value = "cId" , required =true) Long cId ,Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
			return  aOrderService.unBlockCustomer( cId);
		   
		}
}
