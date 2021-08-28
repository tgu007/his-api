package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.enums.Inventory.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public interface CachedTransactionInterface {
    void setMinUomQuantity(BigDecimal minUomQuantity);

    void setWarehouse(DepartmentWarehouse warehouse);

    void setInventoryEntity(InventoryEntityInterface inventoryItem);

    void setOriginPurchaseLine(OrderLineInterface orderLine);

    void setType(TransactionType transactionType);

    void setReasonId(UUID uuid);

    default void setPropertyValue(TransactionType transactionType, BigDecimal minUomQuantity, UUID reasonId, DepartmentWarehouse warehouse, InventoryEntityInterface inventoryEntity, OrderLineInterface originPurchaseLine) {
        this.setType(transactionType);
        this.setMinUomQuantity(minUomQuantity);
        this.setReasonId(reasonId);
        this.setWarehouse(warehouse);
        this.setInventoryEntity(inventoryEntity);
        this.setOriginPurchaseLine(originPurchaseLine);
    }
}
