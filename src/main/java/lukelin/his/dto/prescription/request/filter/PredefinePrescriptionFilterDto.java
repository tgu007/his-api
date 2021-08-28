package lukelin.his.dto.prescription.request.filter;

import lukelin.his.domain.enums.Prescription.PreDefinedPrescriptionType;
import lukelin.his.dto.basic.SearchCodeDto;

public class PredefinePrescriptionFilterDto extends SearchCodeDto {
    private PreDefinedPrescriptionType type;

    private Boolean oneOff;

    public Boolean getOneOff() {
        return oneOff;
    }

    public void setOneOff(Boolean oneOff) {
        this.oneOff = oneOff;
    }

    public PreDefinedPrescriptionType getType() {
        return type;
    }

    public void setType(PreDefinedPrescriptionType type) {
        this.type = type;
    }
}
