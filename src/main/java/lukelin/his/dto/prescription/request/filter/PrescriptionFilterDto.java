package lukelin.his.dto.prescription.request.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.config.JsonConfig;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PrescriptionFilterDto {
    private UUID uuid;

    private List<PrescriptionStatus> prescriptionStatusList;

    private List<UUID> patientSignInIdList;

    private Boolean oneOff;

    private UUID departmentId;

    private List<UUID> departmentIdList;

    private UUID employeeId;

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date mockFutureDate;

    private List<PrescriptionType> prescriptionTypeList;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date cardDate; //护理卡片日期

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date changedStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date changedEndDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordFeeDate;

    private List<String> useMethodList;

    private Boolean treatmentCardFilter;

    private Boolean labBottleCardFilter;

    private String description;

    private Date startDate;

    private Date endDate;

    private Boolean orderByDesc = false;

    private Integer medicineTypeId;

    public Integer getMedicineTypeId() {
        return medicineTypeId;
    }

    public void setMedicineTypeId(Integer medicineTypeId) {
        this.medicineTypeId = medicineTypeId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getOrderByDesc() {
        return orderByDesc;
    }

    public void setOrderByDesc(Boolean orderByDesc) {
        this.orderByDesc = orderByDesc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getLabBottleCardFilter() {
        return labBottleCardFilter;
    }

    public void setLabBottleCardFilter(Boolean labBottleCardFilter) {
        this.labBottleCardFilter = labBottleCardFilter;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public Boolean getTreatmentCardFilter() {
        return treatmentCardFilter;
    }

    public void setTreatmentCardFilter(Boolean treatmentCardFilter) {
        this.treatmentCardFilter = treatmentCardFilter;
    }

    public Date getMockFutureDate() {
        return mockFutureDate;
    }

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public void setMockFutureDate(Date mockFutureDate) {
        this.mockFutureDate = mockFutureDate;
    }

    public List<String> getUseMethodList() {
        return useMethodList;
    }

    public void setUseMethodList(List<String> useMethodList) {
        this.useMethodList = useMethodList;
    }

    public Date getCardDate() {
        return cardDate;
    }

    public void setCardDate(Date cardDate) {
        this.cardDate = cardDate;
    }

    public Date getRecordFeeDate() {
        return recordFeeDate;
    }

    public void setRecordFeeDate(Date recordFeeDate) {
        this.recordFeeDate = recordFeeDate;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public List<PrescriptionType> getPrescriptionTypeList() {
        return prescriptionTypeList;
    }

    public void setPrescriptionTypeList(List<PrescriptionType> prescriptionTypeList) {
        this.prescriptionTypeList = prescriptionTypeList;
    }

    public Date getChangedStartDate() {
        return changedStartDate;
    }

    public void setChangedStartDate(Date changedStartDate) {
        this.changedStartDate = changedStartDate;
    }

    public Date getChangedEndDate() {
        return changedEndDate;
    }

    public void setChangedEndDate(Date changedEndDate) {
        this.changedEndDate = changedEndDate;
    }

    public List<UUID> getPatientSignInIdList() {
        return patientSignInIdList;
    }

    public void setPatientSignInIdList(List<UUID> patientSignInIdList) {
        this.patientSignInIdList = patientSignInIdList;
    }

    public List<PrescriptionStatus> getPrescriptionStatusList() {
        return prescriptionStatusList;
    }

    public void setPrescriptionStatusList(List<PrescriptionStatus> prescriptionStatusList) {
        this.prescriptionStatusList = prescriptionStatusList;
    }

    public Boolean getOneOff() {
        return oneOff;
    }

    public void setOneOff(Boolean oneOff) {
        this.oneOff = oneOff;
    }
}
