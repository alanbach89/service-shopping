package com.abach.microservice.shopping.repository;

import com.abach.microservice.shopping.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
