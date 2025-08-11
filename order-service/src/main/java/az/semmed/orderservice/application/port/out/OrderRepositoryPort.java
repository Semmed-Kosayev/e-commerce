package az.semmed.orderservice.application.port.out;

import az.semmed.orderservice.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositoryPort {

    Order save(Order order);
    List<Order> findCustomerOrdersByEmail(String customerEmail);

}
