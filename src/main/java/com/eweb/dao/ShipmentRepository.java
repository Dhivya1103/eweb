package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

}
