package com.eweb.dto;

import com.eweb.model.SubCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoryDto {
	private Long id;

	private String name;

	private Long categoryId;

	public SubCategoryDto(SubCategory model) {
		super();
		this.id = model.getId();
		this.name = model.getName();
		this.categoryId = model.getCategoryId();
	}
	

}
