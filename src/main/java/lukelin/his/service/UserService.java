package lukelin.his.service;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.ExpressionList;
import lukelin.common.springboot.ebean.DefaultCurrentUserProvider;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.springboot.service.BaseService;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.Organization;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.yb.DoctorAgreement;
import lukelin.his.dto.yb.req.DoctorAgreementSaveDto;
import lukelin.his.dto.basic.req.EmployeeProfileReqDto;
import lukelin.his.dto.basic.req.NewEmployeeReqDto;
import lukelin.his.dto.basic.req.UserPermissionUpdateDto;
import lukelin.his.dto.basic.req.filter.EmployeeFilter;
import lukelin.sdk.account.dto.response.AccountDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService {
    @Autowired
    private EbeanServer ebeanServer;

    @Autowired
    private DefaultCurrentUserProvider currentUserProvider;

    public void createEmployee(AccountDTO accountDto, NewEmployeeReqDto newEmployeeDto) {
        Organization organization = null;
        Employee newEmployee = newEmployeeDto.toEmployee(accountDto);
        List<Organization> organizationList = this.ebeanServer.find(Organization.class).findList();
        if (organizationList.size() > 0)
            newEmployee.setOrganization(organizationList.get(0));
        ebeanServer.save(newEmployee);
    }

    public List<Employee> getEmployeeList(EmployeeFilter employeeFilter) {
        ExpressionList<Employee> el =
                ebeanServer.find(Employee.class).orderBy("whenCreated desc")
                        .where();

        if (employeeFilter.getAllowSupervise() != null)
            el = el.eq("allowSupervise", employeeFilter.getAllowSupervise());

        if (employeeFilter.getDepartmentId() != null)
            el = el.query().fetch("departmentList").where()
                    .eq("departmentList.uuid", employeeFilter.getDepartmentId());

        if (employeeFilter.getDepartmentIdList() != null)
            el = el.query().fetch("departmentList").where()
                    .in("departmentList.uuid", employeeFilter.getDepartmentIdList());

        if (employeeFilter.getEnabled() != null)
            el = el.eq("enabled", employeeFilter.getEnabled());

        if (employeeFilter.getUserRoleType() != null)
            el = el.query().fetch("userRoleList").where()
                    .eq("userRoleList.userRoleType", employeeFilter.getUserRoleType());

        if (employeeFilter.getUserRoleId() != null)
            el = el.query().fetch("userRoleList").where()
                    .eq("userRoleList.uuid", employeeFilter.getUserRoleId());

        if (employeeFilter.getPhoneSearchCode() != null)
            el = el.like("mobileNumber", "%" + employeeFilter.getPhoneSearchCode() + "%");

        if (employeeFilter.getNameSearchCode() != null)
            el = el.like("name", "%" + employeeFilter.getNameSearchCode() + "%");

        //List<Employee> employeeList = el.findList().stream().distinct().collect(Collectors.toList());
        return el.findList().stream().distinct().collect(Collectors.toList());
    }


    public Employee getEmployee(UUID accountId) {
        return ebeanServer.find(Employee.class).where()
                .eq("accountId", accountId)
                .findOne();
    }

    public Employee getEmployeeByOuathAccount(UUID accountId) {
        Optional<Employee> optionalEmployee = ebeanServer.find(Employee.class).where().eq("oauthAccountId", accountId).findOneOrEmpty();
        return optionalEmployee.orElseGet(Employee::new);
    }

    public void activateEmployee(UUID employeeId, boolean active) {
        Employee employee = ebeanServer.find(Employee.class, employeeId);
        employee.setEnabled(active);
        ebeanServer.save(employee);
    }

    public void updateUserPermission(UserPermissionUpdateDto updatePermissionDto) {
        Employee employee = ebeanServer.find(Employee.class, updatePermissionDto.getUuid());
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(new UserRole(updatePermissionDto.getRoleId()));

        Set<DepartmentTreatment> departmentTreatmentSet = new HashSet<>();
        for (UUID departmentId : updatePermissionDto.getDepartmentIdList())
            departmentTreatmentSet.add(new DepartmentTreatment(departmentId));

        Set<DepartmentWarehouse> warehouseSet = new HashSet<>();
        for (UUID warehouseId : updatePermissionDto.getWarehouseIdList())
            warehouseSet.add(new DepartmentWarehouse(warehouseId));

        employee.setWarehouseList(warehouseSet);
        employee.setDepartmentList(departmentTreatmentSet);
        employee.setUserRoleList(userRoleSet);
        ebeanServer.update(employee);
    }

    public void updateEmployeeTreatmentOperation(UUID employeeId, UUID treatmentId, boolean operate) {
        Employee employee = ebeanServer.find(Employee.class, employeeId);
        Optional<Treatment> existingTreatment = employee.getTreatmentList().stream().filter(t -> t.getUuid().equals(treatmentId)).findFirst();
        if (operate && !existingTreatment.isPresent())
            employee.getTreatmentList().add(this.findById(Treatment.class, treatmentId));
        else if (!operate && existingTreatment.isPresent())
            employee.getTreatmentList().remove(existingTreatment.get());
        ebeanServer.save(employee);
    }


    public void updateEmployeeProfile(UUID employeeId, EmployeeProfileReqDto reqDto) {
        Employee employee = ebeanServer.find(Employee.class, employeeId);
        employee.setCertificationNumber(reqDto.getCertificationNumber());
        employee.setAllowSupervise(reqDto.getAllowSupervise());
        ebeanServer.save(employee);
    }


    public void validateDoctorAgreement(Employee doctor) {
        if (doctor == null)
            doctor = this.getCurrentUser();
        DoctorAgreement doctorAgreement = doctor.getDoctorAgreementNumber();
        if (doctorAgreement == null || StringUtils.isBlank(doctorAgreement.getAgreementNumber()))
            throw new ApiValidationException("没有找到医生的协议编号，请通知医保办更新协议编号");
    }

    public Employee getCurrentUser() {
        return Ebean.find(Employee.class).where().eq("accountId", this.currentUserProvider.getCurrentUser()).findOne();
    }
}
