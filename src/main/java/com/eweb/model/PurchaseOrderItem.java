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
@Table(name = "purchase_order_item")
public class PurchaseOrderItem {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column
	    private Long purchaseOrderId;

	    @Column
	    private Long productId;

	    @Column
	    private Long variantId;

	    @Column
	    private Long quantity;

	    @Column
	    private Double unitPrice;

	    @Column
	    private Double totalPrice;
}
