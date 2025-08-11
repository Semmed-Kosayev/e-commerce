package az.semmed.orderservice.application.port.in;

import az.semmed.orderservice.domain.Order;

import java.util.List;

public interface GetCustomerOrdersUseCase {

    List<Order> getCustomerOrdersByEmail(String customerEmail);

}
