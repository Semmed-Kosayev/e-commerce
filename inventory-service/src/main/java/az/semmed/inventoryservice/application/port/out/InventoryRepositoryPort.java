package az.semmed.inventoryservice.application.port.out;

import az.semmed.inventoryservice.domain.Inventory;

import java.util.Optional;

public interface InventoryRepositoryPort {

    Inventory save(Inventory inventory);
    Optional<Inventory> findById(String id);

}
