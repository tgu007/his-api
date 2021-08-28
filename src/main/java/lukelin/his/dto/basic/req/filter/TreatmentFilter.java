package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;

import java.util.List;

public class TreatmentFilter extends EntityFilter {
    private DepartmentTreatmentType treatmentType;

    private List<DepartmentTreatmentType> treatmentTypeList;

    private Boolean ybNotUploaded = false;

    private Boolean ybNotMatched = false;

    private Boolean excludeCombo;

    public Boolean getExcludeCombo() {
        return excludeCombo;
    }

    public void setExcludeCombo(Boolean excludeCombo) {
        this.excludeCombo = excludeCombo;
    }

    public List<DepartmentTreatmentType> getTreatmentTypeList() {
        return treatmentTypeList;
    }

    public void setTreatmentTypeList(List<DepartmentTreatmentType> treatmentTypeList) {
        this.treatmentTypeList = treatmentTypeList;
    }

    public Boolean getYbNotUploaded() {
        return ybNotUploaded;
    }

    public void setYbNotUploaded(Boolean ybNotUploaded) {
        this.ybNotUploaded = ybNotUploaded;
    }

    public Boolean getYbNotMatched() {
        return ybNotMatched;
    }

    public void setYbNotMatched(Boolean ybNotMatched) {
        this.ybNotMatched = ybNotMatched;
    }

    public DepartmentTreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(DepartmentTreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }
}
