package az.semmed.orderservice.application.mapper;

import az.semmed.kafkasharedclasses.order.OrderCreatedEvent;
import az.semmed.orderservice.application.port.in.CreateOrderUseCase;
import az.semmed.orderservice.domain.Order;
import az.semmed.orderservice.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

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
                domainItem.productId(),
                domainItem.quantity(),
                domainItem.price()
        );
    }
}
