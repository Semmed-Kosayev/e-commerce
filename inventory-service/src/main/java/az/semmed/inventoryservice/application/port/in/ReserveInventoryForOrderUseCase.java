package az.semmed.inventoryservice.application.port.in;

import az.semmed.inventoryservice.domain.Inventory;

import java.util.List;

public interface ReserveInventoryForOrderUseCase {

    void reserveInventoryForOrder(ReserveInventoryCommand reserveInventoryCommand);

    record ReserveInventoryCommand(
            String orderId,
            List<ProductCommand> products
    ) {
        public record ProductCommand(
                String productId,
                int quantity
        ) {}
    }
}
