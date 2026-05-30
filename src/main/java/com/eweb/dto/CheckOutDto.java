package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckOutDto {
	private Long userId;
    private Long addressId;
    private String paymentMethod;
}
