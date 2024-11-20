package com.eclub.ms_stock.dtos;

import com.eclub.ms_stock.models.Product;

public class ProductDTO {
    private long productId;
    private String productName;
    private long quantity;

    public ProductDTO(Product product) {
        productId = product.getproductId();
        productName= product.getProductName();
        quantity = product.getQuantity();

    }
    public long getproductId() {return productId;}

    public String getProductName() {
        return productName;
    }

    public long getQuantity() {return quantity;}
}
