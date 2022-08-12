package com.abach.microservice.shopping.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "El stock debe ser mayor que cero")
    private BigDecimal quantity;
    private BigDecimal price;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private BigDecimal subTotal;

    public BigDecimal getSubtotal() {
        if (this.price.doubleValue() > 0 && this.quantity.doubleValue() > 0) {
            return this.quantity.multiply(this.price);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public InvoiceItem() {
        this.quantity = BigDecimal.ZERO;
        this.price = BigDecimal.ZERO;
    }
}
