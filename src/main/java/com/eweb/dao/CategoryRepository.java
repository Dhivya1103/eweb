package com.eweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
