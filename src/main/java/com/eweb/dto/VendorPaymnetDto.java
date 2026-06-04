package com.eweb.dto;

import java.time.LocalDateTime;

import com.eweb.model.VendorPayment;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoAutoStart
public class VendorPaymnetDto {
	 private Long id;	    
	    private Long vendorId;	    
	    private Long purchaseOrderId;	    
	    private Double amount;	    
	    private String paymentMode;	    
	    private String paymentStatus;
	    LocalDateTime paymentDate;
		public VendorPaymnetDto(VendorPayment model) {
			this.id = model.getId();
			this.vendorId = model.getVendorId();
			this.purchaseOrderId = model.getPurchaseOrderId();
			this.amount = model.getAmount();
			this.paymentMode = model.getPaymentMode();
			this.paymentStatus = model.getPaymentStatus();
			this.paymentDate = model.getPaymentDate();
		}
	    
}
