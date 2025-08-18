package az.semmed.inventoryservice.infrastructure.out.jpa;

import az.semmed.inventoryservice.application.mapper.InventoryMapper;
import az.semmed.inventoryservice.application.port.out.InventoryRepositoryPort;
import az.semmed.inventoryservice.domain.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryJpaAdapter implements InventoryRepositoryPort {

    private final InventoryJpaRepository inventoryJpaRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity inventoryEntity = inventoryJpaRepository.findById(inventory.getProductId())
                .orElseThrow(() -> new RuntimeException("Entity not found: " + inventory.getProductId()));
        inventoryMapper.updateInventory(inventoryEntity, inventory);
        InventoryEntity save = inventoryJpaRepository.save(inventoryEntity);
        return inventoryMapper.toInventory(save);
    }

    @Override
    public Optional<Inventory> findById(String id) {
        return inventoryJpaRepository.findById(id).map(inventoryMapper::toInventory);
    }
}
