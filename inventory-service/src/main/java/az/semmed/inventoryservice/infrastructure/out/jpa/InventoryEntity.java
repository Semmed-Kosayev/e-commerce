package az.semmed.inventoryservice.infrastructure.out.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
@Table(name = "inventories")
public class InventoryEntity {

    @Id
    private String productId;
    private Integer totalStock;
    private Integer reservedStock;

    @Version
    private Long version;
}
