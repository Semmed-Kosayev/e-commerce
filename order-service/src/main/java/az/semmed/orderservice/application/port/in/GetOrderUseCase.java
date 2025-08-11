package az.semmed.orderservice.application.port.in;

import az.semmed.orderservice.domain.Order;

public interface GetOrderUseCase {

    Order getOrder(String orderId);
}
