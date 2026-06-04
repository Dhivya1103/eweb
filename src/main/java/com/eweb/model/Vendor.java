package com.eweb.model;

import com.eweb.dto.VendorDto;

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
@Table(name = "vendor")
public class Vendor {
	 @Id 
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long vendorId;
	 @Column
	    private String vendorName;
	 @Column
	    private String contactPerson;
	 @Column
	    private String phone;
	 @Column
	    private String email;
	 @Column
	    private String address;
	 @Column
	    private String gstNumber;
	 @Column
	    private String status;
	 public Vendor(VendorDto dto) {
	
		this.vendorName = dto.getVendorName();
		this.contactPerson = dto.getContactPerson();
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.address = dto.getAddress();
		this.gstNumber = dto.getGstNumber();
		this.status = dto.getStatus();
	 }
	 
	 
}
