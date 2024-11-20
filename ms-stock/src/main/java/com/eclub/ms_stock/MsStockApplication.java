package com.eclub.ms_stock;

import com.eclub.ms_stock.models.Product;
import com.eclub.ms_stock.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsStockApplication.class, args);
	}
	public CommandLineRunner initData(ProductRepository productRepository){
		return args ->{
		};
	}

}
