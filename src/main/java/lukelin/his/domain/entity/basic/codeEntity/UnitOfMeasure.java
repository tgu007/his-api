package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.enums.Basic.UnitOfMeasureType;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_unit_of_measure")
public class UnitOfMeasure extends BaseCodeEntity implements DtoConvertible<UnitOfMeasureDto> {
    public UnitOfMeasure() {
    }

    public UnitOfMeasure(UUID uuid) {
        this.setUuid(uuid);
    }

    @Column(nullable = false)
    private UnitOfMeasureType type;

    public UnitOfMeasureType getType() {
        return type;
    }

    public void setType(UnitOfMeasureType type) {
        this.type = type;
    }

    @Override
    public UnitOfMeasureDto toDto() {
        UnitOfMeasureDto responseDto = DtoUtils.convertRawDto(this);
        responseDto.setType(this.type);
        return responseDto;
    }
}
