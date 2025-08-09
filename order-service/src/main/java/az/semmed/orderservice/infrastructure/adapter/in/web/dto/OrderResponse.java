package az.semmed.orderservice.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        String orderId,
        String customerEmail,
        BigDecimal totalPrice,
        String status,
        LocalDateTime createdAt
) {
}
