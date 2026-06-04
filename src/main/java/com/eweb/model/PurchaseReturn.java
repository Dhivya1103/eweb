package com.eweb.model;

import java.time.LocalDateTime;

import com.eweb.dto.PurchaseReturnDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "purchase_return")
public class PurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long purchaseOrderId;
    @Column
    private Long variantId;
    @Column
    private Long returnQty;
    @Column
    private String reason;
    @Column
    private LocalDateTime createdAt;
	public PurchaseReturn(PurchaseReturnDto dto) {		
		this.purchaseOrderId = dto.getPurchaseOrderId();
		this.variantId = dto.getVariantId();
		this.returnQty = dto.getReturnQty();
		this.reason = dto.getReason();
		
	}
    
    
}
