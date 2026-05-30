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
@Table(name = "order_details")
public class Order {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    @Column
    private String orderNumber;
    @Column
    private Long userId;
    @Column
    private Double totalAmount;
    @Column
    private String paymentMethod;
    @Column
    private String paymentStatus;
    @Column
    private String orderStatus;
    @Column
    private LocalDateTime orderDate;
    @Column
    private String paymentId;
    @Column
    private String refundId;
    @Column
    private String trackingId;
    @Column
    private String courierName;
    @Column
private String trackingUrl;
    @Column
private String shipmentStatus;
    @Column
private LocalDateTime shippedAt;
   
    
}
