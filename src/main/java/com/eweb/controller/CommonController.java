package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.AdminRepository;
import com.eweb.model.Admin;
import com.eweb.model.Role;
import com.eweb.service.AdminService;


@RestController
@RequestMapping("/api")
public class CommonController {
	
	@Autowired
	AdminService adminService;
	
	 @Autowired
	    private AdminRepository adminRepository;
	
	 @PostMapping("/saveRole")
	    public  ResponseEntity<?>  saveRole(@RequestBody Role role,Authentication authentication) {
		 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

	        Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());

	        return adminService.saveRole(role);
	    }

	    // Get all roles
	    @GetMapping("/findAllRole")
	    public  ResponseEntity<?>  getAllRoles(Authentication authentication) {
	    	 UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

	         Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());

	        return adminService.getAllRoles();
	    }
}
