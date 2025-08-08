package az.semmed.orderservice.service;

import az.semmed.orderservice.application.mapper.OrderMapper;
import az.semmed.orderservice.application.port.in.CreateOrderUseCase;
import az.semmed.orderservice.application.port.out.KafkaProducerPort;
import az.semmed.orderservice.application.port.out.OrderRepositoryPort;
import az.semmed.orderservice.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final KafkaProducerPort kafkaProducerPort;
    private final OrderMapper orderMapper;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        Order order = orderMapper.toOrder(command);

        Order savedOrder = orderRepositoryPort.save(order);

        kafkaProducerPort.sendOrderCreatedEvent(
                orderMapper.toOrderCreatedEvent(savedOrder)
        );

        return order;
    }
}
