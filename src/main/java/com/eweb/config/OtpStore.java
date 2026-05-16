package com.eweb.config;

import java.util.concurrent.ConcurrentHashMap;

import com.eweb.dto.OtpData;

public class OtpStore {
	
	  public static ConcurrentHashMap<String, OtpData> store = new ConcurrentHashMap<>();
	
}
