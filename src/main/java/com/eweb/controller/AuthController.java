package com.eweb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.CustomerRepository;
import com.eweb.dto.AdminDto;
import com.eweb.dto.LoginRequest;
import com.eweb.model.Customer;
import com.eweb.model.Role;
import com.eweb.model.Status;
import com.eweb.service.AdminService;
import com.eweb.service.CustomerService;
import com.eweb.service.OtpService;
import com.eweb.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	 @Autowired
	    private AdminService adminService;
	 @Autowired
	 private  CustomerService customerService;
	 @Autowired
	  private OtpService otpService;
	 @Autowired
	    private JwtUtil jwtUtil;
	 
	 @Autowired	
		CustomerRepository customerRepository;

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
	   
	    @PostMapping("/customerSignup")
	    public ResponseEntity<?> cusSignup(@RequestBody AdminDto request) {
	        return customerService.cusSignup(request);
	    }
	    
	    @PostMapping("/customerLogin")
	    public ResponseEntity<?> Customerlogin(@RequestBody LoginRequest request) {
	        try {
	            return customerService.Customerlogin(request);  // ✅ no extra wrapping
	        } 
	        catch (BadCredentialsException ex) {	           

	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(new Status("401", "Invalid username or password"));

	        }catch (Exception e) {
	        	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                     .body(new Status("500", "Something went wrong"));
	        }
	    }
	    @PostMapping("/customerForgot-password")
	    public ResponseEntity<?> customerForgotPassword(@RequestParam String email) {
	        return customerService.customerForgotPassword(email);
	    }
	    @PostMapping("/customerVerifyotp")
	    public ResponseEntity<?> customerVerifyotp(@RequestParam String email,@RequestParam String otp ) {
	        return customerService.customerVerifyotp(email,otp);
	    }
	    @PostMapping("/customerReset-password")
	    public ResponseEntity<?> customerResetpassword(@RequestBody AdminDto request ) {	    	
	        return customerService.customerResetpassword(request);
	    }
	    
	    @PostMapping("/sendMobileOtp")
	    public Map<String, String> sendOtp(@RequestParam String mobile) {

	        otpService.generateOtp(mobile);

	        Map<String, String> res = new HashMap<>();
	        res.put("message", "OTP sent successfully (valid 5 minutes)");

	        return res;
	    }
	    
	    @PostMapping("/verifyMobileOtp")
	    public ResponseEntity<Map<String, Object>> verifyMobileOtp( @RequestParam String mobile, @RequestParam String otp) {
try {
	        boolean valid = otpService.verifyOtp(mobile, otp);
	        Optional<Customer> customer = customerRepository.findByMobileNumber(mobile);
	        Customer c=null;
	        if(customer.isPresent()) {
	        customer.get();
	        }
	        Map<String, String> res = new HashMap<>();
	        if (valid) {
	        	  String token =jwtUtil.generateToken(mobile);
	        	  return ResponseEntity.ok(Map.of(
	  	        	    "token", token,
	  	        	    "adminId", c.getId(),
	  	        	    "email", c.getEmail(),
	  	        	    "fullName", c.getFullName()	        	   
	  	        	));
	          } 

	         
	    }
	    catch (BadCredentialsException ex) {           
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            		.body(Map.of(  "message","Invalid username or password"));

        } catch (Exception ex) {                

        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Something went wrong"
                    ));
        }
			return null;
}
}
