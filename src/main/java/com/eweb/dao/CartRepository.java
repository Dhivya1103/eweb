package com.eweb.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eweb.dto.CartList;
import com.eweb.model.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUserIdAndProductId(Long userId, Long productId);
	
	@Query(value="SELECT p.id as pId , p.name as pName ,p.image_url  as image ,p.price as pPrice ,pv.id as variantId , pv.size as size , pv.color  as color, pv.price as basedamount  , c.quantity as quantity , p.discount  as discount from products p left join product_variant pv  on pv.product_id =p.id left join  cart c  on c.product_id =p.id  and c.variant_id =pv.id  where c.user_id =:userId ", nativeQuery= true)
	Page<CartList>findCartList(@Param("userId") Long userId, Pageable pageable );

	@Query(value="select * from cart c where c.user_id =:userId and c.product_id =:productId and c.variant_id =:variantId " , nativeQuery = true)
	Cart findByUserIdAndProductIdAndVariantId(Long userId, Long productId, Long variantId);

	List<Cart> findByUserId(Long userId);

}
