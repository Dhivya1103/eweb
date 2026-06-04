package com.eweb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.PurchaseOrderItemRepository;
import com.eweb.dao.PurchaseReturRepository;
import com.eweb.dao.PurchaserOrderRepository;
import com.eweb.dao.QualityCheckRepository;
import com.eweb.dto.PurchaseOrderItemRequest;
import com.eweb.dto.PurchaseOrderRequsetDto;
import com.eweb.dto.PurchaseOrderResponse;
import com.eweb.dto.PurchaseReturnDto;
import com.eweb.dto.QualityCheckDto;
import com.eweb.dto.VendorDto;
import com.eweb.model.ProductVariant;
import com.eweb.model.PurchaseOrder;
import com.eweb.model.PurchaseOrderItem;
import com.eweb.model.PurchaseReturn;
import com.eweb.model.QualityCheck;
import com.eweb.model.Status;
import com.eweb.model.Vendor;

import jakarta.transaction.Transactional;

@Service
public class PurchaseOrderService {
	 @Autowired
	    private PurchaserOrderRepository purchaseOrderRepository;

	    @Autowired
	    private PurchaseOrderItemRepository itemRepository;

	    @Autowired
	    private ProductVariantRepository variantRepository;
	    
	    @Autowired
	    QualityCheckRepository qualityCheckRepository;

	    @Autowired
	    PurchaseReturRepository purchaseReturRepository;
		

	    @Transactional
	    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrderRequsetDto request) {
	        PurchaseOrder po = new PurchaseOrder();
	        po.setVendorId(request.getVendorId());
	        po.setPoNumber("PO-" + System.currentTimeMillis());
	        po.setStatus("PENDING");	       
	        po.setCreatedAt(LocalDateTime.now());
	        PurchaseOrder savedPo =purchaseOrderRepository.save(po);
	        double total = 0;
	        for (PurchaseOrderItemRequest item :request.getItems()) {
	            PurchaseOrderItem poItem =new PurchaseOrderItem();
	            poItem.setPurchaseOrderId(savedPo.getId());
	            poItem.setProductId( item.getProductId());
	            poItem.setVariantId(item.getVariantId());
	            poItem.setQuantity(item.getQuantity());
	            poItem.setUnitPrice(item.getUnitPrice());
	            poItem.setTotalPrice(item.getQuantity() * item.getUnitPrice());
	            total += poItem.getTotalPrice();
	            itemRepository.save(poItem);
	        }

	        savedPo.setTotalAmount(total);
	        purchaseOrderRepository.save(savedPo);
	        return ResponseEntity.ok(new Status ("200", "po created successfully"));
	    }

	    public ResponseEntity<?> getAllPurchaseOrders() {
	    	List<PurchaseOrder> vendorList = purchaseOrderRepository.findAll();
	    	
			if(!vendorList.isEmpty()) {
			List<PurchaseOrderResponse> collect = vendorList.stream().map(data->{
				PurchaseOrderResponse dto = new PurchaseOrderResponse(data);
				List<PurchaseOrderItem> byPoItems = itemRepository.findByPoItems(data.getId());
				 if (!byPoItems.isEmpty()) {
                     List<PurchaseOrderItemRequest> itemDtos =byPoItems.stream().map(item -> new PurchaseOrderItemRequest(item))
                                     .collect(Collectors.toList());
                     dto.setItems(itemDtos); 
                 }
			return dto;			
			}).collect(Collectors.toList());	
			return  ResponseEntity.ok(collect);
			}else {
				 return ResponseEntity.ok(new Status ("200", "po not found"));
			}
			
	        
	    }

	    public ResponseEntity<?> getPurchaseOrder(Long id) {
	    	Optional<PurchaseOrder> vendorList = purchaseOrderRepository.findById(id);	    	
			if(vendorList.isPresent()) {			
				PurchaseOrderResponse dto = new PurchaseOrderResponse(vendorList.get());
				List<PurchaseOrderItem> byPoItems = itemRepository.findByPoItems(vendorList.get().getId());
				 if (!byPoItems.isEmpty()) {
                     List<PurchaseOrderItemRequest> itemDtos =byPoItems.stream().map(item -> new PurchaseOrderItemRequest(item))
                                     .collect(Collectors.toList());
                     dto.setItems(itemDtos); 
                 }			
				return  ResponseEntity.ok(dto);
			}else {
				 return ResponseEntity.ok(new Status ("200", "po not found"));
			}
			
	    }

	    @Transactional
	    public ResponseEntity<?> receiveStock(Long poId) {
	    	Optional<PurchaseOrder> po = purchaseOrderRepository.findById(poId);
	    	if(po.isPresent()) {   
	    		PurchaseOrder purchase =po.get();
	        // Already received check
	        if ("RECEIVED".equals(purchase.getStatus())) {
	            return ResponseEntity.ok(new Status("400", "Stock already received"));
	        }
	        // Approval check
	        if (!"SENDTOVENDOR".equals(purchase.getStatus())) {
	            return ResponseEntity.ok( new Status("400", "PO must be sent to vendor before receiving stock"));
	        }
	        List<PurchaseOrderItem> items =itemRepository.findByPoItems(poId);
	        for (PurchaseOrderItem item : items) {
	            Optional<ProductVariant> variant =variantRepository.findById(item.getVariantId());
	            if(variant.isPresent()) {
	            	ProductVariant var= variant.get();
	            	var.setStock(
	            			var.getStock() + item.getQuantity());
	            variantRepository.save(var);
	        }}
	        purchase.setStatus("RECEIVED");
	        purchaseOrderRepository.save(purchase);
	        return ResponseEntity.ok(new Status("200","PO stock updated successfully"));}
	    	else {
	    	 	 return ResponseEntity.ok(new Status("200","PO not  found"));
	    	}
	    }
	    
	    @Transactional
	    public ResponseEntity<?> approvePurchaseOrder(Long poId) {
	        Optional<PurchaseOrder> po = purchaseOrderRepository.findById(poId);
	        
	               if(po.isPresent()) {
	            	   PurchaseOrder purchase =po.get();
	        if(!"PENDING".equals(purchase.getStatus())) {
	            return ResponseEntity.ok(new Status("400","PO is not pending approval"));
	        }
	        purchase.setStatus("APPROVED");
	        purchaseOrderRepository.save(purchase);
	        return ResponseEntity.ok(new Status("200","PO Approved Successfully"));
	        }else {
	        	 return ResponseEntity.ok(new Status("200","PO not  found"));
	        }
	               
	    }
	    @Transactional
	    public ResponseEntity<?> sendToVendor(Long poId) {

	    	Optional<PurchaseOrder> po = purchaseOrderRepository.findById(poId);
	        
            if(po.isPresent()) {
         	   PurchaseOrder purchase =po.get();
     if(!"APPROVED".equals(purchase.getStatus())) {
         return ResponseEntity.ok(new Status("400","PO is not APPROVED "));
     }
     purchase.setStatus("SENDTOVENDOR");
     purchaseOrderRepository.save(purchase);
     return ResponseEntity.ok(new Status("200","PO PO Sent To Vendor"));
     }else {
     	 return ResponseEntity.ok(new Status("200","PO not  found"));
     }
	    }
	    
	    @Transactional
	    public ResponseEntity<?> qualityCheckDetail( QualityCheckDto request){
	    	QualityCheck  res=new QualityCheck(request);
	    	qualityCheckRepository.save(res);
	        Optional<PurchaseOrder> po =purchaseOrderRepository.findById(
	                        request.getPurchaseOrderId());
	                      if(po.isPresent()) {
	                    	  PurchaseOrder purchase =po.get();
	                    	  purchase.setStatus("QUALITY_CHECKED");
	                    	  purchaseOrderRepository.save(purchase);
	                      }
	        return ResponseEntity.ok(
	                new Status("200",
	                "Quality Check Completed"));
	    }
	    
	    @Transactional
	    public ResponseEntity<?> createReturn( PurchaseReturnDto request){
	    	PurchaseReturn purchase= new PurchaseReturn(request);
	        Optional<ProductVariant> variant = variantRepository.findById(request.getVariantId());		                        
	        if(variant.isPresent()) {
	        	ProductVariant var=variant.get();
	        	var.setStock( var.getStock()- request.getReturnQty());
	        variantRepository.save(var);
	        }
	        Optional<PurchaseOrderItem> byPoAndVariant = itemRepository.findByPoAndVariant(request.getPurchaseOrderId(), request.getVariantId());
	        if(byPoAndVariant.isPresent()) {
	        	PurchaseOrderItem  item = byPoAndVariant.get();
	        	item.setQuantity(item.getQuantity() -request.getReturnQty() );
	        	item.setTotalPrice(item.getUnitPrice() * item.getQuantity());
	        	itemRepository.save(item);
	        	 Optional<PurchaseOrder> byOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId());
	 	        if(byOrder.isPresent()) {
	 	        	PurchaseOrder order = byOrder.get();
	 	        	order.setTotalAmount(item.getTotalPrice());
	 	        }
	        }
	       
	        purchase.setCreatedAt(LocalDateTime.now());
	        purchaseReturRepository.save(purchase);

	        return ResponseEntity.ok(
	                new Status("200",
	                "Purchase Return Created"));
	    }
}
