package az.semmed.inventoryservice.application.port.out;

public interface KafkaProducerPort {

    void sendInventoryReservedEvent(String orderId);

    void sendInventoryUnavailableEvent(String orderId);

}
