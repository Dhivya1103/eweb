package com.eweb.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	    public ResponseEntity<Map<String, Object>> handle405(HttpServletRequest request) {

	        Map<String, Object> res = new HashMap<>();

	        res.put("status", 405);
	        res.put("error", "Method Not Allowed");
	        res.put("message", "Wrong HTTP method used");
	        res.put("path", request.getRequestURI());

	        return ResponseEntity.status(405).body(res);
	    }
}
