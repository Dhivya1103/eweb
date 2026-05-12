package com.eweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.ProductVariant;
import com.eweb.model.Review;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>{
	@Query(value = "SELECT * FROM product_variant WHERE product_id = :productId ", nativeQuery = true)
	List<ProductVariant> findByProduct(@Param ("productId")Long productId);

}
