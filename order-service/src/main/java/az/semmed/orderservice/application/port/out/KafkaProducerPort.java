package az.semmed.orderservice.application.port.out;

import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface KafkaProducerPort {

    void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);
}
