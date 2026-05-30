package com.eweb.dto;

import com.eweb.model.CAddress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CAddressDto {
	private Long id;
	private Long userId;
	 private String fullName;

	    private String phone;

	    private String addressLine1;

	    private String addressLine2;

	    private String city;

	    private String state;

	    private String country;

	    private String pincode;

	    private String addressType;
	    private Boolean isDefault;
		public CAddressDto(CAddress model) {
			this.id=model.getId();
			this.userId = model.getUserId();
			this.fullName = model.getFullName();
			this.phone = model.getPhone();
			this.addressLine1 = model.getAddressLine1();
			this.addressLine2 = model.getAddressLine2();
			this.city = model.getCity();
			this.state = model.getState();
			this.country = model.getCountry();
			this.pincode = model.getPincode();
			this.addressType = model.getAddressType();
			this.isDefault = model.getIsDefault();
		}
	    
}
