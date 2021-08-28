package lukelin.his.dto.basic.resp.setup;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.enums.Basic.DiagnoseType;

public class DiagnoseDto extends BaseCodeDto {
    private String searchCode;

    private String insuranceCode;

    private DiagnoseType type;

    public DiagnoseType getType() {
        return type;
    }

    public void setType(DiagnoseType type) {
        this.type = type;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }


    public Diagnose toEntity() {
        Diagnose diagnose = new Diagnose();
        BeanUtils.copyPropertiesIgnoreNull(this, diagnose);
        return diagnose;
    }
}
