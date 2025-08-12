package az.semmed.orderservice.infrastructure.adapter.out.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {

    @EntityGraph(attributePaths = "items")
    List<OrderEntity> findAllByCustomerEmail(String customerEmail);

    @EntityGraph(attributePaths = "items")
    Optional<OrderEntity> findById(String orderId);

}
