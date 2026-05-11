package com.eweb.model;

import com.eweb.dto.SubCategoryDto;

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
@Table(name = "subcategory")
public class SubCategory {
	 	@Id 
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   @Column
	    private Long categoryId;
	    @Column
	    private String name;
		public SubCategory(SubCategoryDto dto) {
			super();
			this.id = dto.getId();				
			this.name = dto.getName();
			this.categoryId = dto.getCategoryId();
		}
	    
	    
	    
	    
}
