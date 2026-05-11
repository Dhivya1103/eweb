package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.model.favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<favorite, Long>{
	@Query(value = "SELECT count(*) FROM favorite WHERE Product_id = :proId", nativeQuery = true)
  Double favoriteCount(@Param ("proId") Long proId);
}
