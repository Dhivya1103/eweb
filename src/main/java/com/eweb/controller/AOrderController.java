package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
}
