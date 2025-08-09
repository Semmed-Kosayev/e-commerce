package az.semmed.orderservice.infrastructure.adapter.in.web;

import az.semmed.orderservice.application.port.in.CreateOrderUseCase;
import az.semmed.orderservice.domain.Order;
import az.semmed.orderservice.infrastructure.adapter.in.web.dto.CreateOrderRequest;
import az.semmed.orderservice.infrastructure.adapter.in.web.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderUseCase.CreateOrderCommand command = toCommand(request);
        Order createdOrder = createOrderUseCase.createOrder(command);
        return ResponseEntity.ok(toResponse(createdOrder));
    }

    private CreateOrderUseCase.CreateOrderCommand toCommand(CreateOrderRequest request) {
        List<CreateOrderUseCase.CreateOrderCommand.OrderItemCommand> itemCommands = request.items().stream()
                .map(i -> new CreateOrderUseCase.CreateOrderCommand.OrderItemCommand(
                        i.productId(),
                        i.quantity(),
                        i.price()
                ))
                .toList();

        return new CreateOrderUseCase.CreateOrderCommand(request.customerEmail(), itemCommands);
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getCustomerEmail(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}
