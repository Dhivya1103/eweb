package com.eweb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eweb.dao.ProductVariantRepository;
import com.eweb.dao.PurchaserOrderRepository;
import com.eweb.dao.QualityCheckRepository;
import com.eweb.dao.VendorInvoiceRepository;
import com.eweb.dao.VendorPaymentRepository;
import com.eweb.dao.VendorRepository;
import com.eweb.dto.PurchaseReturnDto;
import com.eweb.dto.QualityCheckDto;
import com.eweb.dto.VendorDto;
import com.eweb.dto.VendorInvoiceDto;
import com.eweb.dto.VendorPaymentRequest;
import com.eweb.dto.VendorPaymnetDto;
import com.eweb.model.ProductVariant;
import com.eweb.model.PurchaseOrder;
import com.eweb.model.PurchaseReturn;
import com.eweb.model.QualityCheck;
import com.eweb.model.Status;
import com.eweb.model.Vendor;
import com.eweb.model.VendorInvoice;
import com.eweb.model.VendorPayment;

import jakarta.transaction.Transactional;

@Service
public class VendorService {
	 @Autowired
	    private VendorRepository vendorRepository;
	 
	  @Autowired
	    private VendorPaymentRepository paymentRepository;

	    @Autowired
	    private PurchaserOrderRepository purchaseOrderRepository;
	    
	    @Autowired
	    private VendorInvoiceRepository invoiceRepository;
	    @Autowired
	    QualityCheckRepository qualityCheckRepository;

		@Autowired
		ProductVariantRepository productVariantRepository;

	    public ResponseEntity<?> createVendor( VendorDto request) {
	        Vendor vendor = new Vendor(request);       
	         vendorRepository.save(vendor);
	         return ResponseEntity.ok(new Status("200", "vendor saved successfull!"));
	    }

		public ResponseEntity<?> GetAllVendor() {
			List<Vendor> vendorList = vendorRepository.findAll();
			if(!vendorList.isEmpty()) {
			List<VendorDto> collect = vendorList.stream().map(data->{
				VendorDto dto = new VendorDto(data);
			return dto;			
			}).collect(Collectors.toList());	
			return  ResponseEntity.ok(collect);
			}
			return  ResponseEntity.ok(new Status("4004", "vendor details empty!"));
			
		}

		public ResponseEntity<?> findByVendorId(Long id) {
			Optional<Vendor> vendorDetail = vendorRepository.findById(id);
			if(vendorDetail.isPresent()) {
				VendorDto dto = new VendorDto(vendorDetail.get());
				return  ResponseEntity.ok(dto); 
			}else {
				return  ResponseEntity.ok(new Status("4004", "vendor details not Found!"));
			}
		}

		public ResponseEntity<?> deleteFindByVendorId(Long id) {
			
			Optional<Vendor> vendorDetail = vendorRepository.findById(id);
			if(vendorDetail.isPresent()) {
				vendorRepository.delete(vendorDetail.get());
				return  ResponseEntity.ok(new Status("404", "vendor deleted successfully!"));
			}else {
				return  ResponseEntity.ok(new Status("404", "vendor details not Found!"));
			}
			
		}

		public ResponseEntity<?> updateVendor(VendorDto request) {
			Optional<Vendor> vendorDetail = vendorRepository.findById(request.getId());
			if(vendorDetail.isPresent()) {
				Vendor  vendor = vendorDetail.get();
				vendor.setAddress(request.getAddress());
				vendor.setContactPerson(request.getContactPerson());
				vendor.setEmail(request.getEmail());
				vendor.setGstNumber(request.getGstNumber());
				vendor.setPhone(request.getPhone());
				vendor.setStatus(request.getStatus());
				vendor.setVendorId(request.getId());
				vendor.setVendorName(request.getVendorName());
				vendorRepository.save(vendor);
				 return ResponseEntity.ok(new Status("200", "vendor updated successfull!")); 
			}else {
				return  ResponseEntity.ok(new Status("404", "vendor details not Found!"));
			}
		}
		
		 @Transactional
		    public ResponseEntity<?> makePayment( VendorPaymentRequest request) {
		        VendorPayment payment =new VendorPayment();
		        payment.setVendorId( request.getVendorId());
		        payment.setPurchaseOrderId(request.getPurchaseOrderId());
		        payment.setAmount( request.getAmount());
		        payment.setPaymentMode(request.getPaymentMode());
		        payment.setPaymentStatus("PAID");
		        payment.setPaymentDate( LocalDateTime.now());
		        Optional<PurchaseOrder> po =purchaseOrderRepository.findById(request.getPurchaseOrderId());
		          if(po.isPresent()) {
		          PurchaseOrder purchase = po.get();     	
             	purchase.setStatus("PAID");
		        purchaseOrderRepository.save(purchase);
		        }                      
		         paymentRepository.save(payment);
		         return ResponseEntity.ok(new Status("200", "vendor payment updated successfull!"));  
		    }

		    public ResponseEntity<?> getAllPayments() {
		         List<VendorPayment> all = paymentRepository.findAll();
		         if(!all.isEmpty()) {
		         List<VendorPaymnetDto> collect = all.stream().map( data->new VendorPaymnetDto (data) ).collect(Collectors.toList());
		   return ResponseEntity.ok(collect);
		    }else {
		    	return  ResponseEntity.ok(new Status("404", "vendor payemnt details not Found!"));
		    }
		    }

		    public ResponseEntity<?> getPayment(Long id) {

		    	 Optional<VendorPayment> all = paymentRepository.findById(id);
		         if(all.isPresent()) {
		        	 VendorPaymnetDto dto = new VendorPaymnetDto(all.get()) ;
		   return ResponseEntity.ok(dto);
		    }else {
		    	return  ResponseEntity.ok(new Status("404", "vendor payemnt details not Found!"));
		    }
		    }
		    
		    @Transactional
		    public ResponseEntity<?> saveInvoice(VendorInvoiceDto request) {
		        VendorInvoice invoice = new VendorInvoice();
		        invoice.setPurchaseOrderId( request.getPurchaseOrderId());
		        invoice.setInvoiceNumber(request.getInvoiceNumber());
		        invoice.setInvoiceDate(request.getInvoiceDate());
		        invoice.setGstAmount( request.getGstAmount());
		        invoice.setTotalAmount(request.getTotalAmount());
		        invoice.setStatus("INVOICE_RECEIVED");
		        invoiceRepository.save(invoice);
		        Optional<PurchaseOrder> po =purchaseOrderRepository.findById(request.getPurchaseOrderId());
		        if(po.isPresent()) {
		        	PurchaseOrder purchase = po.get();
		        	purchase.setStatus("INVOICE_RECEIVED");
		        purchaseOrderRepository.save(purchase);
		        }
		        return ResponseEntity.ok(
		                new Status("200",
		                "Invoice Saved Successfully"));
		    }
		    
		   
}
