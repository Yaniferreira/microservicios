package com.eclub.ms_stock.repositories;
import com.eclub.ms_stock.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findById (long productId);
}
