package com.eweb.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderRequsetDto {
	 private Long vendorId;

	    private List<PurchaseOrderItemRequest> items;
}
