package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor 

public class OrderDto {
	 private Long userId;

	    private Long addressId;

	    private String paymentMethod;

	    private String paymentStatus;
}
