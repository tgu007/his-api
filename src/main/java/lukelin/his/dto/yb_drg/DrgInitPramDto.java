package lukelin.his.dto.yb_drg;

import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import java.util.*;

public class DrgInitPramDto {
    private List<AllergyMedicineDto> allergyMedicineList;

    private List<EmployeeDto> doctorList;

    private List<BloodTypeDto> bloodTypeList;

    private List<BloodTypeRhDto> bloodTypeRhList;

    private List<DiagnoseDirectionDto> diagnoseDirectionList;

    private List<SignOutMethodDto> signOutMethodList;

    private List<SpecialDiseaseDto> specialDiseaseList;

    private List<DictionaryDto> marriageStatusList;

    private List<SignOutReasonDto> signOutReasonList;

    private List<OperationLevelDto> operationLevelList;

    private List<EmployeeDto> operatorList;

    public List<EmployeeDto> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<EmployeeDto> operatorList) {
        this.operatorList = operatorList;
    }

    public List<OperationLevelDto> getOperationLevelList() {
        return operationLevelList;
    }

    public void setOperationLevelList(List<OperationLevelDto> operationLevelList) {
        this.operationLevelList = operationLevelList;
    }

    public List<SignOutReasonDto> getSignOutReasonList() {
        return signOutReasonList;
    }

    public void setSignOutReasonList(List<SignOutReasonDto> signOutReasonList) {
        this.signOutReasonList = signOutReasonList;
    }

    public List<DictionaryDto> getMarriageStatusList() {
        return marriageStatusList;
    }

    public void setMarriageStatusList(List<DictionaryDto> marriageStatusList) {
        this.marriageStatusList = marriageStatusList;
    }

    public List<AllergyMedicineDto> getAllergyMedicineList() {
        return allergyMedicineList;
    }

    public void setAllergyMedicineList(List<AllergyMedicineDto> allergyMedicineList) {
        this.allergyMedicineList = allergyMedicineList;
    }

    public List<EmployeeDto> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<EmployeeDto> doctorList) {
        this.doctorList = doctorList;
    }

    public List<BloodTypeDto> getBloodTypeList() {
        return bloodTypeList;
    }

    public void setBloodTypeList(List<BloodTypeDto> bloodTypeList) {
        this.bloodTypeList = bloodTypeList;
    }

    public List<BloodTypeRhDto> getBloodTypeRhList() {
        return bloodTypeRhList;
    }

    public void setBloodTypeRhList(List<BloodTypeRhDto> bloodTypeRhList) {
        this.bloodTypeRhList = bloodTypeRhList;
    }

    public List<DiagnoseDirectionDto> getDiagnoseDirectionList() {
        return diagnoseDirectionList;
    }

    public void setDiagnoseDirectionList(List<DiagnoseDirectionDto> diagnoseDirectionList) {
        this.diagnoseDirectionList = diagnoseDirectionList;
    }

    public List<SignOutMethodDto> getSignOutMethodList() {
        return signOutMethodList;
    }

    public void setSignOutMethodList(List<SignOutMethodDto> signOutMethodList) {
        this.signOutMethodList = signOutMethodList;
    }

    public List<SpecialDiseaseDto> getSpecialDiseaseList() {
        return specialDiseaseList;
    }

    public void setSpecialDiseaseList(List<SpecialDiseaseDto> specialDiseaseList) {
        this.specialDiseaseList = specialDiseaseList;
    }
}
