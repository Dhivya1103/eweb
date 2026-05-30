package com.eweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.CAddress;

@Repository
public interface CAddressRepository extends JpaRepository<CAddress, Long>{

	List<CAddress> findByUserId(Long userId);

}
