package com.eweb.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	   @Query(value = "SELECT *  FROM customer u WHERE (:name IS NULL OR LOWER(u.full_name) LIKE LOWER(CONCAT('%', :name, '%'))) and (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))  and (:mobile IS NULL OR LOWER(u.mobile_number) LIKE LOWER(CONCAT('%', :mobile, '%')))", nativeQuery = true)
	   Page<Customer> searchCustomers(String name, String mobile, String email, Pageable pageable);
}
