package lukelin.his.domain.entity.yb;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseSearchableCodeEntity;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.yb.ICD9RespDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "yb.icd_9_cm3")
public class ICD9 extends BaseSearchableCodeEntity implements DtoConvertible<ICD9RespDto> {
    public ICD9(UUID uuid) {
        this.setUuid(uuid);
    }

    public ICD9() {
        super();
    }

    @Override
    public ICD9RespDto toDto() {
        ICD9RespDto respDto = DtoUtils.convertRawDto(this);
        return respDto;
    }
}
