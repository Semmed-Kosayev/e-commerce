package az.semmed.orderservice.infrastructure.adapter.out.kafka;

import az.semmed.kafkasharedclasses.order.OrderConstants;
import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedStatus;
import az.semmed.orderservice.application.port.out.KafkaProducerPort;
import az.semmed.orderservice.domain.Order;
import az.semmed.orderservice.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderKafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendOrderCreatedEvent(Order order) {
        OrderCreatedEvent orderCreatedEvent = toOrderCreatedEvent(order);
        kafkaTemplate.send(OrderConstants.ORDER_CREATED_TOPIC, orderCreatedEvent.orderId(), orderCreatedEvent);
    }

    @Override
    public void sendOrderFinalizedEvent(Order order) {
        OrderFinalizedEvent orderFinalizedEvent = toOrderFinalizedEvent(order);
        kafkaTemplate.send(OrderConstants.ORDER_FINALIZED_TOPIC, orderFinalizedEvent.orderId(), orderFinalizedEvent);
    }

    //mappers
    public OrderCreatedEvent toOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(
                order.getOrderId(),
                order.getItems().stream()
                        .map(this::toSharedOrderItem)
                        .toList(),
                order.getCustomerEmail()
        );
    }

    private OrderCreatedEvent.OrderItem toSharedOrderItem(OrderItem domainItem) {
        return new OrderCreatedEvent.OrderItem(
                domainItem.orderItemId().isPresent() ? domainItem.orderItemId().get() : null,
                domainItem.productId(),
                domainItem.quantity(),
                domainItem.price()
        );
    }

    public OrderFinalizedEvent toOrderFinalizedEvent(Order order) {
        return new OrderFinalizedEvent(
                order.getOrderId(),
                OrderFinalizedStatus.valueOf(order.getStatus().name())
        );
    }
}
