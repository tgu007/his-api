package lukelin.his.dto.basic.req;

import lukelin.his.domain.enums.Basic.InventoryEntityType;

public class QuickAddBrandDto extends QuickAddCodeEntityDto {
    private InventoryEntityType entityType;

    public InventoryEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(InventoryEntityType entityType) {
        this.entityType = entityType;
    }
}
