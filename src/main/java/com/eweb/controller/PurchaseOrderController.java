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

import com.eweb.dao.AdminRepository;
import com.eweb.dto.PurchaseOrderRequsetDto;
import com.eweb.dto.PurchaseReturnDto;
import com.eweb.dto.QualityCheckDto;
import com.eweb.model.Admin;
import com.eweb.service.PurchaseOrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PurchaseOrderController {
	@Autowired	
    private PurchaseOrderService service;

	  @Autowired
	  AdminRepository adminRepository;
	  
    @PostMapping("/createPo")
    public ResponseEntity<?> createPo(@RequestBody PurchaseOrderRequsetDto request , Authentication authentication) {
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());         
               return  service.createPurchaseOrder(request); 
        }

    @GetMapping("/getAllPo")
    public ResponseEntity<?> getAllPo(Authentication authentication) {
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
        return service.getAllPurchaseOrders();
    }

    @GetMapping("/getPoById")
    public ResponseEntity<?> getPoById(@RequestParam (value = "id" ,required = true)Long id ,Authentication authentication) {
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
        return service.getPurchaseOrder(id);
    }

    @PostMapping("/receiveStock")
    public ResponseEntity<?> receiveStock(@RequestParam (value = "poId" ,required = true) Long poId,Authentication authentication) {
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
        return  service.receiveStock(poId);
    }
    
    @PostMapping("/Quality_Check")
    public ResponseEntity<?> QualityCheck( @RequestBody  QualityCheckDto request,Authentication authentication){
    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return service.qualityCheckDetail(request);
    }
    
    @PostMapping("/purchaseReturn")
    public ResponseEntity<?> createReturn(
            @RequestBody PurchaseReturnDto request,Authentication authentication){
    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return service.createReturn(request);
    }
    
    @PostMapping("/approvePurchaseOrder")
    public ResponseEntity<?> approvePurchaseOrder(@RequestParam (value = "poId" ,required = true) Long poId,Authentication authentication) {
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
        return  service.approvePurchaseOrder(poId);
    }
    @PostMapping("/sendToVendor")
    public ResponseEntity<?> sendToVendor(@RequestParam (value = "poId" ,required = true) Long poId,Authentication authentication) {
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
        return  service.sendToVendor(poId);
    }
    
}
