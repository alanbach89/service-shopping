package com.abach.microservice.shopping.repository;

import com.abach.microservice.shopping.entity.Invoice;
import com.abach.microservice.shopping.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    public List<Invoice> findCustomer(Long customerId);
    public Invoice findByNumberInvoice(String numberInvoice);
}
