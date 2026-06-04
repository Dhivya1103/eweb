package com.eweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eweb.model.CAddress;

@Repository
public interface CAddressRepository extends JpaRepository<CAddress, Long>{

	List<CAddress> findByUserId(Long userId);

	@Query(value="select * from caddress  c where c.user_id = :customerId " , nativeQuery = true)
	Optional<CAddress> findByUser(Long customerId);

}
