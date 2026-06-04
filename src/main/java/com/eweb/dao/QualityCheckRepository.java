package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.QualityCheck;

@Repository
public interface QualityCheckRepository extends JpaRepository<QualityCheck, Long> {

}
