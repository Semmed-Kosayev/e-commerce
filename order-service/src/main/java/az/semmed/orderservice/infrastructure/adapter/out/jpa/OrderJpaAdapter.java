package az.semmed.orderservice.infrastructure.adapter.out.jpa;

import az.semmed.orderservice.application.mapper.OrderMapper;
import az.semmed.orderservice.application.port.out.OrderRepositoryPort;
import az.semmed.orderservice.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
