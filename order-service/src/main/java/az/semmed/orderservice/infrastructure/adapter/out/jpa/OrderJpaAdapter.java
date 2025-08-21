package az.semmed.orderservice.infrastructure.adapter.out.jpa;

import az.semmed.orderservice.application.mapper.OrderMapper;
import az.semmed.orderservice.application.port.out.OrderRepositoryPort;
import az.semmed.orderservice.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity;

        if (order.getOrderId() != null && orderJpaRepository.existsById(order.getOrderId())) {
            // Update existing one
            orderEntity = orderJpaRepository.findById(order.getOrderId())
                    .orElseThrow(() -> new IllegalStateException("Entity should exist"));

            orderMapper.updateJpaEntity(orderEntity, order);
        } else {
            // Create new
            orderEntity = orderMapper.toJpaEntity(order);
        }

        OrderEntity savedEntity = orderJpaRepository.save(orderEntity);
        return orderMapper.toOrder(savedEntity);
    }

    @Override
    public List<Order> findCustomerOrdersByEmail(String customerEmail) {
        return orderJpaRepository.findAllByCustomerEmail(customerEmail).stream()
                .map(orderMapper::toOrder)
                .toList();
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderJpaRepository.findById(id).map(orderMapper::toOrder);
    }
}
