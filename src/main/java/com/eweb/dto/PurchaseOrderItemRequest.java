package com.eweb.dto;

import com.eweb.model.PurchaseOrderItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderItemRequest {
	private Long productId;

    private Long variantId;

    private Long quantity;

    private Double unitPrice;

	public PurchaseOrderItemRequest(PurchaseOrderItem dto) {
		this.productId = dto.getProductId();
		this.variantId = dto.getVariantId();
		this.quantity = dto.getQuantity();
		this.unitPrice = dto.getUnitPrice();
	}
    
    
}
