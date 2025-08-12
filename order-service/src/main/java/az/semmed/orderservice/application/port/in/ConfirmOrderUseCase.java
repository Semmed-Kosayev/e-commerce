package az.semmed.orderservice.application.port.in;

import az.semmed.orderservice.domain.Order;

public interface ConfirmOrderUseCase {

    Order confirmOrder(String orderId);
}
