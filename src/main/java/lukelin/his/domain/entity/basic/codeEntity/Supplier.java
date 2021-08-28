package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.enums.Basic.InventoryEntityType;
import lukelin.his.dto.basic.resp.setup.SupplierDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_supplier")
public class Supplier extends BaseSearchableCodeEntity implements DtoConvertible<SupplierDto> {
    @Column(nullable = false)
    private InventoryEntityType type;

    @Column(name = "contact_number")
    private String contactNumber;

    public Supplier(UUID uuid) {
        this.setUuid(uuid);
    }

    public Supplier() {

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

    @Override
    public SupplierDto toDto() {
        SupplierDto supplierDto = DtoUtils.convertRawDto(this);
        return supplierDto;
    }
}
