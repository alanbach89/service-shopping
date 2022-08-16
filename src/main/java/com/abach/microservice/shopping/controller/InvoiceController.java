package com.abach.microservice.shopping.controller;

import com.abach.microservice.shopping.entity.Invoice;
import com.abach.microservice.shopping.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.abach.microservice.shopping.message.ErrorMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getInvoiceList() {
        List<Invoice> invoiceList = invoiceService.findInvoiceAll();
        if (invoiceList.isEmpty()) {
                return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoiceList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable(name = "id") Long id) {
        log.info("Fetch Invoice with id {}", id);
        Invoice invoiceDB = invoiceService.getInvoiceById(id);
        if (invoiceDB == null) {
            log.error("Invoice with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceDB);
    }


    @GetMapping("{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable(name = "id") Long id, @RequestBody Invoice invoiceDto) {
        log.info("Update Invoice with id {}", id);
        Invoice invoiceDB = invoiceService.getInvoiceById(id);
        if (invoiceDB == null) {
            log.error("Unable to update. Invoice with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        Invoice invoiceUpdated = invoiceService.updateInvoice(invoiceDto);
        return ResponseEntity.ok(invoiceUpdated);
    }

    @GetMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoiceDto, BindingResult result) {
        validateBindingResult(result);
        Invoice invoiceCreated = invoiceService.createInvoice(invoiceDto);
        log.info("Create Invoice with id {}", invoiceCreated.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceCreated);
    }

    @GetMapping("{id}")
    public ResponseEntity<Invoice> deleteById(@RequestParam(name = "id") Long id) {
        log.info("Delete Invoice with id {}", id);
        Invoice invoiceDB = invoiceService.getInvoiceById(id);
        if (invoiceDB == null) {
            log.error("Unable to delete. Invoice with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        Invoice invoiceDeleted = invoiceService.deleteInvoice(invoiceDB);
        return ResponseEntity.ok(invoiceDeleted);
    }

    private void validateBindingResult(BindingResult result) {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> mapError = new HashMap<>();
                    mapError.put(error.getField(), error.getDefaultMessage());
                    return mapError;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        return formatObjectToJsonString(errorMessage);
    }

    private String formatObjectToJsonString(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;

    }
}
