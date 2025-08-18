package az.semmed.orderservice.service;

import az.semmed.orderservice.application.mapper.OrderMapper;
import az.semmed.orderservice.application.port.in.ConfirmOrderUseCase;
import az.semmed.orderservice.application.port.in.CreateOrderUseCase;
import az.semmed.orderservice.application.port.in.GetCustomerOrdersUseCase;
import az.semmed.orderservice.application.port.in.GetOrderUseCase;
import az.semmed.orderservice.application.port.in.RejectOrderUseCase;
import az.semmed.orderservice.application.port.out.KafkaProducerPort;
import az.semmed.orderservice.application.port.out.OrderRepositoryPort;
import az.semmed.orderservice.domain.Order;
import az.semmed.orderservice.service.exception.OrderNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService implements CreateOrderUseCase, GetCustomerOrdersUseCase, GetOrderUseCase, ConfirmOrderUseCase, RejectOrderUseCase {

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

    @Override
    public List<Order> getCustomerOrdersByEmail(String customerEmail) {
        return orderRepositoryPort.findCustomerOrdersByEmail(customerEmail);
    }

    @Override
    public Order getOrder(String orderId) {
        return orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFound("Order not found with id: " + orderId));
    }

    @Override
    public Order confirmOrder(String orderId) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFound("Order not found with id: " + orderId));

        order.markAsConfirmed();
        System.out.println("\n\n\n\n\n\n ----->" + order.getStatus());
        Order savedOrder = orderRepositoryPort.save(order);

        kafkaProducerPort.sendOrderFinalizedEvent(
                orderMapper.toOrderFinalizedEvent(savedOrder)
        );

        return savedOrder;
    }

    @Override
    public Order rejectOrder(String orderId) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFound("Order not found with id: " + orderId));

        order.markAsRejected();

        Order savedOrder = orderRepositoryPort.save(order);

        kafkaProducerPort.sendOrderFinalizedEvent(
                orderMapper.toOrderFinalizedEvent(savedOrder)
        );

        return savedOrder;
    }
}
