package az.semmed.inventoryservice.application.port.out;

import az.semmed.kafkasharedclasses.inventory.InventoryReservedEvent;
import az.semmed.kafkasharedclasses.inventory.InventoryUnavailableEvent;

public interface KafkaProducerPort {

    void sendInventoryReservedEvent(InventoryReservedEvent inventoryReservedEvent);
    void sendInventoryUnavailableEvent(InventoryUnavailableEvent inventoryUnavailableEvent);

}
