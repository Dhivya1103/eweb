package com.eweb.dto;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoAutoStart 

public class VendorPaymentRequest {
	  private Long vendorId;

	    private Long purchaseOrderId;

	    private Double amount;

	    private String paymentMode;
}
