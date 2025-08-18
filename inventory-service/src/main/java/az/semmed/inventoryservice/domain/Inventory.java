package az.semmed.inventoryservice.domain;

import az.semmed.inventoryservice.domain.exception.InsufficientReservedStock;
import az.semmed.inventoryservice.domain.exception.InsufficientStock;

public class Inventory {

    private String productId;
    private int totalStock;
    private int reservedStock;

    public static Inventory createInventory(String productId, int totalStock) {
        Inventory inventory = new Inventory();

        inventory.productId = productId;
        inventory.totalStock = totalStock;
        inventory.reservedStock = 0;

        return inventory;
    }

    public static Inventory reform(String productId, Integer totalStock, Integer reservedStock) {
        return new Inventory(productId, totalStock, reservedStock);
    }

    public boolean canBeReserved(int quantity) {
        validateQuantity(quantity);
        return (this.totalStock - this.reservedStock) >= quantity;
    }

    public void reserveStock(int quantity) {
        validateQuantity(quantity);
        if (!canBeReserved(quantity)) {
            throw new InsufficientStock("Cannot reserve " + quantity + " items. Insufficient stock.");
        }
        this.reservedStock += quantity;
    }

    public void deductStock(int quantity) {
        validateQuantity(quantity);
        if (this.reservedStock < quantity) {
            throw new InsufficientReservedStock("Cannot deduct " + quantity + " items. Reserved stock is lower than deduction amount.");
        }
        if (this.totalStock < quantity) {
            throw new InsufficientStock("Cannot deduct " + quantity + " items. Total stock is lower than deduction amount.");
        }
        this.totalStock -= quantity;
        this.reservedStock -= quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }

    private Inventory(String productId, Integer totalStock, Integer reservedStock) {
        this.productId = productId;
        this.totalStock = totalStock;
        this.reservedStock = reservedStock;
    }

    private Inventory() {
    }

    public String getProductId() {
        return productId;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public int getReservedStock() {
        return reservedStock;
    }
}
