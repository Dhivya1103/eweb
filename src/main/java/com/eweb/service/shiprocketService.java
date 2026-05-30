package com.eweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eweb.dao.OrderRepository;
import com.eweb.dao.ShipmentRepository;
import com.eweb.dto.ShipmentRequest;
import com.eweb.model.Order;
import com.eweb.model.Shipment;

import jakarta.transaction.Transactional;

@Service
public class shiprocketService {
	
	@Autowired 	
    private  OrderRepository ordersRepository;
	@Value("${shiprocket.email}")
    private String email;

    @Value("${shiprocket.password}")
    private String password;

    private final RestTemplate restTemplate =
            new RestTemplate();

    @Autowired
    ShipmentRepository shipmentRepository;
    // LOGIN TOKEN
    public String getToken() {

        String url =
        "https://apiv2.shiprocket.in/v1/external/auth/login";

        Map<String, String> body = new HashMap<>();

        body.put("email", email);
        body.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        Map.class
                );

        return response.getBody()
                .get("token")
                .toString();
    }

    // CREATE SHIPMENT
    @Transactional
    public ResponseEntity createShipment(
            ShipmentRequest shipmentRequest) {

        String token = getToken();

        String url =
        "https://apiv2.shiprocket.in/v1/external/orders/create/adhoc";

        Map<String, Object> body = new HashMap<>();

        body.put("order_id",
                shipmentRequest.getOrderId());

        body.put("order_date",
               LocalDateTime.now());

        body.put("pickup_location",
                "Home");

        body.put("billing_customer_name",
                shipmentRequest.getCustomerName());

        body.put("billing_last_name", "");

        body.put("billing_address",
                shipmentRequest.getAddress());

        body.put("billing_city",
                shipmentRequest.getCity());

        body.put("billing_pincode",
                shipmentRequest.getPincode());

        body.put("billing_state",
                shipmentRequest.getState());

        body.put("billing_country",
                "India");

        body.put("billing_phone",
                shipmentRequest.getPhone());

        // PRODUCT DETAILS

        List<Map<String, Object>> items =
                new ArrayList<>();

        Map<String, Object> item =
                new HashMap<>();

        item.put("name", "Shoes");
        item.put("sku", "SHOE_01");
        item.put("units", 1);
        item.put("selling_price", 1200);

        items.add(item);

        body.put("order_items", items);

        body.put("payment_method", "Prepaid");

        body.put("sub_total", 1200);

        body.put("length", 10);
        body.put("breadth", 10);
        body.put("height", 10);
        body.put("weight", 1);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON);

        headers.setBearerAuth(token);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        Map.class
                );

        Map responseBody = response.getBody();

        System.out.println(responseBody);

        Shipment shipment = new Shipment();

        shipment.setOrderId(
                Long.valueOf(
                        shipmentRequest.getOrderId()));

        Object shipmentId =
                responseBody.get("shipment_id");

        Object awb =
                responseBody.get("awb_code");
        Object trackingUrl =
                responseBody.get("tracking_url");

        shipment.setShipmentId(
                shipmentId != null
                        ? shipmentId.toString()
                        : null);

        shipment.setAwbCode(
                awb != null
                        ? awb.toString()
                        : null);
        shipment.setTrackingUrl(
                trackingUrl != null
                        ? trackingUrl.toString()
                        : null
        );
        shipment.setCourierName("Shiprocket");

        shipment.setShipmentStatus("CREATED");

        Shipment saved = shipmentRepository.save(shipment);
        
        Optional<Order> orderOptional = ordersRepository.findById( Long.valueOf( shipmentRequest.getOrderId() ) ); 
        if(orderOptional.isPresent()) 
        { Order order = orderOptional.get(); 
        order.setTrackingId( shipment.getAwbCode() ); 
        order.setTrackingUrl( shipment.getTrackingUrl() ); 
        order.setCourierName( shipment.getCourierName() ); 
        order.setShipmentStatus( shipment.getShipmentStatus() ); 
        order.setOrderStatus("SHIPPED"); 
        order.setShippedAt(LocalDateTime.now());
        ordersRepository.save(order); 
        }
    	 
      
        Map<String, Object> result =
                new HashMap<>();

        result.put(
                "message",
                
        "Shipment Created Successfully"
        );

        result.put(
                "shipmentId",
                shipment.getShipmentId()
        );

        result.put(
                "awbCode",
                shipment.getAwbCode()
        );

        result.put(
                "courierName",
                shipment.getCourierName()
        );

        result.put(
                "status",
                shipment.getShipmentStatus()
        );
        result.put("trackingUrl", shipment.getTrackingUrl());

        return ResponseEntity.ok(result);
       

    }
}
