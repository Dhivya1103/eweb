package com.eweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    public String accountSid;

    @Value("${twilio.auth.token}")
    public String authToken;

    @Value("${twilio.phone.number}")
    public String phoneNumber;
    
    
    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
