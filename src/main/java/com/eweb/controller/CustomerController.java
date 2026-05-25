package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.CustomerRepository;

import com.eweb.model.Customer;
import com.eweb.service.CustomerService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerController {
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerRepository customerRepository;
	
	  @GetMapping("/profile")
	    public ResponseEntity<?> getUserProfile(@RequestParam ("id") Long id , Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());		
				return customerService.getUserProfile(id);			
		}
	    }
