package az.semmed.kafkasharedclasses.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedEvent(
        String orderId,
        List<OrderItem> items,
        String customerEmail
) {
    public record OrderItem(
            String productId,
            int quantity,
            BigDecimal price
    ) {
    }
}
