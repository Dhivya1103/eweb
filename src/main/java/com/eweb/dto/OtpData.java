package com.eweb.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtpData {
	 private String otp;
	    private LocalDateTime expiryTime;

	    public OtpData(String otp, LocalDateTime expiryTime) {
	        this.otp = otp;
	        this.expiryTime = expiryTime;
	    }

}
