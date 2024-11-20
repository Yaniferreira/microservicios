package com.eclub.ms_stock.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @Column(name = "product_id")
    private long productId;
    private String productName;
    private long quantity;

    public Product() {
    }

    public Product(long productId, String productName, long quantity) {
        this.productId= productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public long getproductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setproductId(long productId) {
        this.productId= productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
