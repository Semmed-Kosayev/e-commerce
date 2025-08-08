package az.semmed.orderservice.application.port.out;

import az.semmed.orderservice.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryPort {

    Order save(Order order);

}
