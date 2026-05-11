package com.eweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.SubCategory;



@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{
	
	@Query(value = "SELECT * FROM subcategory WHERE category_id = :moduleId", nativeQuery = true)
	List<SubCategory> findByCategoryId(@Param("moduleId") Long moduleId);

	Optional<SubCategory> findByNameIgnoreCaseAndCategoryId(String name, Long categoryId);

}
