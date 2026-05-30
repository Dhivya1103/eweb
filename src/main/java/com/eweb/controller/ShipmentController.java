package com.eweb.controller;

import java.util.HashMap;
import java.util.Map;
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
import com.eweb.dao.OrderRepository;
import com.eweb.dao.OrderStatusRepository;
import com.eweb.dto.ShipmentRequest;
import com.eweb.model.Admin;
import com.eweb.model.Order;
import com.eweb.model.OrderStatus;
import com.eweb.service.shiprocketService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ShipmentController {
	  @Autowired
	    private shiprocketService hiprocketService;
	  @Autowired
	  OrderRepository ordersRepository;
	
	  @Autowired 
	  private OrderStatusRepository orderStatusRepository;
	  @Autowired
	  AdminRepository adminRepository;

	    @PostMapping("/create")
	    public ResponseEntity createShipment(
	            @RequestBody ShipmentRequest request ,Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
	        return hiprocketService
	                .createShipment(request);
	    }
	    
	    @GetMapping("/tracking") 
	    public ResponseEntity<?> trackOrder( @RequestParam(value="id", required = true) Long id, Authentication authentication) {
			UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
			Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());
	    	Order order = ordersRepository.findById(id) .orElseThrow(() -> new RuntimeException( "Order not found" ));
	    Map<String, Object> response = new HashMap<>(); 
	    response.put( "orderNumber", order.getOrderNumber() );
	    response.put( "trackingId", order.getTrackingId() );
	    response.put( "trackingUrl", order.getTrackingUrl() );
	    response.put( "courierName", order.getCourierName() ); 
	    response.put( "orderStatus", order.getOrderStatus() ); 
	    return ResponseEntity.ok(response); }
	    
//	    update shiped details
	    
	   
}
