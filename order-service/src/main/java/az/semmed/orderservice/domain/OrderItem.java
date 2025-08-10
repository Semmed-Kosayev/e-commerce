package az.semmed.orderservice.domain;

import java.math.BigDecimal;
import java.util.Optional;

public record OrderItem(
        Optional<Long> orderItemId,
        String productId,
        int quantity,
        BigDecimal price
) {
}
