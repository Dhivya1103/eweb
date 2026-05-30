package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.CustomerRepository;
import com.eweb.dto.PaymentDto;
import com.eweb.dto.VerifyDto;
import com.eweb.model.Customer;
import com.eweb.service.PaymentService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PaymentController {
	@Autowired
	  private  PaymentService paymentService;
	@Autowired
	CustomerRepository customerRepository;

	    @PostMapping("/create-order")
	    public ResponseEntity<?> createOrder(@RequestBody PaymentDto request ,Authentication authentication) throws Exception {
	    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
	        return paymentService.createOrder(request);
	    }
	    

	    @PostMapping("/verify-order")
	    public ResponseEntity<?> verifyOrder(@RequestBody VerifyDto request , @RequestParam Long userId ,  Authentication authentication) throws Exception {
	    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
	        return paymentService.verifyPayment(request ,userId);
	    }
}
