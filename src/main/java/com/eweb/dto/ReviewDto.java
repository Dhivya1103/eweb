package com.eweb.dto;

import java.time.LocalDateTime;

import com.eweb.model.Review;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
	private Long id; 
    private Long productId;     
    private Long rating;     
    private String comment;     
    private LocalDateTime createdAt;
	public ReviewDto(Review model) {		
		this.id = model.getId();
		this.productId = model.getProductId();
		this.rating = model.getRating();
		this.comment = model.getComment();
		this.createdAt = model.getCreatedAt();
	}
    
    
}
