package com.eweb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eweb.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{

	Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);

}
