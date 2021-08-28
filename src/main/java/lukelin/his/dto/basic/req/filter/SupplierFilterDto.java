package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.enums.Basic.InventoryEntityType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.dto.basic.SearchCodeDto;

public class SupplierFilterDto extends SearchCodeDto {
    private InventoryEntityType supplierType;

    public InventoryEntityType getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(InventoryEntityType supplierType) {
        this.supplierType = supplierType;
    }
}
