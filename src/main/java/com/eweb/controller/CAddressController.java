package com.eweb.controller;

import java.util.List;
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

import com.eweb.dao.CustomerRepository;
import com.eweb.dto.CAddressDto;
import com.eweb.dto.CheckOutDto;
import com.eweb.model.CAddress;
import com.eweb.model.Customer;
import com.eweb.service.CAddressService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CAddressController {
	@Autowired
	 private  CAddressService caddressService;
	@Autowired	
	CustomerRepository customerRepository;

	    @PostMapping("/addAddress")
	    public ResponseEntity<?> addAddress(@RequestBody CAddressDto request ,Authentication authentication)
	    { UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
        return caddressService.addAddress(request);
	    }
	    
	    @GetMapping("/findAllAddress")
	    public ResponseEntity<?> getAddresses(@RequestParam (value = "userId") Long userId,Authentication authentication)
	    { UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
	        return caddressService.getAddresses(userId);
	    }
	    
	    @PutMapping("/updateAddress")
	    public ResponseEntity<?> updateAddress(@RequestBody CAddressDto request ,Authentication authentication)
	    { UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
	        return caddressService.updateAddress(request);
	    }

	    @DeleteMapping("/deleteAddress")
	    public ResponseEntity<?> deleteAddress(@RequestParam  (value = "id") Long id, @RequestParam (value = "userId") Long userId,Authentication authentication)
	    { UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
        return caddressService.deleteAddress(id, userId);
	        
	    }

	    @PostMapping("/defaultAddress")
	    public ResponseEntity<?> setDefaultAddress( @RequestParam (value = "id") Long id,@RequestParam (value = "userId") Long userId ,Authentication authentication)
	    { UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
        return caddressService.setDefaultAddress(id, userId);

	       
	    }
	    
	    @GetMapping("/checkout")
	    public ResponseEntity<?> checkout(@RequestBody CheckOutDto request,Authentication authentication)
	    { UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Optional<Customer> user = customerRepository.findByUsername(userPrincipal.getUsername());
	        return caddressService.checkout(request);
	        
	    }
}
