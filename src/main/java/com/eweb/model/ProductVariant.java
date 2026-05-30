package com.eweb.model;

import java.time.LocalDateTime;

import com.eweb.dto.VariantDto;

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
@Table(name = "productVariant")
public class ProductVariant {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	@Column
	    private String color;
	 	@Column
	    private String size;
	 	@Column
	    private Long stock;
	 	@Column
	    private Double price;
	 	@Column
	 	private Long productId;
		public ProductVariant(VariantDto dto) {
		
			this.color = dto.getColor();
			this.size = dto.getSize();
			this.stock = dto.getStock();
			this.price = dto.getPrice();
			this.productId = dto.getProductId();
		}
	 	
	 	
}
