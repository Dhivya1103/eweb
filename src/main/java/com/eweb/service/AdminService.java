package com.eweb.service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eweb.dao.AdminRepository;
import com.eweb.dao.RoleRepository;
import com.eweb.dto.AdminDto;
import com.eweb.dto.LoginRequest;
import com.eweb.dto.RoleDto;
import com.eweb.model.Admin;
import com.eweb.model.Role;
import com.eweb.model.Status;
import com.eweb.util.JwtUtil;

@Service
public class AdminService {
	 @Autowired
	    private AdminRepository adminRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	     
	    @Autowired
	    private RoleRepository roleRepository;
	    
	    @Autowired
	    private JwtUtil jwtUtil;
	    @Autowired
	    EmailService emailService;

	    public ResponseEntity<?> signup(AdminDto request) {
	        // Check if email already exists
	        if(adminRepository.findByEmail(request.getEmail()).isPresent()) {
	        	return ResponseEntity.badRequest().body(new Status("400", "Error:Email already registered"));
	        }
	        // Check if mobile already exists
	        if(adminRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
	        	return ResponseEntity.badRequest().body(new Status("400", "Error:Mobile number already registered"));
	        }
	        // Check password match
	        if(!request.getPassword().equals(request.getConfirmPassword())) {
	        	return ResponseEntity.badRequest().body(new Status("400", "Error:Password and Confirm Password do not match"));
	        }
	        Optional<Role> role = roleRepository.findById(request.getRoleId());
	        if(role.isPresent()) {
	        // Save admin
	        Admin admin = new Admin();
	        admin.setFullName(request.getFullName());
	        admin.setEmail(request.getEmail());
	        admin.setMobileNumber(request.getMobileNumber());
	        admin.setPassword(passwordEncoder.encode(request.getPassword()));
	        admin.setRoleId(request.getRoleId());
	        admin.setStatus(request.getStatus());

	        adminRepository.save(admin);}

	        return ResponseEntity.ok(new Status("200", "User registered successfully!"));
	        
	    }
	    
	    // Save Role
	    public ResponseEntity<?> saveRole(Role role) {
	         Role save = roleRepository.save(role);
	         return ResponseEntity.ok(new Status("200", "role registered successfully!"));
	    }

	    // Find all roles
	    public ResponseEntity<?> getAllRoles() {
	        List<Role> all = roleRepository.findAll();
	        List<RoleDto> collect = all.stream().map(data-> new RoleDto(data)).collect(Collectors.toList());
	        return new ResponseEntity<>(collect, HttpStatus.OK);
	    }

		public ResponseEntity<?> login(LoginRequest request) {			
			        Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
			        
			        if(!admin.isPresent()) {
			        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new Status(
                                        "403",
                                        "Invalid email or password!"
                                ));
			        }
			              

			        if (!passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) {
			        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new Status(
                                        "403",
                                        "Invalid email or password!"
                                ));
			        }
			        Optional<Role> byId = roleRepository.findById(admin.get().getRoleId());
			   String roleName =null;
			        if(byId.isPresent()) {
			        	roleName=byId.get().getName();
			        }
			        	
			        String token = jwtUtil.generateToken(admin.get().getEmail(), request.isRememberMe());
			        return ResponseEntity.ok(Map.of(
			        	    "token", token,
			        	    "adminId", admin.get().getId(),
			        	    "email", admin.get().getEmail(),
			        	    "fullName", admin.get().getFullName(),
			        	    "role", roleName
			        	));
			    }

		public ResponseEntity<?> forgetPassword(String email) {
			 Optional<Admin> userOpt = adminRepository.findByEmail(email);
			    if(!userOpt.isPresent()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                             .body(new Status("403", "Email not found"));
			    }

			    // Generate 6-digit OTP
			    String otp = String.valueOf(new Random().nextInt(900000) + 100000);
			    Admin otpEntity = userOpt.get();			   
			    otpEntity.setOtp(otp);
			    otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(10));
			    adminRepository.save(otpEntity);
			    // Send OTP via email (pseudo code)
			    emailService.sendOtp(email, otp);

			    return ResponseEntity.ok(new Status("200", "otp send  successfully your registerd mail!"));
		}

		public ResponseEntity<?> verifyOtp(String email, String otp) {
			 Optional<Admin> admin = adminRepository.findByEmail(email);
			 Admin a = admin.get();
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

		public ResponseEntity<?> resetPassword(AdminDto request) {
			 Optional<Admin> adminOpt = adminRepository.findByEmail(request.getEmail());

			    if (!adminOpt.isPresent()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                             .body(new Status("404", "Email not found"));
			    }
			    Admin admin = adminOpt.get();
			    if (!request.getPassword().equals(request.getConfirmPassword())) {
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			                             .body(new Status("400", "Passwords do not match"));
			    }
			    admin.setPassword(passwordEncoder.encode(request.getPassword()));			   
			    admin.setOtp(null);
			    admin.setExpiryTime(null);
			    adminRepository.save(admin);
			    return ResponseEntity.ok(new Status("200", "Password reset successfully!"));
		}
		
		
}
