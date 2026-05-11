package com.eweb.model;

import java.time.LocalDateTime;

import com.eweb.dto.ProductDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Products {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	@Column
	    private String name;
	 	@Column
	    private String description;	    
	    @Column
	    private Long category;
	   @Column
	    private Long subCategory;
	    @Column
	    private Double price;
	    @Column
	    private Double discount;
	    @Column
	    private Long stock;
	    @Column
	    private String imageUrl;
	    @Column
	    private LocalDateTime createdAt;
	    @Column
	    private LocalDateTime updatedAt;
		public Products(ProductDto dto) {				
			this.name = dto.getName();
			this.description = dto.getDescription();			
			this.category = dto.getCategoryId();			
			this.subCategory = dto.getSubCategoryId();
			this.price = dto.getPrice();
			this.discount = dto.getDiscount();
			this.stock = dto.getStock();
			this.imageUrl = dto.getImageUrl();
			
		}
	    
	    
}
