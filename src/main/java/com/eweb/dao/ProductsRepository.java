package com.eweb.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.dto.ProductList;
import com.eweb.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{
	
@Query(value= "select p.id as pId , p.name as pName , p.description as description , p.category as cId , p.sub_category as sCid , p.price as price , p.discount as discount , p.stock as stock ,p.image_url as image , c.name as cName , s.name as sName from products p left Join category c on c.id = p.category left join subcategory s on s.id =p.sub_category  WHERE (:categoryId IS NULL OR p.category = :categoryId) "
		+ " AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
		+ "  AND (:minPrice IS NULL OR p.price >= :minPrice) "
		+ "  AND (:maxPrice IS NULL OR p.price <= :maxPrice) " , nativeQuery = true)
	Page<ProductList> findLatestProduct( @Param("categoryId") Long categoryId,
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice, Pageable pageable);

@Query(value= "select p.id as pId , p.name as pName , p.description as description , p.category as cId , p.sub_category as sCid , p.price as price , p.discount as discount , p.stock as stock ,p.image_url as image , c.name as cName , s.name as sName from products p left Join category c on c.id = p.category left join subcategory s on s.id =p.sub_category left join favorite f on f.product_id = p.id WHERE customer_id = :userId ", nativeQuery = true)
Page<ProductList> findFavoriteProductsByUser(Long userId, Pageable pageable);

}
