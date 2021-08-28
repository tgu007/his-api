package lukelin.his.dto.account.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.YB.YBUploadStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FeeListDto extends BaseFeeListDto {
    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date feeDate;


    private String prescriptionDescription;

    private FeeStatus feeStatus;

    @JsonFormat(pattern = "MM/dd HH:mm", timezone = "GMT+8")
    private Date getShortFormatFeeDate() {
        return feeDate;
    }

    private YBUploadStatus uploadStatus;

    private String selfRatio;

    private BigDecimal selfFeeAmount;

    private BigDecimal insuranceAmount;

    private String uploadError;

    private Integer duration;

    private Boolean overlapping;

    private Boolean allowMultiExecution;

    private String whoCreated;

    private UUID departmentId;

    private FeeRecordMethod feeRecordMethod;

    private String supervisorName;

    private Boolean selfPay;

    private Boolean missingSupervisor;

    public Boolean getMissingSupervisor() {
        return missingSupervisor;
    }

    public void setMissingSupervisor(Boolean missingSupervisor) {
        this.missingSupervisor = missingSupervisor;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public FeeRecordMethod getFeeRecordMethod() {
        return feeRecordMethod;
    }

    public void setFeeRecordMethod(FeeRecordMethod feeRecordMethod) {
        this.feeRecordMethod = feeRecordMethod;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public Boolean getAllowMultiExecution() {
        return allowMultiExecution;
    }

    public void setAllowMultiExecution(Boolean allowMultiExecution) {
        this.allowMultiExecution = allowMultiExecution;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getOverlapping() {
        return overlapping;
    }

    public void setOverlapping(Boolean overlapping) {
        this.overlapping = overlapping;
    }

    public YBUploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(YBUploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getSelfRatio() {
        return selfRatio;
    }

    public void setSelfRatio(String selfRatio) {
        this.selfRatio = selfRatio;
    }

    public BigDecimal getSelfFeeAmount() {
        return selfFeeAmount;
    }

    public void setSelfFeeAmount(BigDecimal selfFeeAmount) {
        this.selfFeeAmount = selfFeeAmount;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getUploadError() {
        return uploadError;
    }

    public void setUploadError(String uploadError) {
        this.uploadError = uploadError;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getPrescriptionDescription() {
        return prescriptionDescription;
    }

    public void setPrescriptionDescription(String prescriptionDescription) {
        this.prescriptionDescription = prescriptionDescription;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }
}
