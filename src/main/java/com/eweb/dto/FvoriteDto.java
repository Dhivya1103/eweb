package com.eweb.dto;

import java.time.LocalDateTime;

import com.eweb.model.favorite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FvoriteDto {
	private Long id;
	private Long productId;
	private Long customerId;
	private LocalDateTime createdAt;
	public FvoriteDto(favorite model) {
	
		this.id = model.getId();
		this.productId = model.getProductId();
		this.customerId = model.getCustomerId();
		this.createdAt = model.getCreatedAt();
	} 

	
}
