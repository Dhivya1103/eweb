package com.eweb.dto;

import com.eweb.model.Vendor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VendorDto {
	private Long id;
	private String vendorName;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
    private String gstNumber;
    private String status;
    
    public VendorDto(Vendor dto) {
    	this.id=dto.getVendorId();
		this.vendorName = dto.getVendorName();
		this.contactPerson = dto.getContactPerson();
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.address = dto.getAddress();
		this.gstNumber = dto.getGstNumber();
		this.status = dto.getStatus();
	 }
}
