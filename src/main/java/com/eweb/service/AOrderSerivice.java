package com.eweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CustomerRepository;
import com.eweb.dao.OrderItemRepository;
import com.eweb.dao.OrderRepository;
import com.eweb.dao.OrderStatusRepository;
import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dto.AdminDto;
import com.eweb.dto.Aorderdetails;
import com.eweb.dto.OrderResponseDto;
import com.eweb.dto.ProductDto;
import com.eweb.dto.TrackingDto;
import com.eweb.dto.VariantDto;
import com.eweb.model.Customer;
import com.eweb.model.Order;
import com.eweb.model.OrderItem;
import com.eweb.model.OrderStatus;
import com.eweb.model.ProductVariant;
import com.eweb.model.Products;
import com.eweb.model.Status;
@Service
public class AOrderSerivice {
	
	@Autowired 	
    private  OrderRepository ordersRepository;
@Autowired
    private  OrderItemRepository orderItemsRepository;
@Autowired	
CustomerRepository customerRepository;
@Autowired
ProductsRepository productsRepository;
@Autowired
ProductVariantRepository productVariantRepository;
@Autowired
OrderStatusRepository orderStatusRepository;

	public ResponseEntity<?> updateOrderStatus(Long orderId, String status,String remarks) {
		Optional<Order> order = ordersRepository.findById(orderId);	           
		if(order.isPresent()) {
			Order nOrder = order.get();
			nOrder.setOrderStatus(status);
			ordersRepository.save(nOrder);
			 OrderStatus history =new OrderStatus();
			    history.setOrderId(orderId);
			    history.setStatus(status);
			    history.setRemarks(remarks);
			    history.setCreatedAt(LocalDateTime.now());
			    orderStatusRepository.save(history);			    
		    return ResponseEntity.ok(
		            new Status("200", "Status updated"));
		}
		return ResponseEntity.badRequest()
                .body(new Status("400", "Status not  Updated"));
	}

	public ResponseEntity<?> getAllorder() {
		List<Order> orders =ordersRepository.findAll();
		 if(orders.isEmpty()) {
			 return ResponseEntity.badRequest()
	                    .body(new Status("400",
	                            "order not found")); 
		 }
		 for( Order dto : orders) {
			 OrderResponseDto response = new OrderResponseDto(dto);
			 Optional<Customer> byId = customerRepository.findById(dto.getUserId());
			 if(byId.isPresent()) {
				response.setCustomerName(byId.get().getFullName());
			 }
			 return ResponseEntity.ok(response);
		 }

		   ;
		   return null;
	}
	
	public ResponseEntity<?> getOrderDetails(Long id) {

        Order order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Aorderdetails response = new Aorderdetails();

        response.setOrderId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setOrderStatus(order.getOrderStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCourierName(order.getCourierName());
        response.setTrackingId(order.getTrackingId());
        response.setOrderDate(order.getOrderDate());

        // CUSTOMER
        Customer customer = customerRepository
                .findById(order.getUserId())
                .orElse(null);
        if (customer != null) {
           AdminDto customerDto = new AdminDto(customer);         
            response.setCustomer(customerDto);
        }
        List<OrderItem> byOrderId = orderItemsRepository.findByOrderId(order.getId());
        Map<Long, ProductDto> productMap = new HashMap<>();
        for (OrderItem item : byOrderId) {
            // PRODUCT
            Products product = productsRepository.findById(item.getProductId()).orElse(null);
            // VARIANT
            ProductVariant variant =productVariantRepository.findById(item.getVariantId()).orElse(null);
            if (product != null && variant != null) {
                // CHECK EXISTING PRODUCT
                ProductDto productDto =productMap.get(product.getId());
                // CREATE PRODUCT ONLY ONCE
                if (productDto == null) {
                    productDto = new ProductDto(product);
                    productDto.setVariants(new ArrayList<>());
                    productMap.put(product.getId(), productDto);
                }
                // VARIANT DTO
                VariantDto variantDto =new VariantDto(variant);                               
                // ADD VARIANT
                variantDto.setQuantity(item.getQuantity());
                variantDto.setPrice(item.getPrice());
                variantDto.setTotal(
                        item.getPrice() * item.getQuantity()
                );
                productDto.getVariants().add(variantDto);
            }
        }
        response.setProducts(
                new ArrayList<>(productMap.values())
        );
        // TRACKING
        List<TrackingDto> trackingDtos =
        		orderStatusRepository.findByOrderId(order.getId())
                .stream()
                .map( data ->{TrackingDto dto = new TrackingDto(data);
                return dto; })
                .collect(Collectors.toList());
        response.setTracking(trackingDtos);
        return ResponseEntity.ok( response);
    }

   

   

}
