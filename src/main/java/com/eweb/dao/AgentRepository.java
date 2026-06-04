package com.eweb.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eweb.dto.AgentResponseDto;
import com.eweb.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

	@Query(value="select * from delivery_agent a where a.status =:status " , nativeQuery =true)
	List<Agent> findByStatus(String status);

}
