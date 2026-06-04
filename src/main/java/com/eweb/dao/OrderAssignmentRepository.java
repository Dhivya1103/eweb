package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eweb.model.OrderAssignment;

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {
	 @Query(value ="SELECT COUNT(*) FROM order_assignmnet a WHERE a.agent_id=:id AND DATE(a.assigned_date)=CURRENT_DATE " , nativeQuery =true)
	Long countTodayOrders(Long id);

}
