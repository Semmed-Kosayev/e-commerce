package az.semmed.orderservice.application.port.out;

import az.semmed.orderservice.domain.Order;
import org.springframework.stereotype.Component;

@Component
public interface KafkaProducerPort {

    void sendOrderCreatedEvent(Order orderCreatedEvent);

    void sendOrderFinalizedEvent(Order orderFinalizedEvent);
}
