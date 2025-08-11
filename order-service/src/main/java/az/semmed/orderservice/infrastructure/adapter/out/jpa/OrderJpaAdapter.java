package az.semmed.orderservice.infrastructure.adapter.out.jpa;

import az.semmed.orderservice.application.mapper.OrderMapper;
import az.semmed.orderservice.application.port.out.OrderRepositoryPort;
import az.semmed.orderservice.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapper.toJpaEntity(order);
        orderJpaRepository.save(orderEntity);
        return orderMapper.toOrder(orderEntity);
    }

    @Override
    public List<Order> findCustomerOrdersByEmail(String customerEmail) {
        return orderJpaRepository.findAllByCustomerEmail(customerEmail).stream()
                .map(orderMapper::toOrder)
                .toList();
    }
}
