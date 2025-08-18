package az.semmed.inventoryservice.service;

import az.semmed.inventoryservice.application.mapper.InventoryMapper;
import az.semmed.inventoryservice.application.port.in.ReserveInventoryForOrderUseCase;
import az.semmed.inventoryservice.application.port.out.InventoryRepositoryPort;
import az.semmed.inventoryservice.application.port.out.KafkaProducerPort;
import az.semmed.inventoryservice.domain.Inventory;
import az.semmed.inventoryservice.domain.exception.InsufficientStock;
import az.semmed.inventoryservice.service.exception.ProductNotFound;
import az.semmed.kafkasharedclasses.inventory.InventoryReservedEvent;
import az.semmed.kafkasharedclasses.inventory.InventoryUnavailableEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService implements ReserveInventoryForOrderUseCase {

    private final InventoryRepositoryPort repository;
    private final KafkaProducerPort kafkaProducer;

    @Override
    public void reserveInventoryForOrder(ReserveInventoryCommand reserveInventoryCommand) {
        try {
            for (ReserveInventoryCommand.ProductCommand product : reserveInventoryCommand.products()) {
                String productId = product.productId();
                int quantity = product.quantity();

                Inventory inventory = repository.findById(productId)
                        .orElseThrow(() -> new ProductNotFound("Product not found with id: " + productId));

                inventory.reserveStock(quantity);
                repository.save(inventory);
            }

            kafkaProducer.sendInventoryReservedEvent(
                    new InventoryReservedEvent(reserveInventoryCommand.orderId())
            );

        } catch (InsufficientStock | ProductNotFound | ObjectOptimisticLockingFailureException e) {
            kafkaProducer.sendInventoryUnavailableEvent(
                    new InventoryUnavailableEvent(reserveInventoryCommand.orderId())
            );
        }
    }
}
