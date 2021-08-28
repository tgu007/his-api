package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.enums.Basic.DiagnoseType;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_diagnose")
public class Diagnose extends BaseSearchableCodeEntity implements DtoConvertible<DiagnoseDto> {
    public Diagnose(UUID uuid) {
        this.setUuid(uuid);
    }

    public Diagnose() {
        super();
    }

    @Column(name = "insurance_code", length = 50)
    private String insuranceCode;

    @Column(name = "type")
    private DiagnoseType type;

    public DiagnoseType getType() {
        return type;
    }

    public void setType(DiagnoseType type) {
        this.type = type;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

    @Override
    public DiagnoseDto toDto() {
        DiagnoseDto diagnoseDto = DtoUtils.convertRawDto(this);
        return diagnoseDto;
    }
}
