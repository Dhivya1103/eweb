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
@Table(name = "vendor_payment")
public class VendorPayment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long vendorId;

    @Column
    private Long purchaseOrderId;

    @Column
    private Double amount;

    @Column
    private String paymentMode;

    @Column
    private String paymentStatus;

    @Column
    private LocalDateTime paymentDate;
}
