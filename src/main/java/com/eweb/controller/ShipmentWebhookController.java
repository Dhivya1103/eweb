package com.eweb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.OrderRepository;
import com.eweb.dao.OrderStatusRepository;
import com.eweb.model.Order;
import com.eweb.model.OrderStatus;

@RestController
@RequestMapping("/webhook")
@CrossOrigin
public class ShipmentWebhookController {
	
	  @Autowired
	  OrderRepository ordersRepository;
	
	  @Autowired 
	  private OrderStatusRepository orderStatusRepository;
	  
	  
	 @PostMapping("/shipment") 
	    public ResponseEntity<?> shipmentWebhook( @RequestBody Map<String, Object> payload ) 
	    { System.out.println(payload); 
	    // GET DATA 
	    String awb = payload.get("awb").toString();
	    String currentStatus = payload.get("current_status") .toString(); 
	    // FIND ORDER USING TRACKING ID 
	    Order order = ordersRepository .findByTrackingId(awb) .orElse(null); 
	    if(order == null) { return ResponseEntity.badRequest() .body("Order not found"); 
	    } 
	    // UPDATE SHIPMENT STATUS order.setShipmentStatus( currentStatus ); 
	    // OPTIONAL ORDER STATUS UPDATE 
	    if(currentStatus.equals("DELIVERED")) 
	    { order.setOrderStatus( "DELIVERED" ); 
	    } 
	    if(currentStatus.equals( "OUT_FOR_DELIVERY")) 
	    { order.setOrderStatus( "OUT_FOR_DELIVERY" ); 
	    } ordersRepository.save(order); 
	    // SAVE TRACKING HISTORY 
	    OrderStatus history = new OrderStatus();
	    history.setOrderId(order.getId()); 
	    history.setStatus(currentStatus); 
	    history.setRemarks( "Updated from Shiprocket Webhook" );
	    orderStatusRepository.save(history); 
	    return ResponseEntity.ok( "Webhook Received" ); 
	    }
	    
}
