package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.VendorInvoice;

@Repository
public interface VendorInvoiceRepository extends JpaRepository<VendorInvoice, Long>{

}
