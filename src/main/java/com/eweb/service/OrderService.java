package com.eweb.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CartRepository;
import com.eweb.dao.OrderItemRepository;
import com.eweb.dao.OrderRepository;
import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dto.OrderDto;
import com.eweb.dto.OrderResponseDto;
import com.eweb.model.Cart;
import com.eweb.model.Order;
import com.eweb.model.OrderItem;
import com.eweb.model.ProductVariant;
import com.eweb.model.Products;
import com.eweb.model.Status;
import com.razorpay.RazorpayClient;
import com.razorpay.Refund;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	
	
	 @Value("${razorpay.key}")
	    private String razorpayKey;

	    @Value("${razorpay.secret}")
	    private String razorpaySecret;
	    
	    
	@Autowired
	 private  CartRepository cartRepository;
	@Autowired
	    private  ProductVariantRepository  productVariantRepository;
	@Autowired 	
	    private  OrderRepository ordersRepository;
	@Autowired
	    private  OrderItemRepository orderItemsRepository;
	@Autowired
	private ProductsRepository productsRepoitory;
	

	@Transactional
	public ResponseEntity<?> createOrderAfterPayment(Long userId, String paymentId, String paymentMethod)
	{
	    // GET CART ITEMS
	    List<Cart> cartItems =cartRepository.findByUserId(userId);
	    if (cartItems.isEmpty()) {
	        return ResponseEntity.badRequest()
	                .body(new Status("400", "Cart is empty!"));
	    }
	    double totalAmount = 0;
	    // CREATE ORDER
	    Order order = new Order();
	    order.setUserId(userId);
	    order.setPaymentId(paymentId);
	    order.setPaymentMethod(paymentMethod);
	    order.setPaymentStatus("PAID");
	    order.setOrderStatus("CONFIRMED");
	    order.setOrderDate(LocalDateTime.now());
	    // SAVE ORDER FIRST
	    Order savedOrder = ordersRepository.save(order);
	    // GENERATE ORDER NUMBER
	    savedOrder.setOrderNumber(
	            generateOrderNumber(savedOrder.getId())
	    );
	    ordersRepository.save(savedOrder);
	    List<OrderItem> orderItems = new ArrayList<>();
	    // LOOP CART ITEMS
	    for (Cart cart : cartItems) {
	        Optional<Products> byId =productsRepoitory.findById(cart.getProductId());
	        if (byId.isEmpty()) {
	            throw new RuntimeException("Product not found");
	        }
	        Products product = byId.get();
	        Optional<ProductVariant> var =productVariantRepository.findById(cart.getVariantId());
	        if (var.isEmpty()) {
	            throw new RuntimeException(
	                    "Variant not found"
	            );
	        }
	        ProductVariant variant = var.get();
	        // STOCK CHECK
	        if (variant.getStock() < cart.getQuantity()) {
	            throw new RuntimeException(
	                    product.getName() + " out of stock"
	            );
	        }
	        // TOTAL
	        totalAmount +=variant.getPrice() * cart.getQuantity();
	        // REDUCE STOCK
	        variant.setStock( variant.getStock() - cart.getQuantity());
	        productVariantRepository.save(variant);
	        // CREATE ORDER ITEM
	        OrderItem item = new OrderItem();
	        item.setOrderId(savedOrder.getId());
	        item.setProductId(product.getId());
	        item.setVariantId(variant.getId());
	        item.setQuantity(cart.getQuantity());
	        item.setPrice(variant.getPrice());
	        orderItems.add(item);
	    }
	    // SAVE ALL ORDER ITEMS
	    orderItemsRepository.saveAll(orderItems);
	    // UPDATE TOTAL
	    savedOrder.setTotalAmount(totalAmount);
	    ordersRepository.save(savedOrder);
	    // CLEAR CART
	    cartRepository.deleteByUserId(userId);
	    return ResponseEntity.ok(
	            new Status(
	                    "200",
	                    "Order created successfully!"
	            )
	    );
	}
	    public ResponseEntity<?> createOrder(OrderDto request) {
	        List<Cart> cartItems =cartRepository.findByUserId(request.getUserId());
	        if (cartItems.isEmpty()) {
	            return ResponseEntity.badRequest()
	                    .body(new Status("400", "Cart is empty"));
	        }

	        double totalAmount = 0;
	        // CREATE ORDER
	        Order order = new Order();
	        order.setUserId(request.getUserId());
	        order.setPaymentMethod(request.getPaymentMethod());
	        order.setPaymentStatus(request.getPaymentStatus());
	        order.setOrderStatus("PLACED");
	        order.setOrderDate(LocalDateTime.now());
	        // SAVE FIRST
	        Order savedOrder = ordersRepository.save(order);
	        // GENERATE ORDER NUMBER
	        savedOrder.setOrderNumber(
	                generateOrderNumber(savedOrder.getId())
	        );
	        ordersRepository.save(savedOrder);

	        List<OrderItem> items = new ArrayList<>();
	        for (Cart cart : cartItems) {
	            Optional<ProductVariant> var =
	                    productVariantRepository.findById(cart.getVariantId());
	            if (var.isPresent()) {
	                ProductVariant variant = var.get();
	                // STOCK CHECK
	                if (variant.getStock() < cart.getQuantity()) {
	                    return ResponseEntity.badRequest()
	                            .body(new Status("400", "Out Of Stock"));
	                }
	                // TOTAL
	                totalAmount +=variant.getPrice() * cart.getQuantity();
	                // REDUCE STOCK
	                variant.setStock( variant.getStock() - cart.getQuantity());
	                productVariantRepository.save(variant);
	                // CREATE ORDER ITEM
	                OrderItem item = new OrderItem();
	                item.setOrderId(savedOrder.getId());
	                item.setProductId(cart.getProductId());
	                item.setVariantId(cart.getVariantId());
	                item.setQuantity(cart.getQuantity());
	                item.setPrice(variant.getPrice());
	                items.add(item);
	            }
	        }
	        // SAVE ORDER ITEMS
	        orderItemsRepository.saveAll(items);
	        // UPDATE TOTAL
	        savedOrder.setTotalAmount(totalAmount);
	        ordersRepository.save(savedOrder);
	        // CLEAR CART
	        cartRepository.deleteByUserId(request.getUserId());
	        return ResponseEntity.ok(
	                new Status("200", "COD Order Placed")
	        );
	    }

		
		@Transactional
		public ResponseEntity<?> cancelOrder(Long orderId, Long userId) {

		    Optional<Order> order = ordersRepository.findById(orderId);
		            
		    if(order.isEmpty()) {
		    	return ResponseEntity.badRequest().body(new Status("400", "order  is empty")); 
		    }
		    Order nOrder= order.get();
		    if (!nOrder.getUserId().equals(userId)) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN)
		                .body(new Status("403", "Not allowed"));
		    }

		    if (nOrder.getOrderStatus().equals("SHIPPED")) {
		        return ResponseEntity.badRequest()
		                .body(new Status("400", "Cannot cancel shipped order"));
		    }
		    // RESTORE STOCK
		    List<OrderItem> items =orderItemsRepository.findByOrderId(orderId);
		    for (OrderItem item : items) {
		        Optional<ProductVariant> variant = productVariantRepository.findById(item.getVariantId());
		                       
		        if(variant.isEmpty()) {
			    	return ResponseEntity.badRequest().body(new Status("400", "variant  is empty")); 
			    }
		        ProductVariant var = variant.get();
		        var.setStock(var.getStock() + item.getQuantity()
		        );
		        productVariantRepository.save(var);
		    }
		    if (nOrder.getPaymentMethod().equals("ONLINE")) {
		        try {
		            RazorpayClient client =new RazorpayClient(razorpayKey, razorpaySecret);
		            JSONObject refundRequest =new JSONObject();
		            // amount in paise
		            refundRequest.put("amount",nOrder.getTotalAmount() * 100);
		            Refund refund = client.payments
		                    .refund(
		                        nOrder.getPaymentId(),
		                        refundRequest
		                    );
		            nOrder.setPaymentStatus("REFUNDED");
		            nOrder.setRefundId(refund.get("id"));
		        } catch (Exception e) {
		            return ResponseEntity.badRequest()
		                    .body(new Status("400",
		                            "Refund failed"));
		        }
		    }
		    nOrder.setOrderStatus("CANCELLED");
		    ordersRepository.save(nOrder);
		    return ResponseEntity.ok(new Status("200", "Order Cancelled"));
		}


		public ResponseEntity<?> myOrder(Long userId) {
			 List<Order> orders =ordersRepository.findByUserIdOrderByIdDesc(userId);
			 if(orders.isEmpty()) {
				 return ResponseEntity.badRequest()
		                    .body(new Status("400",
		                            "order not found")); 
			 }
			 for( Order dto : orders) {
				 OrderResponseDto response = new OrderResponseDto(dto);
				 return ResponseEntity.ok(response);
			 }

			   ;
			   return null;
		}
		
		public String generateOrderNumber(Long id) {

		    String date = LocalDate.now()
		            .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		    return "ORD" + date + String.format("%04d", id);
		}
		
//		@Transactional
//		public ResponseEntity<?> refundOrder(Long orderId) {
//			Optional<Order> order = ordersRepository.findById(orderId);
//            
//		    if(order.isEmpty()) {
//		    	return ResponseEntity.badRequest().body(new Status("400", "order  is empty")); 
//		    }
//		    Order nOrder = order.get();
//		    if (!nOrder.getPaymentMethod().equals("ONLINE")) {
//		        return ResponseEntity.badRequest()
//		                .body(new Status("400", "Refund not allowed for COD"));
//		    }
//
//		    if (nOrder.getOrderStatus().equals("CANCELLED")) {
//		        return ResponseEntity.badRequest()
//		                .body(new Status("400", "Already cancelled"));
//		    }
//		    // RESTORE STOCK
//		    List<OrderItem> items = orderItemsRepository.findByOrderId(orderId);
//		    for (OrderItem item : items) {
//		        Optional<ProductVariant> variant =productVariantRepository.findById(item.getVariantId());
//		        if(variant.isEmpty()) {
//			    	return ResponseEntity.badRequest().body(new Status("400", "variant  is empty")); 
//			    }
//		        ProductVariant var = variant.get();
//		        var.setStock(var.getStock() + item.getQuantity());
//		        productVariantRepository.save(var);
//		    }
//		    nOrder.setOrderStatus("REFUNDED");
//		    nOrder.setPaymentStatus("REFUNDED");
//		    ordersRepository.save(nOrder);
//		    return ResponseEntity.ok(new Status("200", "Refund processed"));
//		}
		
		public ResponseEntity<?> getOrderDetails(Long orderId, Long userId) 
		{
		    Optional<Order> order =ordersRepository.findById(orderId);
		    if(order.isEmpty()) {
		    	return ResponseEntity.badRequest().body(new Status("400", "order  is empty")); 
		    }
		    Order nOrder= order.get();
		    // SECURITY CHECK
		    if(!nOrder.getUserId().equals(userId)) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN).body( new Status( "403","Access denied"));
		    }
		    List<OrderItem> items =orderItemsRepository.findByOrderId(orderId);
		    Map<String, Object> response =new HashMap<>();
		    response.put("order", order);
		    response.put("items", items);
		    return ResponseEntity.ok(response);
		}
}
