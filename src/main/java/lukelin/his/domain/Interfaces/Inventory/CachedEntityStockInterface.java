package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;

import java.math.BigDecimal;
import java.util.UUID;

public interface CachedEntityStockInterface {
    DepartmentWarehouse getWarehouse();

    BigDecimal getMinUomQuantity();

    OrderLineInterface getOriginPurchaseLine();

    UUID getUuid();

    String getStockName();
}
