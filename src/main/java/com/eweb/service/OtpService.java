package com.eweb.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eweb.config.OtpStore;
import com.eweb.dto.OtpData;

@Service
public class OtpService {
	@Autowired
	private SmsService smsService;
	 private static final int OTP_VALID_MINUTES = 5;

	    public String generateOtp(String mobile) {

	        String otp = String.valueOf(100000 + new Random().nextInt(900000));

	        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_VALID_MINUTES);

	        OtpStore.store.put(mobile, new OtpData(otp, expiryTime));

	        System.out.println("OTP for " + mobile + " is " + otp);
	        smsService.sendOtp(mobile, otp);

	        return otp;
	    }
	    
	    public boolean verifyOtp(String mobile, String inputOtp) {

	        if (!OtpStore.store.containsKey(mobile)) {
	            return false;
	        }
	        OtpData data = OtpStore.store.get(mobile);
	        // Expired check
	        if (LocalDateTime.now().isAfter(data.getExpiryTime())) {
	            OtpStore.store.remove(mobile);
	            return false;
	        }
	        // OTP match check
	        if (data.getOtp().equals(inputOtp)) {
	            OtpStore.store.remove(mobile); // one-time use
	            return true;
	        }
	        return false;
	    }
}
