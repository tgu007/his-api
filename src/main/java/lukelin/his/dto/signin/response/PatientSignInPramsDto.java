package lukelin.his.dto.signin.response;

import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;
import lukelin.his.dto.basic.resp.setup.FromHospitalRespDto;

import java.util.List;

public class PatientSignInPramsDto {
    private List<DictionaryDto> signInMethodList;

    private List<DictionaryDto> careLevelList;

    private List<DictionaryDto> patientConditionList;

    private List<DictionaryDto> insuranceTypeList;

    private List<DepartmentTreatmentDto> departmentTreatmentList;

    private List<EmployeeDto> employeeList;

    public List<EmployeeDto> getEmployeeList() {
        return employeeList;
    }

    public  List<DrgGroupRespDto> drgGroupList;

    public List<DrgGroupRespDto> getDrgGroupList() {
        return drgGroupList;
    }

    private List<FromHospitalRespDto> fromHospitalList;

    public List<FromHospitalRespDto> getFromHospitalList() {
        return fromHospitalList;
    }

    public void setFromHospitalList(List<FromHospitalRespDto> fromHospitalList) {
        this.fromHospitalList = fromHospitalList;
    }

    public void setDrgGroupList(List<DrgGroupRespDto> drgGroupList) {
        this.drgGroupList = drgGroupList;
    }

    public void setEmployeeList(List<EmployeeDto> employeeList) {
        this.employeeList = employeeList;
    }

    public List<DepartmentTreatmentDto> getDepartmentTreatmentList() {
        return departmentTreatmentList;
    }

    public void setDepartmentTreatmentList(List<DepartmentTreatmentDto> departmentTreatmentList) {
        this.departmentTreatmentList = departmentTreatmentList;
    }

    public List<DictionaryDto> getSignInMethodList() {
        return signInMethodList;
    }

    public void setSignInMethodList(List<DictionaryDto> signInMethodList) {
        this.signInMethodList = signInMethodList;
    }

    public List<DictionaryDto> getCareLevelList() {
        return careLevelList;
    }

    public void setCareLevelList(List<DictionaryDto> careLevelList) {
        this.careLevelList = careLevelList;
    }

    public List<DictionaryDto> getPatientConditionList() {
        return patientConditionList;
    }

    public void setPatientConditionList(List<DictionaryDto> patientConditionList) {
        this.patientConditionList = patientConditionList;
    }

    public List<DictionaryDto> getInsuranceTypeList() {
        return insuranceTypeList;
    }

    public void setInsuranceTypeList(List<DictionaryDto> insuranceTypeList) {
        this.insuranceTypeList = insuranceTypeList;
    }
}
