package lukelin.his.domain.Interfaces.Inventory;

import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;

import java.math.BigDecimal;
import java.util.List;

public interface EntityBaseStockSummaryRespInterface {
    List<BaseStockSummaryRespDto> getStockSummaryList();

    InventoryEntityInterface inventoryEntity();

    default BigDecimal getTotalQuantity() {
        return this.getStockSummaryList().stream().map(BaseStockSummaryRespDto::getMinUomQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default String getMinUomDisplayQuantity() {
        return this.getTotalQuantity().toString() + this.inventoryEntity().getMinUom().getName();
    }

    default String getWarehouseDisplayQuantity() {
        return this.inventoryEntity().getDisplayQuantity(WarehouseType.levelOneWarehouse, this.getTotalQuantity());
    }

    default String getDepartmentDisplayQuantity() {
        return this.inventoryEntity().getDisplayQuantity(WarehouseType.wardWarehouse, this.getTotalQuantity());
    }
}
