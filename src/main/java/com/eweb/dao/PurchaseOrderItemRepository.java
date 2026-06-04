package com.eweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eweb.model.PurchaseOrderItem;
@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {

	@Query(value = "select * from purchase_order_item p where p.purchase_order_id = :id " , nativeQuery = true)
	List<PurchaseOrderItem> findByPoItems(Long id);
	@Query(value = "select * from purchase_order_item p where p.purchase_order_id = :purchaseOrderId AND p.variant_id = :variantId " , nativeQuery = true)
	Optional<PurchaseOrderItem> findByPoAndVariant(Long purchaseOrderId, Long variantId);

}
