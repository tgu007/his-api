package lukelin.his.dto.basic.req;


import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.sdk.account.dto.request.AccountReqDTO;
import lukelin.sdk.account.dto.response.AccountDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class NewEmployeeReqDto {
    private String phone;

    private String password;

    private String name;

    private UUID userRoleId;

    private List<UUID> treatmentDepartmentIdList;

    private List<UUID> warehouseDepartmentIdList;

    private String certificationNumber;

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public UUID getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UUID userRoleId) {
        this.userRoleId = userRoleId;
    }

    public List<UUID> getTreatmentDepartmentIdList() {
        return treatmentDepartmentIdList;
    }

    public void setTreatmentDepartmentIdList(List<UUID> treatmentDepartmentIdList) {
        this.treatmentDepartmentIdList = treatmentDepartmentIdList;
    }

    public List<UUID> getWarehouseDepartmentIdList() {
        return warehouseDepartmentIdList;
    }

    public void setWarehouseDepartmentIdList(List<UUID> warehouseDepartmentIdList) {
        this.warehouseDepartmentIdList = warehouseDepartmentIdList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountReqDTO toAccountReqDto() {
        AccountReqDTO accountReqDTO = new AccountReqDTO();
        accountReqDTO.setPhone(this.getPhone());
        accountReqDTO.setPassword(this.getPassword());
        accountReqDTO.setFullName(this.getName());
        return accountReqDTO;
    }

    public Employee toEmployee(AccountDTO accountDto) {
        Employee employee = new Employee();
        employee.setAccountId(accountDto.getUuid());
        employee.setEnabled(false);
        employee.setName(this.getName());
        employee.setMobileNumber(accountDto.getPhone());
        employee.setCertificationNumber(this.getCertificationNumber());
        UserRole userRole = Ebean.find(UserRole.class, this.getUserRoleId());

        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRole);
        employee.setUserRoleList(userRoleSet);

        Set<DepartmentTreatment> departmentTreatmentSet = new HashSet<>();
        if (userRole.getDefaultDepartment() != null)
            departmentTreatmentSet.add(userRole.getDefaultDepartment());

        if (this.getTreatmentDepartmentIdList() != null)
            for (UUID departmentId : this.getTreatmentDepartmentIdList()) {
                DepartmentTreatment departmentTreatment = new DepartmentTreatment(departmentId);
                departmentTreatmentSet.add(departmentTreatment);
            }
        employee.setDepartmentList(departmentTreatmentSet);


        if (this.getWarehouseDepartmentIdList() != null) {
            Set<DepartmentWarehouse> departmentWarehouseSet = new HashSet<>();
            for (UUID warehouseId : this.getWarehouseDepartmentIdList()) {
                DepartmentWarehouse departmentWarehouse = new DepartmentWarehouse(warehouseId);
                departmentWarehouseSet.add(departmentWarehouse);
            }
            employee.setWarehouseList(departmentWarehouseSet);
        }
        return employee;
    }
}
