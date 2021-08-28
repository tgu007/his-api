package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.yb_drg.DiagnoseDirectionDto;
import lukelin.his.dto.yb_drg.SignOutMethodDto;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.sign_out_method")
public class SignOutMethod extends BaseCodeEntity implements DtoConvertible<SignOutMethodDto> {
    @Column(nullable = false, name = "default")
    private boolean defaultSelection;

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    @Override
    public SignOutMethodDto toDto() {
        SignOutMethodDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
