package com.eweb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.Admin;
import com.eweb.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByEmail(String email);
	
	   Optional<Customer> findByMobileNumber(String mobileNumber);
	   
	   @Query(value = "SELECT *  FROM customer u WHERE u.full_name =:username ", nativeQuery = true)
		Optional<Customer> findByUsername(@Param("username") String username);
}
