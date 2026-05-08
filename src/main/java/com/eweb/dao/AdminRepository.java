package com.eweb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Optional<Admin> findByEmail(String email);
    Optional<Admin> findByMobileNumber(String mobileNumber);
    
    @Query(value = "SELECT *  FROM admin u WHERE u.full_name =:username and (u.status='' OR u.status='A') ", nativeQuery = true)
	Optional<Admin> findByUsername(@Param("username") String username);
}
