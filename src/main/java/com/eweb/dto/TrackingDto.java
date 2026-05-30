package com.eweb.dto;

import java.time.LocalDateTime;

import com.eweb.model.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrackingDto {	

	    private String status;

	    private String remarks;

	    private LocalDateTime createdAt;

		public TrackingDto(OrderStatus model) {
			super();
			this.status = model.getStatus();
			this.remarks = model.getRemarks();
			this.createdAt = model.getCreatedAt();
		}
	    
	    

	 
	
}
