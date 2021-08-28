package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.yb_drg.BloodTypeRhDto;
import lukelin.his.dto.yb_drg.DiagnoseDirectionDto;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.diagnose_direction")
public class DiagnoseDirection extends BaseCodeEntity implements DtoConvertible<DiagnoseDirectionDto> {
    @Column(nullable = false, name = "default")
    private boolean defaultSelection;

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    @Override
    public DiagnoseDirectionDto toDto() {
        DiagnoseDirectionDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
