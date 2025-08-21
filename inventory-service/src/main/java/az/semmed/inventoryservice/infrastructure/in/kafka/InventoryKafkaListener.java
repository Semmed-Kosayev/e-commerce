package az.semmed.inventoryservice.infrastructure.in.kafka;

import az.semmed.inventoryservice.application.port.in.ReserveInventoryForOrderUseCase;
import az.semmed.kafkasharedclasses.inventory.InventoryConstants;
import az.semmed.kafkasharedclasses.order.OrderConstants;
import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryKafkaListener {

    private final ReserveInventoryForOrderUseCase reserveInventoryForOrderUseCase;

    @KafkaListener(topics = OrderConstants.ORDER_CREATED_TOPIC, groupId = InventoryConstants.GROUP_ID)
    public void listenToOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        var productCommands = orderCreatedEvent.items().stream()
                .map(this::toProductCommand)
                .toList();

        reserveInventoryForOrderUseCase.reserveInventoryForOrder(
                new ReserveInventoryForOrderUseCase.ReserveInventoryCommand(orderCreatedEvent.orderId(), productCommands)
        );
    }

    private ReserveInventoryForOrderUseCase.ReserveInventoryCommand.ProductCommand toProductCommand(
            OrderCreatedEvent.OrderItem orderItem
    ) {
        return new ReserveInventoryForOrderUseCase.ReserveInventoryCommand.ProductCommand(
                orderItem.productId(),
                orderItem.quantity()
        );
    }

}
