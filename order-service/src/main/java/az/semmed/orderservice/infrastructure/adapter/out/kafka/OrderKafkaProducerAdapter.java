package az.semmed.orderservice.infrastructure.adapter.out.kafka;

import az.semmed.kafkasharedclasses.order.OrderConstants;
import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedEvent;
import az.semmed.orderservice.application.port.out.KafkaProducerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderKafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send(OrderConstants.ORDER_CREATED_TOPIC, orderCreatedEvent.orderId(), orderCreatedEvent);
    }

    @Override
    public void sendOrderFinalizedEvent(OrderFinalizedEvent orderFinalizedEvent) {
        kafkaTemplate.send(OrderConstants.ORDER_FINALIZED_TOPIC, orderFinalizedEvent.orderId(), orderFinalizedEvent);
    }
}
