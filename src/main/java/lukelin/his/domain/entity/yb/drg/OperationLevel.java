package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.yb_drg.OperationLevelDto;
import lukelin.his.dto.yb_drg.SpecialDiseaseDto;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.operation_level")
public class OperationLevel extends BaseCodeEntity implements DtoConvertible<OperationLevelDto> {
    @Column(nullable = false, name = "default")
    private boolean defaultSelection;

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    @Override
    public OperationLevelDto toDto() {
        OperationLevelDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
