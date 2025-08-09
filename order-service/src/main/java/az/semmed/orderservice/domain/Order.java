package az.semmed.orderservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

    private String orderId;
    private String customerEmail;
    private List<OrderItem> items;
    private LocalDateTime createdAt;
    private OrderStatus status;

    public static Order createOrder(String customerEmail, List<OrderItem> items) {
        Order order = new Order();

        order.orderId = "ORD-" + UUID.randomUUID();
        order.customerEmail = customerEmail;
        order.items = items;
        order.createdAt = LocalDateTime.now();
        order.status = OrderStatus.PENDING;

        return order;
    }

    public static Order reform(
            String orderId,
            String customerEmail,
            List<OrderItem> items,
            LocalDateTime createdAt,
            OrderStatus status
    ) {
        return new Order(orderId, customerEmail, items, createdAt, status);
    }

    private Order() {
    }

    private Order(String orderId, String customerEmail, List<OrderItem> items, LocalDateTime createdAt, OrderStatus status) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.items = items;
        this.createdAt = createdAt;
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.price().multiply(new BigDecimal(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
    }

    public void reject() {
        this.status = OrderStatus.REJECTED;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
