package az.semmed.kafkasharedclasses;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        String orderId,
        String productId,
        int quantity,
        BigDecimal price,
        String customerEmail
) {
}
