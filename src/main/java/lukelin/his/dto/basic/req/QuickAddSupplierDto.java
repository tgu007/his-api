package lukelin.his.dto.basic.req;

import lukelin.his.domain.enums.Basic.InventoryEntityType;

public class QuickAddSupplierDto extends QuickAddCodeEntityDto {
    private InventoryEntityType entityType;

    private String contactNumber;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public InventoryEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(InventoryEntityType entityType) {
        this.entityType = entityType;
    }
}
