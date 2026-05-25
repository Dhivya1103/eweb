package com.eweb.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eweb.dao.CustomerRepository;
import com.eweb.dto.AdminDto;
import com.eweb.dto.DashboardproductDto;
import com.eweb.dto.LoginRequest;
import com.eweb.model.Admin;
import com.eweb.model.Customer;
import com.eweb.model.Role;
import com.eweb.model.Status;
import com.eweb.util.JwtUtil;

@Service
public class CustomerService {
	@Autowired	
	CustomerRepository customerRepository;
	  @Autowired
	    private PasswordEncoder passwordEncoder;
	  
	    @Autowired
	    private JwtUtil jwtUtil;
	    
	    @Autowired
	    EmailService emailService;
	
	  public ResponseEntity<?> cusSignup(AdminDto request) {
	        // Check if email already exists
	        if(customerRepository.findByEmail(request.getEmail()).isPresent()) {
	        	return ResponseEntity.badRequest().body(new Status("400", "Error:Email already registered"));
	        }
	        // Check if mobile already exists
	        if(customerRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
	        	return ResponseEntity.badRequest().body(new Status("400", "Error:Mobile number already registered"));
	        }
	        // Check password match
	        if(!request.getPassword().equals(request.getConfirmPassword())) {
	        	return ResponseEntity.badRequest().body(new Status("400", "Error:Password and Confirm Password do not match"));
	        }      
	  
	        Customer admin = new Customer();
	        admin.setFullName(request.getFullName());
	        admin.setEmail(request.getEmail());
	        admin.setMobileNumber(request.getMobileNumber());
	        admin.setPassword(passwordEncoder.encode(request.getPassword()));    
	        customerRepository.save(admin);

	        return ResponseEntity.ok(new Status("200", "User registered successfully!"));
	        
	    }
	  public ResponseEntity<?> Customerlogin(LoginRequest request) {			
	        Optional<Customer> customer = customerRepository.findByEmail(request.getEmail());
	        
	        if(!customer.isPresent()) {
	        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
                      .body(new Status(
                              "403",
                              "Invalid email or password!"
                      ));
	        }	              
	        if (!passwordEncoder.matches(request.getPassword(), customer.get().getPassword())) {
	        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
                      .body(new Status(
                              "403",
                              "Invalid email or password!"
                      ));
	        }     	        	
	        String token = jwtUtil.generateToken(customer.get().getEmail(), request.isRememberMe());
	        return ResponseEntity.ok(Map.of(
	        	    "token", token,
	        	    "adminId", customer.get().getId(),
	        	    "email", customer.get().getEmail(),
	        	    "fullName", customer.get().getFullName()	        	   
	        	));
	    }
	  
	  public ResponseEntity<?> customerForgotPassword(String email) {
			 Optional<Customer> userOpt = customerRepository.findByEmail(email);
			    if(!userOpt.isPresent()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                             .body(new Status("403", "Email not found"));
			    }

			    // Generate 6-digit OTP
			    String otp = String.valueOf(new Random().nextInt(900000) + 100000);
			    Customer otpEntity = userOpt.get();			   
			    otpEntity.setOtp(otp);
			    otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(10));
			    customerRepository.save(otpEntity);
			    // Send OTP via email (pseudo code)
			    emailService.sendOtp(email, otp);

			    return ResponseEntity.ok(new Status("200", "otp send  successfully your registerd mail!"));
		}

		public ResponseEntity<?> customerVerifyotp(String email, String otp) {
			 Optional<Customer> admin = customerRepository.findByEmail(email);
			 Customer a = admin.get();
			 if (a.getExpiryTime() == null || a.getExpiryTime().isBefore(LocalDateTime.now())) {
			        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                             .body(new Status("401", "OTP has expired"));
			    } 
			 if(!admin.isPresent()) {
				 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(new Status("403", "Email not found"));
			 }
			 	if (otp.equals(admin.get().getOtp())) {
			 		return ResponseEntity.ok(new Status("200", "otp verified  successfully!"));
			 	} else {
			 		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                          .body(new Status("error", "Invalid OTP"));
			 	}
		}

		public ResponseEntity<?> customerResetpassword(AdminDto request) {
			 Optional<Customer> adminOpt = customerRepository.findByEmail(request.getEmail());

			    if (!adminOpt.isPresent()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                             .body(new Status("404", "Email not found"));
			    }
			    Customer a = adminOpt.get();
			    if (!request.getPassword().equals(request.getConfirmPassword())) {
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			                             .body(new Status("400", "Passwords do not match"));
			    }
			    a.setPassword(passwordEncoder.encode(request.getPassword()));			   
			    a.setOtp(null);
			    a.setExpiryTime(null);
			    customerRepository.save(a);
			    return ResponseEntity.ok(new Status("200", "Password reset successfully!"));
		}
		public ResponseEntity<?> getUserProfile(Long id) {
			Optional<Customer> byEmail = customerRepository.findById(id);
			if(byEmail.isPresent()) {
				AdminDto dto = new AdminDto(byEmail.get());
				return new ResponseEntity<AdminDto>(dto, HttpStatus.OK);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Status("404", "NoRecord Found"));
}
		}
		
}
