package com.eclub.ms_stock.config;

import com.eclub.ms_stock.dtos.NewVentasDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaConfirmationListener {

    @RabbitListener(queues = RabbitMQConfig.VENTAS_COMFIRM_QUEUE)
    public void logVentaConfirmation(NewVentasDTO newVentasDTO) {
        log.info("Venta confirmada: {}", newVentasDTO);
    }
}

