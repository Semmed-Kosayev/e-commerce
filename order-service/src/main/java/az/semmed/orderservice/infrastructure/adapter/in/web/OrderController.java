package az.semmed.orderservice.infrastructure.adapter.in.web;

import az.semmed.orderservice.application.port.in.CreateOrderUseCase;
import az.semmed.orderservice.application.port.in.GetCustomerOrdersUseCase;
import az.semmed.orderservice.domain.Order;
import az.semmed.orderservice.infrastructure.adapter.in.web.dto.CreateOrderRequest;
import az.semmed.orderservice.infrastructure.adapter.in.web.dto.OrderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetCustomerOrdersUseCase getCustomerOrdersUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderUseCase.CreateOrderCommand command = toCommand(request);
        Order createdOrder = createOrderUseCase.createOrder(command);
        return ResponseEntity.ok(toResponse(createdOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersByEmail(
            @RequestParam("email")
            @NotBlank(message = "Customer email must not be blank")
            @Email(message = "Customer email should be a valid email address")
            String email
    ) {
        List<Order> customerOrders = getCustomerOrdersUseCase.getCustomerOrdersByEmail(email);
        return ResponseEntity.ok(toResponse(customerOrders));
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

    private List<OrderResponse> toResponse(List<Order> order) {
        return order.stream()
                .map(this::toResponse)
                .toList();
    }
}
