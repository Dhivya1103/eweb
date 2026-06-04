package com.eweb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eweb.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>{
@Query(value="select * from coupon where coupon_code = :couponCode " , nativeQuery =true)
	Optional<Coupon> findByCouponCode(String couponCode);

}
