package az.semmed.orderservice.infrastructure.adapter.in.kafka;

import az.semmed.kafkasharedclasses.inventory.InventoryReservedEvent;
import az.semmed.kafkasharedclasses.inventory.InventoryUnavailableEvent;
import az.semmed.orderservice.application.port.in.ConfirmOrderUseCase;
import az.semmed.orderservice.application.port.in.RejectOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderKafkaListener {

    private static final String INVENTORY_RESERVED_TOPIC = "inventory-reserved-topic";
    private static final String INVENTORY_UNAVAILABLE_TOPIC = "inventory-unavailable-topic";
    private static final String GROUP_ID = "order-group";

    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;

    @KafkaListener(topics = INVENTORY_RESERVED_TOPIC, groupId = GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void listenToInventoryReservedEvent(InventoryReservedEvent event) {
        confirmOrderUseCase.confirmOrder(event.orderId());
    }

    @Transactional
    @KafkaListener(topics = INVENTORY_UNAVAILABLE_TOPIC, groupId = GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void listenToInventoryUnavailableEvent(InventoryUnavailableEvent event) {
        rejectOrderUseCase.rejectOrder(event.orderId());
    }

}
