package com.eweb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "vendor_invoice")
public class VendorInvoice {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 @Column
	    private Long purchaseOrderId;
	 @Column
	    private String invoiceNumber;
	 @Column
	    private LocalDate invoiceDate;
	 @Column
	    private Double gstAmount;
	 @Column
	    private Double totalAmount;
	 @Column
	    private String status;
}
