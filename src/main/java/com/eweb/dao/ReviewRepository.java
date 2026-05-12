package com.eweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query(value = "SELECT count(*) FROM review WHERE Product_id = :proId", nativeQuery = true)
	  Double reviewCount(@Param ("proId") Long proId);
	@Query(value = "SELECT * FROM review WHERE Product_id = :productId ", nativeQuery = true)
	List<Review> findByProduct(@Param ("productId")Long productId);
	
}
