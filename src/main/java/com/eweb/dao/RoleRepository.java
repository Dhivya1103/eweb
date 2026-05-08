package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
