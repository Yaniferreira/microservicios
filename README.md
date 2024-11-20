# Proyecto de Microservicios - Prueba Técnica

Este proyecto implementa un sistema de microservicios que incluye:

### Microservicio 1: `ms-stock`
Este microservicio se encarga de gestionar el inventario de productos.  
**Características principales:**
- Consulta y actualización del stock.
- Recepción de mensajes de RabbitMQ para ajustar el inventario tras una venta.

### Microservicio 2: `ms-ventas`
Este microservicio gestiona las ventas de productos.  
**Características principales:**
- Validación de stock disponible antes de realizar una venta.
- Comunicación con `ms-stock` para confirmar disponibilidad.
- Envío de mensajes a RabbitMQ tras completar una venta.

## Tecnologías Utilizadas

- **Java**: Lenguaje principal.
- **Spring Boot**: Framework para simplificar el desarrollo.
- **RabbitMQ**: Broker de mensajes para la comunicación asíncrona entre microservicios.
- **Gradle**: Sistema de automatización de construcción.
- **Lombok**: Simplificación de código repetitivo (getters, setters, constructores, etc.).
