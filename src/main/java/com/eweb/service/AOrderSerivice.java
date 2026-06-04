package com.eweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.CAddressRepository;
import com.eweb.dao.CustomerRepository;
import com.eweb.dao.OrderItemRepository;
import com.eweb.dao.OrderRepository;
import com.eweb.dao.OrderStatusRepository;
import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.ProductsRepository;
import com.eweb.dto.AdminDto;
import com.eweb.dto.Aorderdetails;
import com.eweb.dto.CustomerDetailsResponse;
import com.eweb.dto.CustomerResponse;
import com.eweb.dto.OrderHistoryDto;
import com.eweb.dto.OrderResponseDto;
import com.eweb.dto.ProductDto;
import com.eweb.dto.SalesChartDto;
import com.eweb.dto.TrackingDto;
import com.eweb.dto.VariantDto;
import com.eweb.model.CAddress;
import com.eweb.model.Customer;
import com.eweb.model.Order;
import com.eweb.model.OrderItem;
import com.eweb.model.OrderStatus;
import com.eweb.model.ProductVariant;
import com.eweb.model.Products;
import com.eweb.model.Status;

import jakarta.transaction.Transactional;
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
@Autowired
CAddressRepository cAddressRepository;

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

	public ResponseEntity<?>  getLowStockProducts(long value) {
		List<ProductVariant> byVariantValue = productVariantRepository.findByLessValue(value);
		if(!byVariantValue.isEmpty()) {
			List<VariantDto> collect = byVariantValue.stream().map(data->{
				VariantDto dto =	new VariantDto(data);
				Optional<Products> byId = productsRepository.findById(data.getProductId());
				if(byId.isPresent()) {
					dto.setPName(byId.get().getName());
				}
				return dto;
			}).collect(Collectors.toList());
			 return ResponseEntity.ok( collect);
		}
		return ResponseEntity.badRequest()
                .body(new Status("400", "Data not  There"));
		
	}

   
	 public List<SalesChartDto> getLast7DaysSales() {

	        List<Object[]> result =ordersRepository.getLast7DaysSales();

	        return result.stream().map(obj -> new SalesChartDto( obj[0].toString(),((Number) obj[1]).doubleValue())).toList();
	    }

	    public List<SalesChartDto>  getLast30DaysSales() {

	        List<Object[]> result =ordersRepository.getLast30DaysSales();

	        return  result.stream().map(obj -> new SalesChartDto(obj[0].toString(),((Number) obj[1]).doubleValue())).toList();
	    }

		public ResponseEntity<?> getAllcustomers(String name, String mobile, String email, Pageable pageable) {  
			    Page<Customer> customers =customerRepository.searchCustomers(name , mobile ,email, pageable);
			    List<CustomerResponse> response =customers.getContent().stream().map(customer -> {
			                        CustomerResponse dto = new CustomerResponse();
			                        dto.setId(customer.getId());
			                        dto.setName(customer.getFullName());
			                        dto.setEmail(customer.getEmail());
			                        dto.setMobile(customer.getMobileNumber());
			                        dto.setStatus(customer.getStatus());
			                        Long totalOrders =ordersRepository.countByCustomerId( customer.getId());
			                        Double totalSpent =ordersRepository.getTotalSpent( customer.getId());
			                        dto.setTotalOrders(totalOrders);
			                        dto.setTotalSpent(totalSpent == null ? 0 : totalSpent);
			                        return dto;
			                    })
			                    .toList();
			    return ResponseEntity.ok(response);
			}
   
		public ResponseEntity<?> getCustomerDetails( Long customerId) {
		    Customer customer =customerRepository.findById(customerId)
		            .orElseThrow(() ->new RuntimeException("Customer Not Found"));
		    CustomerDetailsResponse dto =new CustomerDetailsResponse();
		    dto.setId(customer.getId());
		    dto.setName(customer.getFullName());
		    dto.setEmail(customer.getEmail());
		    dto.setMobile(customer.getMobileNumber());
		    Optional<CAddress> byUserAddress = cAddressRepository.findByUser(customerId);
		    if(byUserAddress.isPresent()) {
		    dto.setAddress(byUserAddress.get().getAddressLine1());
		    dto.setCity(byUserAddress.get().getCity());
		    dto.setState(byUserAddress.get().getState());
		    dto.setPinCode(byUserAddress.get().getPincode());
		    
		    }		 
		    dto.setStatus(customer.getStatus());
		    List<OrderHistoryDto> orderHistory =ordersRepository.findByUserIdOrderByIdDesc(customerId)
		            .stream().map(order -> {
		                OrderHistoryDto item =new OrderHistoryDto();
		                item.setOrderId(order.getId());
		                item.setDate(order.getOrderDate());
		                item.setAmount(order.getTotalAmount());
		                item.setStatus(order.getOrderStatus());
		                return item;
		            }).toList();
		    dto.setOrderHistory(orderHistory);
		    return ResponseEntity.ok(dto);
		}
		@Transactional
		public ResponseEntity<?> blockCustomer(Long cId) {
		    Optional<Customer> byCustomer =customerRepository.findById(cId);
		    if(byCustomer.isPresent()) {
		    	Customer customer = byCustomer.get();
		    customer.setStatus("BLOCKED");
		    customerRepository.save(customer);
		    return ResponseEntity.ok(new Status( "200" , "Customer Blocked"));
		    }
		    else {
		    	 return ResponseEntity.ok(new Status("400" ,"Customer Not Found"));
		    }
		}
		
		@Transactional
		public ResponseEntity<?> unBlockCustomer(Long id){
			  Optional<Customer> byCustomer =customerRepository.findById(id);
			    if(byCustomer.isPresent()) {
			    	Customer customer = byCustomer.get();
		    customer.setStatus("ACTIVE");

		    customerRepository.save(customer);
		    return ResponseEntity.ok(new Status( "200" ,"Customer Activated"));
			    }
			    else {
			    	 return ResponseEntity.ok(new Status("400" ,"Customer Not Found"));
			    }
		}
}
