package com.eclub.ms_ventas.controllers;

import com.eclub.ms_ventas.config.RabbitMQConfig;
import com.eclub.ms_ventas.dtos.NewVentasDTO;
import com.eclub.ms_ventas.dtos.ProductDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api")
public class VentaController {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${microservicio1.url}")
    private String microservicio1Url;
    public VentaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/catalogo")
    public List<ProductDTO> obtenerProductos() {
        String url = microservicio1Url + "/api/catalogo";
        try {
            ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductDTO>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching products from Microservicio 1", e);
        }
    }
    @GetMapping("/catalogo/{productId}")
    public ProductDTO obtenerProductoPorId(@PathVariable long productId) {
        String url = microservicio1Url + "/api/stock/" + productId;

        try {
            ResponseEntity<ProductDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    ProductDTO.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching product with ID " + productId + " from Microservicio 1", e);
        }
    }

    @PostMapping(path = "/ventas")
    public ResponseEntity<String> publishVentas(@RequestBody NewVentasDTO newVentasDTO) {
        // URL del microservicio de stock
        String url = microservicio1Url + "/api/stock/" + newVentasDTO.productId();

        try {
            // Consultar stock disponible
            ResponseEntity<ProductDTO> response = restTemplate.getForEntity(url, ProductDTO.class);
            ProductDTO product = response.getBody();

            if (product == null || product.quantity() < newVentasDTO.quantity()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Not enough stock for product ID " + newVentasDTO.productId());
            }

            // Enviar mensaje a RabbitMQ
            this.template.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_VENTAS,
                    newVentasDTO
            );

            return ResponseEntity.status(HttpStatus.CREATED).body("Sale processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing sale: " + e.getMessage());
        }
    }

}
