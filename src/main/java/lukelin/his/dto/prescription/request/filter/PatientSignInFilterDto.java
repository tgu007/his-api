package lukelin.his.dto.prescription.request.filter;

import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.basic.SearchCodeDto;

import java.util.List;
import java.util.UUID;

public class PatientSignInFilterDto extends SearchCodeDto {
    private Boolean check3024 = false;

    private List<UUID> departmentIdList;

    private List<PatientSignInStatus> statusList;

    private Boolean pendingUploadFee;

    public Boolean getCheck3024() {
        return check3024;
    }

    public void setCheck3024(Boolean check3024) {
        this.check3024 = check3024;
    }

    private List<String> insuranceTypeList;

    public List<String> getInsuranceTypeList() {
        return insuranceTypeList;
    }

    public void setInsuranceTypeList(List<String> insuranceTypeList) {
        this.insuranceTypeList = insuranceTypeList;
    }

    public Boolean getPendingUploadFee() {
        return pendingUploadFee;
    }

    public void setPendingUploadFee(Boolean pendingUploadFee) {
        this.pendingUploadFee = pendingUploadFee;
    }

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public List<PatientSignInStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<PatientSignInStatus> statusList) {
        this.statusList = statusList;
    }
}
