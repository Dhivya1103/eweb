package com.eweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
		@Id
	  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		@Column
		private Long userId;
		@Column
	    private Long productId;
		@Column
	    private Long quantity;
		@Column 
		private Long variantId;
}
