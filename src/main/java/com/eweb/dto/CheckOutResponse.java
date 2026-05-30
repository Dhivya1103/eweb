package com.eweb.dto;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckOutResponse {
	private boolean success;
    private String message;
    private Object data;
	public CheckOutResponse(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
    
}
