package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.yb_drg.DiagnoseDirectionDto;
import lukelin.his.dto.yb_drg.SignOutMethodDto;
import lukelin.his.dto.yb_drg.SpecialDiseaseDto;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.special_disease")
public class SpecialDisease extends BaseCodeEntity implements DtoConvertible<SpecialDiseaseDto> {
    @Column(nullable = false, name = "default")
    private boolean defaultSelection;

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    @Override
    public SpecialDiseaseDto toDto() {
        SpecialDiseaseDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
