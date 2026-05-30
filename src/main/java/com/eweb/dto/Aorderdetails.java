package com.eweb.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eweb.model.Customer;
import com.eweb.model.Order;
import com.eweb.model.Products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Aorderdetails {
	
		private Long orderId;
	  
	    private Long userId;
	    private String orderNumber;
	    
	    private Double totalAmount;
	    
	    private String paymentMethod;
	    
	    private String paymentStatus;
	    
	    private String orderStatus;
	    
	    private LocalDateTime orderDate;
	    
	    private String paymentId;
	    private String courierName;

	    private String trackingId;
	    
	    private String refundId;
	    private AdminDto customer;

	    private List<ProductDto> products;

	    private List<TrackingDto> tracking;
	   

		public Aorderdetails(Order model) {
			this.orderId = model.getId();
			this.userId = model.getUserId();
			this.totalAmount = model.getTotalAmount();
			this.paymentMethod = model.getPaymentMethod();
			this.paymentStatus = model.getPaymentStatus();
			this.orderStatus = model.getOrderStatus();
			this.orderDate = model.getOrderDate();
			this.paymentId = model.getPaymentId();
			this.refundId = model.getRefundId();
		}
}
