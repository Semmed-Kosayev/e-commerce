package az.semmed.orderservice.application.mapper;

import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedEvent;
import az.semmed.kafkasharedclasses.order.OrderFinalizedStatus;
import az.semmed.orderservice.application.port.in.CreateOrderUseCase;
import az.semmed.orderservice.domain.Order;
import az.semmed.orderservice.domain.OrderItem;
import az.semmed.orderservice.domain.OrderStatus;
import az.semmed.orderservice.infrastructure.adapter.out.jpa.OrderEntity;
import az.semmed.orderservice.infrastructure.adapter.out.jpa.OrderItemEntity;
import az.semmed.orderservice.infrastructure.adapter.out.jpa.OrderStatusJpa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderMapper {

    public Order toOrder(CreateOrderUseCase.CreateOrderCommand createOrderCommand) {
        List<OrderItem> domainItems = createOrderCommand.items().stream()
                .map(this::toOrderItem)
                .toList();

        return Order.createOrder(createOrderCommand.customerEmail(), domainItems);
    }

    private OrderItem toOrderItem(CreateOrderUseCase.CreateOrderCommand.OrderItemCommand itemCommand) {
        return new OrderItem(
                Optional.empty(),
                itemCommand.productId(),
                itemCommand.quantity(),
                itemCommand.price()
        );
    }

    public OrderCreatedEvent toOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(
                order.getOrderId(),
                order.getItems().stream()
                        .map(this::toSharedOrderItem)
                        .toList(),
                order.getCustomerEmail()
        );
    }

    private OrderCreatedEvent.OrderItem toSharedOrderItem(OrderItem domainItem) {
        return new OrderCreatedEvent.OrderItem(
                domainItem.orderItemId().isPresent()? domainItem.orderItemId().get() : null,
                domainItem.productId(),
                domainItem.quantity(),
                domainItem.price()
        );
    }

    public OrderEntity toJpaEntity(Order order) {
        OrderEntity entity = new OrderEntity();

        entity.setOrderId(order.getOrderId());
        entity.setCustomerEmail(order.getCustomerEmail());
        entity.setStatus(OrderStatusJpa.valueOf(order.getStatus().name()));
        entity.setCreatedAt(order.getCreatedAt());
        entity.setTotalPrice(order.getTotalPrice());

        order.getItems().stream()
                .map(this::toJpaItemEntity)
                .forEach(entity::addItem);

        return entity;
    }

    private OrderItemEntity toJpaItemEntity(OrderItem domainItem) {
        OrderItemEntity itemEntity = new OrderItemEntity();

        itemEntity.setId(domainItem.orderItemId().isPresent()?
                domainItem.orderItemId().get()
                :
                null
        );
        itemEntity.setProductId(domainItem.productId());
        itemEntity.setQuantity(domainItem.quantity());
        itemEntity.setPrice(domainItem.price());

        return itemEntity;
    }

    public Order toOrder(OrderEntity entity) {
        List<OrderItem> domainItems = entity.getItems().stream()
                .map(this::toDomainItem)
                .toList();

        return Order.reform(
                entity.getOrderId(),
                entity.getCustomerEmail(),
                domainItems,
                entity.getCreatedAt(),
                OrderStatus.valueOf(entity.getStatus().name())
        );
    }

    private OrderItem toDomainItem(OrderItemEntity itemEntity) {
        return new OrderItem(
                Optional.of(itemEntity.getId()),
                itemEntity.getProductId(),
                itemEntity.getQuantity(),
                itemEntity.getPrice()
        );
    }

    public OrderFinalizedEvent toOrderFinalizedEvent(Order order) {
        return new OrderFinalizedEvent(
                order.getOrderId(),
                OrderFinalizedStatus.valueOf(order.getStatus().name())
        );
    }
}
