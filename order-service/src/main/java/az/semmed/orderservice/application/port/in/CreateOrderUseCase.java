package az.semmed.orderservice.application.port.in;

import az.semmed.orderservice.domain.Order;

import java.math.BigDecimal;
import java.util.List;

public interface CreateOrderUseCase {

    Order createOrder(CreateOrderCommand command);

    record CreateOrderCommand(
            String customerEmail,
            List<OrderItemCommand> items
    ) {
        public record OrderItemCommand(
                String productId,
                int quantity,
                BigDecimal price
        ) {
        }
    }

}
