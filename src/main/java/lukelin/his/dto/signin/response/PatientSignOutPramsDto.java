package lukelin.his.dto.signin.response;

import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import java.util.List;

public class PatientSignOutPramsDto {
    private List<DepartmentTreatmentDto> departmentTreatmentList;

    private List<EmployeeDto> employeeList;

    private List<DictionaryDto> signOutReasonList;

    public List<DepartmentTreatmentDto> getDepartmentTreatmentList() {
        return departmentTreatmentList;
    }

    public void setDepartmentTreatmentList(List<DepartmentTreatmentDto> departmentTreatmentList) {
        this.departmentTreatmentList = departmentTreatmentList;
    }

    public List<EmployeeDto> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDto> employeeList) {
        this.employeeList = employeeList;
    }

    public List<DictionaryDto> getSignOutReasonList() {
        return signOutReasonList;
    }

    public void setSignOutReasonList(List<DictionaryDto> signOutReasonList) {
        this.signOutReasonList = signOutReasonList;
    }
}
