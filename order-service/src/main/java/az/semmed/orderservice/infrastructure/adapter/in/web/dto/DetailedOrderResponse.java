package az.semmed.orderservice.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DetailedOrderResponse(
        String orderId,
        String customerEmail,
        BigDecimal totalPrice,
        List<OrderItemResponse> items,
        String status,
        LocalDateTime createdAt
) {
    public record OrderItemResponse(
            Long id,
            String productId,
            int quantity,
            BigDecimal price
    ) {
    }
}
