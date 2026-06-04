package com.eweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {
	  private Long id;
	    private String name;
	    private String email;
	    private String mobile;
	    private Long totalOrders;
	    private Double totalSpent;
	    private String status;
}
