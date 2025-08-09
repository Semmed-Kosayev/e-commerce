package az.semmed.orderservice.infrastructure.adapter.out.kafka;

import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import az.semmed.orderservice.application.port.out.KafkaProducerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final static String TOPIC = "order-events-topic";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send(TOPIC, orderCreatedEvent.orderId(), orderCreatedEvent);
    }
}
