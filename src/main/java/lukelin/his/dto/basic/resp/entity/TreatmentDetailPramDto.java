package lukelin.his.dto.basic.resp.entity;

import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.util.List;

public class TreatmentDetailPramDto {
    private List<UnitOfMeasureDto> minUomList;

    private List<DictionaryDto> feeTypeList;

    private List<DictionaryDto> recoverTypeList;

    private List<DepartmentTreatmentDto> departmentTreatmentList;

    private List<DictionaryDto> labTreatmentTypeList;

    private List<DictionaryDto> labSampleTypeList;

    public List<DictionaryDto> getLabTreatmentTypeList() {
        return labTreatmentTypeList;
    }

    public void setLabTreatmentTypeList(List<DictionaryDto> labTreatmentTypeList) {
        this.labTreatmentTypeList = labTreatmentTypeList;
    }

    public List<DictionaryDto> getLabSampleTypeList() {
        return labSampleTypeList;
    }

    public void setLabSampleTypeList(List<DictionaryDto> labSampleTypeList) {
        this.labSampleTypeList = labSampleTypeList;
    }

    public List<DepartmentTreatmentDto> getDepartmentTreatmentList() {
        return departmentTreatmentList;
    }

    public void setDepartmentTreatmentList(List<DepartmentTreatmentDto> departmentTreatmentList) {
        this.departmentTreatmentList = departmentTreatmentList;
    }

    public List<UnitOfMeasureDto> getMinUomList() {
        return minUomList;
    }

    public void setMinUomList(List<UnitOfMeasureDto> minUomList) {
        this.minUomList = minUomList;
    }

    public List<DictionaryDto> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<DictionaryDto> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

    public List<DictionaryDto> getRecoverTypeList() {
        return recoverTypeList;
    }

    public void setRecoverTypeList(List<DictionaryDto> recoverTypeList) {
        this.recoverTypeList = recoverTypeList;
    }
}
