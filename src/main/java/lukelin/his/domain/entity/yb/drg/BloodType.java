package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.yb_drg.AllergyMedicineDto;
import lukelin.his.dto.yb_drg.BloodTypeDto;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.blood_type")
public class BloodType extends BaseCodeEntity implements DtoConvertible<BloodTypeDto> {
    @Column(nullable = false, name = "default")
    private boolean defaultSelection;

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    @Override
    public BloodTypeDto toDto() {
        BloodTypeDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
