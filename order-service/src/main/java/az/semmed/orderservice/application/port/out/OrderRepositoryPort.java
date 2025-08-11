package az.semmed.orderservice.application.port.out;

import az.semmed.orderservice.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepositoryPort {

    Order save(Order order);
    List<Order> findCustomerOrdersByEmail(String customerEmail);
    Optional<Order> findById(String id);

}
