package lukelin.his.dto.account.filter;


import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.Fee.FeeStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FeeFilterDto {
    private Date startDate;

    private Date endDate;

    private String searchCode;

    private List<String> feeTypeList;

    private List<FeeStatus> feeStatusList;

    private List<UUID> departmentIdList;

    private List<DepartmentTreatmentType> otherTreatmentDepartmentList;

    private String whoCreated;

    private Boolean orderByDesc = true;

    private Boolean pendingUpload;

    private String prescriptionDescription;

    private UUID prescriptionId;

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionDescription() {
        return prescriptionDescription;
    }

    public void setPrescriptionDescription(String prescriptionDescription) {
        this.prescriptionDescription = prescriptionDescription;
    }

    public List<DepartmentTreatmentType> getOtherTreatmentDepartmentList() {
        return otherTreatmentDepartmentList;
    }

    public void setOtherTreatmentDepartmentList(List<DepartmentTreatmentType> otherTreatmentDepartmentList) {
        this.otherTreatmentDepartmentList = otherTreatmentDepartmentList;
    }

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public Boolean getPendingUpload() {
        return pendingUpload;
    }

    public void setPendingUpload(Boolean pendingUpload) {
        this.pendingUpload = pendingUpload;
    }

    public Boolean getOrderByDesc() {
        return orderByDesc;
    }

    public void setOrderByDesc(Boolean orderByDesc) {
        this.orderByDesc = orderByDesc;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
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

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public List<String> getFeeTypeList() {
        return feeTypeList;
    }

    public void setFeeTypeList(List<String> feeTypeList) {
        this.feeTypeList = feeTypeList;
    }

    public List<FeeStatus> getFeeStatusList() {
        return feeStatusList;
    }

    public void setFeeStatusList(List<FeeStatus> feeStatusList) {
        this.feeStatusList = feeStatusList;
    }
}
