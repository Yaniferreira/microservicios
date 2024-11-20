package com.eclub.ms_stock.config;

import com.eclub.ms_stock.dtos.NewVentasDTO;
import com.eclub.ms_stock.models.Product;
import com.eclub.ms_stock.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerListener {

    private final ProductRepository productRepository;

    @RabbitListener(queues = RabbitMQConfig.VENTAS_COMFIRM_QUEUE)
    public void processStockUpdate(NewVentasDTO newVentasDTO) {
        log.info("Receiving message: {}", newVentasDTO);

        Product product = productRepository.findById(newVentasDTO.productId());

        if (product == null) {
            log.error("Product with ID {} not found", newVentasDTO.productId());
            return;
        }
        if (product.getQuantity() < newVentasDTO.quantity()) {
            log.error("Not enough stock for product ID {}", newVentasDTO.productId());
            return;
        }

        product.setQuantity(product.getQuantity() - newVentasDTO.quantity());
        productRepository.save(product);
        log.info("Stock updated for product ID {}: remaining {}", product.getproductId(), product.getQuantity());
    }
}
