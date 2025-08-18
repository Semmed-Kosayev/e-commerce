package az.semmed.inventoryservice.application.mapper;

import az.semmed.inventoryservice.domain.Inventory;
import az.semmed.inventoryservice.infrastructure.out.jpa.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public Inventory toInventory(InventoryEntity entity) {
        return Inventory.reform(
                entity.getProductId(),
                entity.getTotalStock(),
                entity.getReservedStock()
        );
    }

    public InventoryEntity toInventoryEntity(Inventory inventory) {
        InventoryEntity inventoryEntity = new InventoryEntity();

        inventoryEntity.setProductId(inventory.getProductId());
        inventoryEntity.setTotalStock(inventory.getTotalStock());
        inventoryEntity.setReservedStock(inventory.getReservedStock());

        return inventoryEntity;
    }

    public void updateInventory(InventoryEntity inventoryEntity, Inventory inventory) {
        inventoryEntity.setTotalStock(inventory.getTotalStock());
        inventoryEntity.setReservedStock(inventory.getReservedStock());
    }

}
