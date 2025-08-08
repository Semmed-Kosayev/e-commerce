package az.semmed.orderservice.domain;

import java.math.BigDecimal;

public record OrderItem(
        String productId,
        int quantity,
        BigDecimal price
) {
}
