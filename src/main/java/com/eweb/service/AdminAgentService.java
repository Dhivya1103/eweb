package com.eweb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.AgentRepository;
import com.eweb.dao.OrderAssignmentRepository;
import com.eweb.dao.OrderRepository;

import com.eweb.dto.AgentDto;
import com.eweb.dto.AgentResponseDto;
import com.eweb.dto.AssignOrderDto;
import com.eweb.dto.DropDownDto;
import com.eweb.dto.OrderDropDownDto;
import com.eweb.model.Agent;
import com.eweb.model.Order;
import com.eweb.model.OrderAssignment;
import com.eweb.model.Status;

import jakarta.transaction.Transactional;

@Service
public class AdminAgentService {
	@Autowired
	AgentRepository agentRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderAssignmentRepository orderAssignmentRepository;
	
	@Transactional
	public ResponseEntity<?> createAgent(AgentDto dto){
		Agent agent =new Agent();
	    agent.setAgentName(dto.getAgentName());
	    agent.setMobile(dto.getMobile());
	    agent.setEmail(dto.getEmail());
	    agent.setAddress(dto.getAddress());
	    agent.setVehicleType(dto.getVehicleType());
	    agent.setAssignedArea(dto.getAssignedArea());
	    agent.setStatus("ACTIVE");
	    agent.setCreatedDate(LocalDateTime.now());
	    agentRepository.save(agent);
	    return ResponseEntity.ok(new Status("200" , "Agent Created Successfully"));
	}
	
	public ResponseEntity<?> getAllAgents(){

	    List<AgentResponseDto> response =agentRepository.findAll()
	            .stream().map(agent -> {
	                AgentResponseDto dto =new AgentResponseDto();
	                dto.setId(agent.getId());
	                dto.setAgentName(agent.getAgentName());
	                dto.setMobile(agent.getMobile());
	                dto.setAssignedArea( agent.getAssignedArea());
	                dto.setStatus(agent.getStatus());
	                Long count =orderAssignmentRepository.countTodayOrders(agent.getId());
	                dto.setTodayOrders(count);
	                return dto;
	            }).toList();
	    return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> getAgent( Long id){
		Optional<Agent> agent =agentRepository.findById(id);
		if(agent.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404", "Agent not  present"));
		}              

	    return ResponseEntity.ok(agent);
	}
	@Transactional
	public ResponseEntity<?> updateAgent(AgentDto dto){
		Optional<Agent> agents =agentRepository.findById(dto.getId());
		if(agents.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404", "Agent not  present"));
		}   
		Agent agent = agents.get();
	    agent.setAgentName(dto.getAgentName());
	    agent.setMobile(dto.getMobile());
	    agent.setEmail(dto.getEmail());
	    agent.setAddress(dto.getAddress());
	    agent.setVehicleType(dto.getVehicleType());
	    agent.setAssignedArea(dto.getAssignedArea());
	    agentRepository.save(agent);
	    return ResponseEntity.ok(new Status ("200" ,"Agent Updated Successfully"));
	}
	
	@Transactional
	public ResponseEntity<?> deleteAgent(Long id){
		Optional<Agent> agents =agentRepository.findById(id);
		if(agents.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404", "Agent not  present"));
		}  
		agentRepository.delete(agents.get());
	    return ResponseEntity.ok(new Status("200" ,"Agent Deleted Successfully"));
	}
	
	public ResponseEntity<?> getAgentDropdown(){	   
	   List<Agent> byStatus = agentRepository.findByStatus("ACTIVE");
	   List<DropDownDto> list = byStatus.stream()
	            .map(data -> { DropDownDto dto = new DropDownDto();
	                dto.setId(data.getId());
	                dto.setName(data.getAgentName());
	                return dto;
	            }).toList();
	    return ResponseEntity.ok(list);
	}
//	unassigned order api
	public ResponseEntity<?> getOrders(){

	  List<Order> unAssignedOrders = orderRepository.getUnAssignedOrders();
	  List<OrderDropDownDto> list = unAssignedOrders.stream().map(order -> {
		  OrderDropDownDto dto =new OrderDropDownDto();

	                dto.setId(order.getId());
	                dto.setOrderNo(order.getOrderNumber());
	                return dto;
	            }).toList();
	    return ResponseEntity.ok(list);
	}
//	ASSIGN ORDER TO AGENT
	@Transactional
	public ResponseEntity<?> assignOrder( AssignOrderDto dto){
	    Optional<Order> byOrder = orderRepository.findById(dto.getOrderId());
	    if(byOrder.isEmpty()) {
	    	 return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404", "Order doesn't exist"));
	    }
	    Order order = byOrder.get();
	    Optional<Agent> agents =agentRepository.findById(dto.getAgentId());
		if(agents.isEmpty()) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new Status("404", "Agent not  present"));
		}  
		Agent agent = agents.get();
	    OrderAssignment assignment = new OrderAssignment();
	    assignment.setOrderId(order.getId());
	    assignment.setAgentId(agent.getId());
	    assignment.setAssignedDate(LocalDateTime.now());
	    assignment.setStatus("ASSIGNED");
	    orderAssignmentRepository.save(assignment);
	    order.setOrderStatus("ASSIGNED_TO_AGENT");
	    orderRepository.save(order);
	    return ResponseEntity.ok(new Status("200" ,"Order Assigned Successfully"));
	}
}
