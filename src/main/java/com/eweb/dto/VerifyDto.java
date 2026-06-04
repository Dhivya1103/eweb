package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor 

public class VerifyDto {
	 private String razorpayOrderId;

	    private String razorpayPaymentId;

	    private String razorpaySignature;
	    private String paymentMethod;
	    private String couponCode;
}
