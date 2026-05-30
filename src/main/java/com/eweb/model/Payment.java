package com.eweb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private String id;
	  	@Column
	    private String razorpayOrderId;
	  	@Column
	    private String razorpayPaymentId;
	  	@Column
	    private String paymentStatus;
	  	@Column
	    private String paymentMethod;
}
