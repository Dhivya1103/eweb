package com.eweb.service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.PaymentRepository;
import com.eweb.dto.PaymentDto;
import com.eweb.dto.VerifyDto;
import com.eweb.model.Payment;
import com.eweb.model.Status;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {
	 @Value("${razorpay.key}")
	    private String razorpayKey;

	    @Value("${razorpay.secret}")
	    private String razorpaySecret;
	    @Autowired
	    private  PaymentRepository paymentRepository;
	    @Autowired
	    private  OrderService orderService;
	    public ResponseEntity<?> createOrder(PaymentDto request ) throws Exception {
	        RazorpayClient razorpay = new RazorpayClient(razorpayKey, razorpaySecret);
	        JSONObject orderRequest =new JSONObject();
	        orderRequest.put("amount",request.getAmount() * 100);
	        orderRequest.put("currency","INR");
	        orderRequest.put("receipt","txn_" + System.currentTimeMillis());
	        Order order =razorpay.orders.create(orderRequest);
	        Map<String, Object> response =new HashMap<>();
	        response.put("razorpayOrderId",order.get("id"));
	        response.put("amount",order.get("amount"));
	        response.put("currency",order.get("currency"));
	        return ResponseEntity.ok(response);
	    }
	    
	    @Transactional
	    public ResponseEntity<Status> verifyPayment(VerifyDto request,Long userId) throws Exception {
	        // Create Payload
	        String payload =request.getRazorpayOrderId()+ "|"+ request.getRazorpayPaymentId();
	        // Generate Signature
	        String generatedSignature =Utils.getHash(payload,razorpaySecret);
	        Optional<Payment> existing =
	                paymentRepository.findByRazorpayPaymentId(
	                        request.getRazorpayPaymentId()
	                );

	        if (existing.isPresent()) {
	            return ResponseEntity
	                    .status(HttpStatus.CONFLICT)
	                    .body(new Status("409", "Payment already processed"));
	        }
	        	        // Verify Signature
	        boolean isValid =MessageDigest.isEqual( generatedSignature.getBytes(),request.getRazorpaySignature().getBytes());
//	        if(!isValid) {
//	        	return ResponseEntity.ok(new Status("404", "Payment verification failed!"));     
//	        	}
	          // SAVE PAYMENT
	        Payment payment = new Payment();
	        payment.setRazorpayOrderId( request.getRazorpayOrderId());
	        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
	        payment.setPaymentStatus("SUCCESS");
	        payment.setPaymentMethod(request.getPaymentMethod());
	        paymentRepository.save(payment);
	        // CREATE ORDER
	        ResponseEntity<?> order = orderService.createOrderAfterPayment(
	                        userId,
	                        request.getRazorpayPaymentId(),
	                        request.getPaymentMethod()
	                );

	         return ResponseEntity.ok(new Status("200", "Payment verification successfull!")); 
	         }}
