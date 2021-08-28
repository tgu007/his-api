package lukelin.his.dto.prescription.response;

import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

import java.util.*;

public class PrescriptionExecutionListRespDto extends BaseWardPatientListRespDto {
    private PrescriptionListRespDto listRespDto;

    private Integer allowedExecutionCount;

    private Integer executedCount;

    private String sampleType;

    private List<TreatmentRespDto> childTreatmentList;

    public Integer getExecutedCount() {
        return executedCount;
    }

    public void setExecutedCount(Integer executedCount) {
        this.executedCount = executedCount;
    }

    public List<TreatmentRespDto> getChildTreatmentList() {
        return childTreatmentList;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public void setChildTreatmentList(List<TreatmentRespDto> childTreatmentList) {
        this.childTreatmentList = childTreatmentList;
    }

    public PrescriptionListRespDto getListRespDto() {
        return listRespDto;
    }

    public void setListRespDto(PrescriptionListRespDto listRespDto) {
        this.listRespDto = listRespDto;
    }

    public Integer getAllowedExecutionCount() {
        return allowedExecutionCount;
    }

    public void setAllowedExecutionCount(Integer allowedExecutionCount) {
        this.allowedExecutionCount = allowedExecutionCount;
    }
}
