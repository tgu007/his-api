package lukelin.his.dto.basic.resp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.yb.TreatmentUploadResult;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.YB.YBMatchStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.yb.resp.CenterTreatmentRespDto;

import javax.persistence.*;
import java.util.*;

public class TreatmentRespDto extends BaseEntityRespDto {
    private DepartmentTreatmentType executeDepartmentType;

    private DepartmentTreatmentDto defaultExecuteDepartment;

    private CenterTreatmentRespDto centerTreatment;

    private YBUploadStatus ybUploadStatus;

    private String ybUploadError;

    private YBMatchStatus ybMatchStatus;

    private String ybMatchError;

    private Integer duration;

    private DictionaryDto recoveryType;

    private Boolean allowMultiExecution;

    private Boolean allowManualFee;

    private Boolean allowAutoFee;

    private Boolean prescriptionRequired;

    private Boolean showInCard;

    private DictionaryDto labTreatmentType;

    private DictionaryDto labSampleType;

    private Boolean combo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastModifiedDate;

    private String lastModifiedBy;

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    private List<TreatmentRespDto> childTreatmentList;

    public List<TreatmentRespDto> getChildTreatmentList() {
        return childTreatmentList;
    }

    public void setChildTreatmentList(List<TreatmentRespDto> childTreatmentList) {
        this.childTreatmentList = childTreatmentList;
    }

    public Boolean getCombo() {
        return combo;
    }

    public void setCombo(Boolean combo) {
        this.combo = combo;
    }

    public DictionaryDto getLabTreatmentType() {
        return labTreatmentType;
    }

    public void setLabTreatmentType(DictionaryDto labTreatmentType) {
        this.labTreatmentType = labTreatmentType;
    }

    public DictionaryDto getLabSampleType() {
        return labSampleType;
    }

    public void setLabSampleType(DictionaryDto labSampleType) {
        this.labSampleType = labSampleType;
    }

    public Boolean getShowInCard() {
        return showInCard;
    }

    public void setShowInCard(Boolean showInCard) {
        this.showInCard = showInCard;
    }

    public Boolean getAllowManualFee() {
        return allowManualFee;
    }

    public void setAllowManualFee(Boolean allowManualFee) {
        this.allowManualFee = allowManualFee;
    }

    public Boolean getAllowAutoFee() {
        return allowAutoFee;
    }

    public void setAllowAutoFee(Boolean allowAutoFee) {
        this.allowAutoFee = allowAutoFee;
    }

    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public DictionaryDto getRecoveryType() {
        return recoveryType;
    }

    public void setRecoveryType(DictionaryDto recoveryType) {
        this.recoveryType = recoveryType;
    }

    public Boolean getAllowMultiExecution() {
        return allowMultiExecution;
    }

    public void setAllowMultiExecution(Boolean allowMultiExecution) {
        this.allowMultiExecution = allowMultiExecution;
    }

    public CenterTreatmentRespDto getCenterTreatment() {
        return centerTreatment;
    }

    public void setCenterTreatment(CenterTreatmentRespDto centerTreatment) {
        this.centerTreatment = centerTreatment;
    }

    public YBUploadStatus getYbUploadStatus() {
        return ybUploadStatus;
    }

    public void setYbUploadStatus(YBUploadStatus ybUploadStatus) {
        this.ybUploadStatus = ybUploadStatus;
    }

    public String getYbUploadError() {
        return ybUploadError;
    }

    public void setYbUploadError(String ybUploadError) {
        this.ybUploadError = ybUploadError;
    }

    public YBMatchStatus getYbMatchStatus() {
        return ybMatchStatus;
    }

    public void setYbMatchStatus(YBMatchStatus ybMatchStatus) {
        this.ybMatchStatus = ybMatchStatus;
    }

    public String getYbMatchError() {
        return ybMatchError;
    }

    public void setYbMatchError(String ybMatchError) {
        this.ybMatchError = ybMatchError;
    }

    public DepartmentTreatmentType getExecuteDepartmentType() {
        return executeDepartmentType;
    }

    public void setExecuteDepartmentType(DepartmentTreatmentType executeDepartmentType) {
        this.executeDepartmentType = executeDepartmentType;
    }

    public DepartmentTreatmentDto getDefaultExecuteDepartment() {
        return defaultExecuteDepartment;
    }

    public void setDefaultExecuteDepartment(DepartmentTreatmentDto defaultExecuteDepartment) {
        this.defaultExecuteDepartment = defaultExecuteDepartment;
    }
}
