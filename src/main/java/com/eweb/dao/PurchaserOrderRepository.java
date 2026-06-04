package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.PurchaseOrder;

@Repository
public interface PurchaserOrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
