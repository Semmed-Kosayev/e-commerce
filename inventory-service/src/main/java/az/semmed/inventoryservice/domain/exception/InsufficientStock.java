package az.semmed.inventoryservice.domain.exception;

public class InsufficientStock extends RuntimeException {
  public InsufficientStock(String message) {
    super(message);
  }
}
