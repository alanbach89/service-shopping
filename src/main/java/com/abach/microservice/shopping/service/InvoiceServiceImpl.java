package com.abach.microservice.shopping.service;

import com.abach.microservice.shopping.entity.Invoice;
import com.abach.microservice.shopping.repository.InvoiceItemRepository;
import com.abach.microservice.shopping.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findInvoiceAll() {
        return this.invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {

        Invoice invoiceDB = this.invoiceRepository.getReferenceById(invoice.getId());
        if(invoiceDB != null)
            return invoiceDB;

        invoice.setState("CREATED");
        invoiceDB = this.invoiceRepository.save(invoice);
        /*invoiceDB.getItems().forEach(invoiceItem -> {
            pro
        });*/
        return invoiceDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {

        Invoice invoiceDB = this.invoiceRepository.getReferenceById(invoice.getId());
        if(invoiceDB == null)
            return null;

        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {

        Invoice invoiceDB = this.invoiceRepository.getReferenceById(invoice.getId());
        if(invoiceDB == null)
            return null;

        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return this.invoiceRepository.getReferenceById(id);
    }
}
