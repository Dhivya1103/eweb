package com.eweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	@Query(value="select * from order_details o where o.user_id = :userId ", nativeQuery=true)
		List<Order> findByUserIdOrderByIdDesc(@Param("userId") Long userId);

	Optional<Order> findByTrackingId(String awb);
	@Query(value =" SELECT DATE(order_date) as salesDate, SUM(total_amount) as totalSales FROM order_details WHERE order_date >= CURRENT_DATE - INTERVAL '7 days' " 
			+ " GROUP BY DATE(order_date) ORDER BY salesDate ", nativeQuery = true)
	List<Object[]> getLast7DaysSales();

	@Query(value = "SELECT DATE(order_date) as salesDate,SUM(total_amount) as totalSales FROM order_details WHERE order_date >= CURRENT_DATE - INTERVAL '30 days' "
			+ " GROUP BY DATE(order_date) ORDER BY salesDate ", nativeQuery = true)
	List<Object[]> getLast30DaysSales();
	@Query(value="select count(*) from order_details o where o.user_id = :id ", nativeQuery=true)
	Long countByCustomerId(Long id);

	@Query(value="select sum(o.total_amount) from order_details o where o.user_id = :id ", nativeQuery=true)
	Double getTotalSpent(Long id);
	@Query(value =" SELECT *FROM order_details o WHERE o.order_status = 'READY_FOR_DELIVERY' AND o.id NOT IN ( SELECT oa.order_id FROM order_assignmnet oa ) ", nativeQuery = true)
	List<Order>  getUnAssignedOrders();

	
}
