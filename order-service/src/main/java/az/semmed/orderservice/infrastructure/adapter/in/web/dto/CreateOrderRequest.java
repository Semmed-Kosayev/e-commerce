package az.semmed.orderservice.infrastructure.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(

        @NotBlank(message = "Customer email must not be blank")
        @Email(message = "Customer email should be a valid email address")
        String customerEmail,

        @Valid
        @NotEmpty(message = "Order must contain at least one item")
        List<OrderItemRequest> items
) {
    public record OrderItemRequest(
            @NotBlank(message = "Product ID must not be blank")
            String productId,

            @NotNull(message = "Quantity must not be null")
            @Min(1)
            @Positive(message = "Quantity must be positive number")
            Integer quantity,

            @NotNull(message = "Price must not be null")
            @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
            @PositiveOrZero(message = "Price must be a non-negative value")
            BigDecimal price
    ) {
    }
}
