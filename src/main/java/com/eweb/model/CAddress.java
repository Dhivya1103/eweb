package com.eweb.model;

import com.eweb.dto.CAddressDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "caddress")
public class CAddress {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column
    private Long userId;
	@Column
    private String fullName;
	@Column
    private String phone;
	@Column
    private String addressLine1;
	@Column
    private String addressLine2;
	@Column
    private String city;
	@Column
    private String state;
	@Column
    private String country;
	@Column
    private String pincode;
	@Column
    private String addressType;
	@Column
    private Boolean isDefault;
	public CAddress(CAddressDto dto)
	{
		this.userId = dto.getUserId();
		this.fullName = dto.getFullName();
		this.phone = dto.getPhone();
		this.addressLine1 = dto.getAddressLine1();
		this.addressLine2 = dto.getAddressLine2();
		this.city = dto.getCity();
		this.state = dto.getState();
		this.country = dto.getCity();
		this.pincode = dto.getPincode();
		this.addressType = dto.getAddressType();
		
	}
	
	
}
