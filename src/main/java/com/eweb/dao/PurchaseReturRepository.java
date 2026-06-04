package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.PurchaseReturn;

@Repository
public interface PurchaseReturRepository extends JpaRepository<PurchaseReturn, Long>{

}
