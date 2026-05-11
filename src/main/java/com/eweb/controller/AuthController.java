package com.eweb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dto.AdminDto;
import com.eweb.dto.LoginRequest;
import com.eweb.model.Role;
import com.eweb.model.Status;
import com.eweb.service.AdminService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	 @Autowired
	    private AdminService adminService;

	    @PostMapping("/signup")
	    public ResponseEntity<?> signup(@RequestBody AdminDto request) {
	        return adminService.signup(request);
	    }
	    
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
	        try {
	            return adminService.login(request);  // ✅ no extra wrapping
	        } 
	        catch (BadCredentialsException ex) {	           

	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(new Status("401", "Invalid username or password"));

	        }catch (Exception e) {
	        	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                     .body(new Status("500", "Something went wrong"));
	        }
	    }
	    
	    @PostMapping("/forgot-password")
	    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
	        return adminService.forgetPassword(email);
	    }
	    @PostMapping("/verifyotp")
	    public ResponseEntity<?> verifyOtp(@RequestParam String email,@RequestParam String otp ) {
	        return adminService.verifyOtp(email,otp);
	    }
	    @PostMapping("/reset-password")
	    public ResponseEntity<?> resetPassword(@RequestBody AdminDto request ) {
	    	
	        return adminService.resetPassword(request);
	    }
	    @PostMapping("/saveRole")
	    public  ResponseEntity<?>  saveRole(@RequestBody Role role) {
	        return adminService.saveRole(role);
	    }
	   
}
