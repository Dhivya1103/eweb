package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.VendorPayment;

@Repository
public interface VendorPaymentRepository extends JpaRepository<VendorPayment, Long> {

}
