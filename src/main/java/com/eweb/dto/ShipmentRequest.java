package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentRequest {
	private Long orderId;
    private String customerName;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;
}
