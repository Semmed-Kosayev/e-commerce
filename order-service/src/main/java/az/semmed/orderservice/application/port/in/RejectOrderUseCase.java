package az.semmed.orderservice.application.port.in;

import az.semmed.orderservice.domain.Order;

public interface RejectOrderUseCase {

    Order rejectOrder(String orderId);
}
