package az.semmed.inventoryservice.infrastructure.out.kafka;

import az.semmed.inventoryservice.application.port.out.KafkaProducerPort;
import az.semmed.kafkasharedclasses.inventory.InventoryReservedEvent;
import az.semmed.kafkasharedclasses.inventory.InventoryUnavailableEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InventoryKafkaProducerAdapter implements KafkaProducerPort {

    private static final String INVENTORY_RESERVED_TOPIC = "inventory-reserved-topic";
    private static final String INVENTORY_UNAVAILABLE_TOPIC = "inventory-unavailable-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendInventoryReservedEvent(InventoryReservedEvent inventoryReservedEvent) {
        kafkaTemplate.send(INVENTORY_RESERVED_TOPIC, inventoryReservedEvent.orderId(), inventoryReservedEvent);
    }

    @Override
    public void sendInventoryUnavailableEvent(InventoryUnavailableEvent inventoryUnavailableEvent) {
        kafkaTemplate.send(INVENTORY_UNAVAILABLE_TOPIC, inventoryUnavailableEvent.orderId(), inventoryUnavailableEvent);
    }
}
