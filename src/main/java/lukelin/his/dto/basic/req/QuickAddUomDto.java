package lukelin.his.dto.basic.req;

import lukelin.his.domain.enums.Basic.UnitOfMeasureType;

public class QuickAddUomDto extends QuickAddCodeEntityDto {
    private UnitOfMeasureType uomType;

    public UnitOfMeasureType getUomType() {
        return uomType;
    }

    public void setUomType(UnitOfMeasureType uomType) {
        this.uomType = uomType;
    }
}
