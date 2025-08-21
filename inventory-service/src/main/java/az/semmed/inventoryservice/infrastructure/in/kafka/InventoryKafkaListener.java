package az.semmed.inventoryservice.infrastructure.in.kafka;

import az.semmed.inventoryservice.application.port.in.ReserveInventoryForOrderUseCase;
import az.semmed.kafkasharedclasses.inventory.InventoryConstants;
import az.semmed.kafkasharedclasses.order.OrderConstants;
import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryKafkaListener {

    private final ReserveInventoryForOrderUseCase reserveInventoryForOrderUseCase;

    @KafkaListener(topics = OrderConstants.ORDER_CREATED_TOPIC, groupId = InventoryConstants.GROUP_ID)
    public void listenToOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        List<ReserveInventoryForOrderUseCase.ReserveInventoryCommand.ProductCommand> productCommands =
                orderCreatedEvent.items().stream()
                        .map(i -> new ReserveInventoryForOrderUseCase.ReserveInventoryCommand.ProductCommand(
                                i.productId(),
                                i.quantity())
                        )
                        .toList();

        reserveInventoryForOrderUseCase.reserveInventoryForOrder(
                new ReserveInventoryForOrderUseCase.ReserveInventoryCommand(orderCreatedEvent.orderId(), productCommands)
        );
    }

}
