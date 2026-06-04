package com.eweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eweb.dao.AdminRepository;
import com.eweb.dto.AgentDto;
import com.eweb.dto.AssignOrderDto;
import com.eweb.model.Admin;
import com.eweb.service.AdminAgentService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdminAgentController {
	@Autowired
	private  AdminAgentService adminAgentService;

	@Autowired
	AdminRepository adminRepository;
	
    @PostMapping("/saveAgents")
    public ResponseEntity<?> create(@RequestBody AgentDto dto  ,  Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.createAgent(dto);
    }

    @GetMapping("/findAllAgents")
    public ResponseEntity<?> getAll(Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.getAllAgents();
    }

    @GetMapping("/getAgents")
    public ResponseEntity<?> getById(@RequestParam (value="id",required =true) Long id ,Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.getAgent(id);
    }

    @PutMapping("/updateAgents")
    public ResponseEntity<?> update(@RequestBody AgentDto dto ,Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.updateAgent(dto);
    }

    @DeleteMapping("/deleteAgents")
    public ResponseEntity<?> delete( @RequestParam(value="id" , required =true) Long id , Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.deleteAgent(id);
    }

    @GetMapping("/agents/dropdown")
    public ResponseEntity<?> dropdown(Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.getAgentDropdown();
    }
//unassignedorder api
    @GetMapping("/orders/unassigned")
    public ResponseEntity<?> orders(Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.getOrders();
    }

    @PostMapping("/order-assignments")
    public ResponseEntity<?> assign( @RequestBody AssignOrderDto dto ,Authentication authentication){
    	UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		Optional<Admin> user = adminRepository.findByUsername(userPrincipal.getUsername());	
        return adminAgentService.assignOrder(dto);
    }
}
