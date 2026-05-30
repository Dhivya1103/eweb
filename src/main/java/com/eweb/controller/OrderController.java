package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.CustomerRepository;
import com.eweb.dto.OrderDto;
import com.eweb.model.Customer;
import com.eweb.service.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {
	@Autowired
	 private  OrderService orderService;
	
	@Autowired
	CustomerRepository customerRepository;

	    @PostMapping("/CreateCODOrder")
	    public ResponseEntity<?> createOrder(@RequestBody OrderDto request, Authentication authentication) {
			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());	
				return orderService.createOrder(request);
	    }
	    @PostMapping("/CancerOrder")
	    public ResponseEntity<?> cancelOrder( @RequestParam(value = "orderId") Long  orderId,@RequestParam(value = "userId") Long userId , Authentication authentication) {
			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());	
				return orderService.cancelOrder(orderId,userId);
	    }
//	    @PostMapping("/refundOrder")
//	    public ResponseEntity<?> refundOrder( @RequestParam(value = "orderId") Long  orderId, Authentication authentication) {
//			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
//				Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());	
//				return orderService.refundOrder(orderId);
//	    }
	    @GetMapping("/myOrder")
	    public ResponseEntity<?> myOrder( @RequestParam(value = "userId") Long userId , Authentication authentication) {
			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());	
				return orderService.myOrder(userId);
	    }
	    @GetMapping("/getOrder")	   
	    public ResponseEntity<?> getOrder( @RequestParam(value = "orderId") Long  orderId,@RequestParam(value = "userId") Long userId , Authentication authentication) {
			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());	
				return orderService.getOrderDetails(orderId,userId);
	    }
}
