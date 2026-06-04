package com.eweb.controller;

import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
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
import com.eweb.dto.OrderDto;
import com.eweb.dto.PurchaseReturnDto;
import com.eweb.dto.QualityCheckDto;
import com.eweb.dto.VendorDto;
import com.eweb.dto.VendorInvoiceDto;
import com.eweb.dto.VendorPaymentRequest;
import com.eweb.model.Admin;
import com.eweb.model.Customer;
import com.eweb.service.VendorService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VendorController {
	@Autowired
	private VendorService vendorService;
	@Autowired
	private AdminRepository adminRepository;
	
	  @PostMapping("/createVendor")
	    public ResponseEntity<?> createVendor(@RequestBody VendorDto request, Authentication authentication) {
			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
				return vendorService.createVendor(request);
	    }
	  
	  @GetMapping("/GetAllVendor")
	    public ResponseEntity<?> GetAllVendor( Authentication authentication) {
			  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
				return vendorService.GetAllVendor();
	    }
	  @GetMapping("/findByVendorId")
	    public ResponseEntity<?>findByVendorId( @RequestParam (value = "id" , required =true) Long id ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
	        return vendorService.findByVendorId(id);
	    }
	  @DeleteMapping("/findByVendorId")
	    public ResponseEntity<?>deleteFindByVendorId( @RequestParam (value = "id" , required =true) Long id ,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
	        return vendorService.deleteFindByVendorId(id);
	    }
	  @PutMapping("/updateVendor")
	    public ResponseEntity<?>updateVendor( @RequestBody VendorDto request,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
	        return vendorService.updateVendor(request);
	    }
	  
	  @PostMapping("/vendorPayment")
	    public ResponseEntity<?> makePayment(@RequestBody VendorPaymentRequest request,Authentication authentication) {
		  UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		  
			return   vendorService.makePayment(request);
	    }

	    @GetMapping("/allVendorPayment")
	    public ResponseEntity<?> getAll(Authentication authentication) {
	    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		       
	        		return vendorService.getAllPayments();
	    }

	    @GetMapping("/vendorPaymentById")
	    public ResponseEntity<?> getById(
	            @RequestParam(value= "id" ,required=true )Long id,Authentication authentication) {
	    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());		        
				return vendorService.getPayment(id);
	    }
	    
	    @PostMapping("/invoiceSave")
	    public ResponseEntity<?> saveInvoice( @RequestBody   VendorInvoiceDto request,Authentication authentication){
	    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
				Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
	        return vendorService.saveInvoice(request);
	    }
	   
	   
}
