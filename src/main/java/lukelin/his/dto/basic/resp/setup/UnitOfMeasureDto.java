package lukelin.his.dto.basic.resp.setup;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.enums.Basic.UnitOfMeasureType;

import java.util.UUID;

public class UnitOfMeasureDto extends BaseCodeDto {
    private UUID uuid;

    private String code;

    private UnitOfMeasureType type;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UnitOfMeasureType getType() {
        return type;
    }

    public void setType(UnitOfMeasureType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UnitOfMeasure toEntity() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        BeanUtils.copyPropertiesIgnoreNull(this, unitOfMeasure);
        return unitOfMeasure;
    }
}
