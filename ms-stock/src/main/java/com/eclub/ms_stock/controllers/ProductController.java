package com.eclub.ms_stock.controllers;

import com.eclub.ms_stock.dtos.NewProductDTO;
import com.eclub.ms_stock.dtos.ProductDTO;
import com.eclub.ms_stock.models.Product;
import com.eclub.ms_stock.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @PostMapping("/stock")
    public ResponseEntity<Object> createProduct(@RequestBody NewProductDTO newProductDTO){
        if (newProductDTO.productId() <= 0) {
            return new ResponseEntity<>("You must enter a valid id.", HttpStatus.BAD_REQUEST);
        }
        if (newProductDTO.productName() == null) {
            return new ResponseEntity<>("You must enter a name.",HttpStatus.BAD_REQUEST);
        }
        if (newProductDTO.quantity() <= 0) {
            return new ResponseEntity<>("You must enter a valid quantity.",HttpStatus.BAD_REQUEST);
        }
        if (productRepository.existsById(newProductDTO.productId())) {
            return new ResponseEntity<>("The id is already in use.",HttpStatus.BAD_REQUEST);
        }
        //CREO PRODUCTO
        Product newProduct = new Product();
        newProduct.setproductId(newProductDTO.productId());
        newProduct.setProductName(newProductDTO.productName());
        newProduct.setQuantity(newProductDTO.quantity());
        //GUARDO PRODUCTO
        productRepository.save(newProduct);
        return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/stock/{productId}")
    public ResponseEntity<Object> getProductById(@PathVariable long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @GetMapping( "/catalogo")
    public List<ProductDTO> getProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }
}
