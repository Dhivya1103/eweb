package com.eweb.model;

import java.time.LocalDateTime;

import com.eweb.dto.QualityCheckDto;

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
@Table(name = "quality_check")
public class QualityCheck {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 @Column
	    private Long purchaseOrderId;
	 @Column
	    private Long receivedQty;
	 @Column
	    private Long damagedQty;
	 @Column
	    private Long acceptedQty;
	 @Column
	    private String remarks;
	 public QualityCheck(QualityCheckDto dto) {
		
		this.purchaseOrderId = dto.getPurchaseOrderId();
		this.receivedQty = dto.getReceivedQty();
		this.damagedQty = dto.getDamagedQty();
		this.acceptedQty = dto.getAcceptedQty();
		this.remarks = dto.getRemarks();
	 }
	 
	 
}
