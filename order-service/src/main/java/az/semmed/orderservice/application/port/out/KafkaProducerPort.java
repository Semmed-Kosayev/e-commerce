package az.semmed.orderservice.application.port.out;

import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedEvent;
import org.springframework.stereotype.Component;

@Component
public interface KafkaProducerPort {

    void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);
    void sendOrderFinalizedEvent(OrderFinalizedEvent orderFinalizedEvent);
}
