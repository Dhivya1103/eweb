package com.eweb.model;

import java.time.LocalDateTime;

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
@Table(name = "shipment")
public class Shipment {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long orderId;

	    private String shipmentId;

	    private String awbCode;

	    private String courierName;

	    private String shipmentStatus;

	    private LocalDateTime createdAt;
	    private String trackingUrl;
}
