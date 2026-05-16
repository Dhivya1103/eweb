package com.eweb.model;

import java.time.LocalDateTime;

import com.eweb.dto.ReviewDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
public class Review {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	@Column
	    private Long productId;
	    @Column 
	    private Long rating;
	    @Column 
	    private String comment;
	    @Column 
	    private LocalDateTime createdAt;
		public Review(ReviewDto dto) {			
			this.id = dto.getId();
			this.productId = dto.getProductId();
			this.rating = dto.getRating();
			this.comment = dto.getComment();
		
		}
	    
	    
}
