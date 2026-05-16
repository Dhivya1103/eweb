package com.eweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    public String accountSid;

    @Value("${twilio.auth.token}")
    public String authToken;

    @Value("${twilio.phone.number}")
    public String phoneNumber;
}
