package az.semmed.inventoryservice;

import az.semmed.inventoryservice.infrastructure.in.kafka.InventoryKafkaListener;
import az.semmed.inventoryservice.service.InventoryService;
import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}
