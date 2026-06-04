package com.eweb.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.eweb.model.PurchaseOrder;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderResponse {
	 private Long id;	   
	    private String poNumber;	    
	    private Long vendorId;	    
	    private Double totalAmount;	    
	    private String status;	    
	    private LocalDateTime createdAt;
	    private List<PurchaseOrderItemRequest>items;
		public PurchaseOrderResponse(PurchaseOrder dto) {
			super();
			this.id = dto.getId();
			this.poNumber = dto.getPoNumber();
			this.vendorId = dto.getVendorId();
			this.totalAmount = dto.getTotalAmount();
			this.status = dto.getStatus();
			this.createdAt = dto.getCreatedAt();
		}
	    
	    
}
