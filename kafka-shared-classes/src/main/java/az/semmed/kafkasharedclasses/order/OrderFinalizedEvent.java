package az.semmed.kafkasharedclasses.order;

public record OrderFinalizedEvent(
        String orderId,
        OrderFinalizedStatus status
) {
}
