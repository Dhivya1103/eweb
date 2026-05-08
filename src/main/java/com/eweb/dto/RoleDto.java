package com.eweb.dto;

import com.eweb.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {
private Long id;
private String name;

public RoleDto(Role role) {
	
	this.id = role.getId();
	this.name = role.getName();
}

}
