package lukelin.his.dto.prescription.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.BooleanSupplier;

public class PrescriptionListRespDto {
    private String groupColour;

    private Integer groupIndex;

    private UUID groupId;

    private UUID uuid;

    private String description;

    private String serveInfo;

    private String quantityInfo;

    private String firstDayQuantityInfo;

    private String issueQuantityInfo;

    private String frequency;

    private String useMethod;

    private String dropSpeed;

    private BigDecimal unitPrice;

    private String reference;

    private boolean covered;

    private boolean oneOff;

    private String whoCreated;

    private String approvedBy;

    private String stoppedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date whenCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date whenModified;

    private PrescriptionType type;

    private PrescriptionStatus status;

    private List<PrescriptionChangeLogRespDto> changeLogRespDtoList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    private String confirmStopBy;

    private String executeDepartment;

    private boolean allowEdit;

    private UUID prescriptionChargeableId;

    private UUID prescriptionDetailId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date lastExecutionTime;

    private BigDecimal fixedQuantity;

    private Boolean printSlipTypeOne;

    private Boolean printSlipTypeTwo;

    private String frequencyName;

    private Boolean selfPrepared;

    private String fixedQuantityInfo;

    private BigDecimal adjustQuantity;

    private String diagnoseName;

    private String popUpInfo;

    private Boolean printSlipChinese;

    private String sampleType;

    private Boolean labTest = false;

    public Boolean getLabTest() {
        return labTest;
    }

    public void setLabTest(Boolean labTest) {
        this.labTest = labTest;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public Boolean getPrintSlipChinese() {
        return printSlipChinese;
    }

    public void setPrintSlipChinese(Boolean printSlipChinese) {
        this.printSlipChinese = printSlipChinese;
    }

    public String getPopUpInfo() {
        return popUpInfo;
    }

    public void setPopUpInfo(String popUpInfo) {
        this.popUpInfo = popUpInfo;
    }

    public String getDiagnoseName() {
        return diagnoseName;
    }

    public void setDiagnoseName(String diagnoseName) {
        this.diagnoseName = diagnoseName;
    }

    public String getConfirmStopBy() {
        return confirmStopBy;
    }

    public void setConfirmStopBy(String confirmStopBy) {
        this.confirmStopBy = confirmStopBy;
    }

    public BigDecimal getAdjustQuantity() {
        return adjustQuantity;
    }

    public void setAdjustQuantity(BigDecimal adjustQuantity) {
        this.adjustQuantity = adjustQuantity;
    }

    public String getFixedQuantityInfo() {
        return fixedQuantityInfo;
    }

    public void setFixedQuantityInfo(String fixedQuantityInfo) {
        this.fixedQuantityInfo = fixedQuantityInfo;
    }

    public Boolean getSelfPrepared() {
        return selfPrepared;
    }

    public void setSelfPrepared(Boolean selfPrepared) {
        this.selfPrepared = selfPrepared;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public Boolean getPrintSlipTypeOne() {
        return printSlipTypeOne;
    }

    public void setPrintSlipTypeOne(Boolean printSlipTypeOne) {
        this.printSlipTypeOne = printSlipTypeOne;
    }

    public Boolean getPrintSlipTypeTwo() {
        return printSlipTypeTwo;
    }

    public void setPrintSlipTypeTwo(Boolean printSlipTypeTwo) {
        this.printSlipTypeTwo = printSlipTypeTwo;
    }

    public BigDecimal getFixedQuantity() {
        return fixedQuantity;
    }

    public void setFixedQuantity(BigDecimal fixedQuantity) {
        this.fixedQuantity = fixedQuantity;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getStoppedBy() {
        return stoppedBy;
    }

    public void setStoppedBy(String stoppedBy) {
        this.stoppedBy = stoppedBy;
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    public Date getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(Date lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public Date getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Date whenModified) {
        this.whenModified = whenModified;
    }

    public UUID getPrescriptionChargeableId() {
        return prescriptionChargeableId;
    }

    public void setPrescriptionChargeableId(UUID prescriptionChargeableId) {
        this.prescriptionChargeableId = prescriptionChargeableId;
    }

    public UUID getPrescriptionDetailId() {
        return prescriptionDetailId;
    }

    public void setPrescriptionDetailId(UUID prescriptionDetailId) {
        this.prescriptionDetailId = prescriptionDetailId;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public String getExecuteDepartment() {
        return executeDepartment;
    }

    public void setExecuteDepartment(String executeDepartment) {
        this.executeDepartment = executeDepartment;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIssueQuantityInfo() {
        return issueQuantityInfo;
    }

    public void setIssueQuantityInfo(String issueQuantityInfo) {
        this.issueQuantityInfo = issueQuantityInfo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<PrescriptionChangeLogRespDto> getChangeLogRespDtoList() {
        return changeLogRespDtoList;
    }

    public void setChangeLogRespDtoList(List<PrescriptionChangeLogRespDto> changeLogRespDtoList) {
        this.changeLogRespDtoList = changeLogRespDtoList;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public String getGroupColour() {
        return groupColour;
    }

    public void setGroupColour(String groupColour) {
        this.groupColour = groupColour;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServeInfo() {
        return serveInfo;
    }

    public void setServeInfo(String serveInfo) {
        this.serveInfo = serveInfo;
    }

    public String getQuantityInfo() {
        return quantityInfo;
    }

    public void setQuantityInfo(String quantityInfo) {
        this.quantityInfo = quantityInfo;
    }

    public String getFirstDayQuantityInfo() {
        return firstDayQuantityInfo;
    }

    public void setFirstDayQuantityInfo(String firstDayQuantityInfo) {
        this.firstDayQuantityInfo = firstDayQuantityInfo;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(String useMethod) {
        this.useMethod = useMethod;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public boolean isOneOff() {
        return oneOff;
    }

    public void setOneOff(boolean oneOff) {
        this.oneOff = oneOff;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public PrescriptionType getType() {
        return type;
    }

    public void setType(PrescriptionType type) {
        this.type = type;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }
}
