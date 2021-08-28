package lukelin.his.dto.basic.resp.setup;

import lukelin.his.dto.basic.resp.OrganizationDto;
import lukelin.sdk.account.dto.response.AccountDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EmployeeDto extends BaseEmployeeDto {

//    private List<DepartmentTreatmentDto> departmentList;
//
//    private List<DepartmentWarehouseDto> warehouseList;

    private AccountDTO account;

    private Map<String, Object> uiPermission;

    private OrganizationDto organization;

    private List<UUID> departmentIdList;

    private List<UUID> wardIdList;

    private List<UUID> warehouseIdList;

    private String certificationNumber;

    private Boolean allowSupervise;

    private Boolean needSupervise;



    public Boolean getNeedSupervise() {
        return needSupervise;
    }

    public void setNeedSupervise(Boolean needSupervise) {
        this.needSupervise = needSupervise;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public Boolean getAllowSupervise() {
        return allowSupervise;
    }

    public void setAllowSupervise(Boolean allowSupervise) {
        this.allowSupervise = allowSupervise;
    }

    public List<UUID> getWarehouseIdList() {
        return warehouseIdList;
    }

    public void setWarehouseIdList(List<UUID> warehouseIdList) {
        this.warehouseIdList = warehouseIdList;
    }

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public List<UUID> getWardIdList() {
        return wardIdList;
    }

    public void setWardIdList(List<UUID> wardIdList) {
        this.wardIdList = wardIdList;
    }

    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }

    public Map<String, Object> getUiPermission() {
        return uiPermission;
    }

    public void setUiPermission(Map<String, Object> uiPermission) {
        this.uiPermission = uiPermission;
    }


//    public List<DepartmentTreatmentDto> getDepartmentList() {
//        return departmentList;
//    }
//
//    public void setDepartmentList(List<DepartmentTreatmentDto> departmentList) {
//        this.departmentList = departmentList;
//    }

//    public List<DepartmentWarehouseDto> getWarehouseList() {
//        return warehouseList;
//    }
//
//    public void setWarehouseList(List<DepartmentWarehouseDto> warehouseList) {
//        this.warehouseList = warehouseList;
//    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
}
