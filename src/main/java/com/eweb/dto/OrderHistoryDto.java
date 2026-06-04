package com.eweb.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
	  private Long orderId;
	    private LocalDateTime date;
	    private Double amount;
	    private String status;
}
