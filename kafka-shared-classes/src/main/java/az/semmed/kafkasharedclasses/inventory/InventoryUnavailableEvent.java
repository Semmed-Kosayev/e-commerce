package az.semmed.kafkasharedclasses.inventory;

public record InventoryUnavailableEvent(
        String orderId
) {
}
