package com.eweb.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseReturnDto {
	 
	    private Long id;	    
	    private Long purchaseOrderId;	    
	    private Long variantId;	    
	    private Long returnQty;	    
	    private String reason;
}
	   
