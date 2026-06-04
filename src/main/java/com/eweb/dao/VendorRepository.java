package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
