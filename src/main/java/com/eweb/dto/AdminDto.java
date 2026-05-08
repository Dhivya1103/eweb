package com.eweb.dto;

import com.eweb.model.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDto {
	private String fullName;
    private String email;
    private String mobileNumber;
    private String password;
    private String confirmPassword;
    private Long roleId;
    private String roleName;
    private String status;
	public AdminDto(Admin admin) {
	
		this.fullName = admin.getFullName();
		this.email = admin.getEmail();
		this.mobileNumber = admin.getMobileNumber();
		this.roleId = admin.getRoleId();
		this.status=admin.getStatus();
		
	}
    
    

}
