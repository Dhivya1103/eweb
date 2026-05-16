package com.eweb.service;

import org.springframework.stereotype.Service;

import com.eweb.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	 private final TwilioConfig config;

	    public SmsService(TwilioConfig config) {
	        this.config = config;
	        Twilio.init(config.accountSid, config.authToken);
	    }

	    public void sendOtp(String mobile, String otp) {

	        Message.creator(
	                new PhoneNumber("+91" + mobile),
	                new PhoneNumber(config.phoneNumber),
	                "Your OTP is: " + otp
	        ).create();

	        System.out.println("OTP sent to " + mobile);
	    }
}
