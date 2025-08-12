package az.semmed.orderservice.domain.exception;

public class IllegalOrderStatus extends RuntimeException {
    public IllegalOrderStatus(String message) {
        super(message);
    }
}
