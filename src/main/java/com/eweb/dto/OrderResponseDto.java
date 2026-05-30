package com.eweb.dto;

import java.time.LocalDateTime;

import com.eweb.model.Order;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
	private Long id;
  
    private Long userId;
    
    private Double totalAmount;
    
    private String paymentMethod;
    
    private String paymentStatus;
    
    private String orderStatus;
    
    private LocalDateTime createdAt;
    
    private String paymentId;
    
    private String refundId;
    private String customerName;

	public OrderResponseDto(Order model) {
		this.id = model.getId();
		this.userId = model.getUserId();
		this.totalAmount = model.getTotalAmount();
		this.paymentMethod = model.getPaymentMethod();
		this.paymentStatus = model.getPaymentStatus();
		this.orderStatus = model.getOrderStatus();
		this.createdAt = model.getOrderDate();
		this.paymentId = model.getPaymentId();
		this.refundId = model.getRefundId();
	}
    
    
}
