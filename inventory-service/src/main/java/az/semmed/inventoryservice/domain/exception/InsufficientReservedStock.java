package az.semmed.inventoryservice.domain.exception;

public class InsufficientReservedStock extends RuntimeException {
    public InsufficientReservedStock(String message) {
        super(message);
    }
}
