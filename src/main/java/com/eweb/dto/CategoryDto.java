package com.eweb.dto;

import com.eweb.model.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private Long id;
	private String name;
	public CategoryDto(Category model) {
		super();
		this.id = model.getId();
		this.name = model.getName();
	}
	
}
