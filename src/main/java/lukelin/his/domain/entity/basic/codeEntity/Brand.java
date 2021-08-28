package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.enums.Basic.InventoryEntityType;
import lukelin.his.dto.basic.resp.setup.BrandDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_brand")
public class Brand extends BaseSearchableCodeEntity implements DtoConvertible<BrandDto> {
    public Brand(UUID uuid) {
        this.setUuid(uuid);
    }

    public Brand() {
        super();
    }

    @Column(nullable = false)
    private InventoryEntityType type;

    public InventoryEntityType getType() {
        return type;
    }

    public void setType(InventoryEntityType type) {
        this.type = type;
    }

    @Override
    public BrandDto toDto() {
        BrandDto brandDto = DtoUtils.convertRawDto(this);
        return brandDto;
    }
}
