package com.eweb.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QualityCheckDto {
	
	    private Long id;
	
	    private Long purchaseOrderId;
	
	    private Long receivedQty;
	
	    private Long damagedQty;
	 
	    private Long acceptedQty;
	
	    private String remarks;
}
