package com.eweb.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class VendorInvoiceDto {
	private Long id;
	  private Long purchaseOrderId;

	    private String invoiceNumber;

	    private LocalDate invoiceDate;

	    private Double gstAmount;

	    private Double totalAmount;
}
