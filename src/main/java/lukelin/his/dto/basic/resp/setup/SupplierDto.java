package lukelin.his.dto.basic.resp.setup;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.Supplier;
import lukelin.his.domain.enums.Basic.InventoryEntityType;
import lukelin.his.domain.enums.EntityType;

public class SupplierDto extends BaseCodeDto {
    private InventoryEntityType type;

    private String contactNumber;

    private String searchCode;

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public InventoryEntityType getType() {
        return type;
    }

    public void setType(InventoryEntityType type) {
        this.type = type;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Supplier toEntity() {
        Supplier supplier = new Supplier();
        BeanUtils.copyPropertiesIgnoreNull(this, supplier);
        return supplier;
    }
}
