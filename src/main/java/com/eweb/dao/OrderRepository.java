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

}
