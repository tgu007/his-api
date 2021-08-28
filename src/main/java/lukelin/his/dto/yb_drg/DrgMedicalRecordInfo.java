package lukelin.his.dto.yb_drg;

import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.yb.ICD9RespDto;

import java.util.*;

public class DrgMedicalRecordInfo {
    private String mainInfo;

    private String currentCondition;

    private String operationHistory;

    private String bloodTakenHistory;

    private String signInCondition;

    private String treatmentProcess;

    private String singOutCondition;

    private String signOutPrescription;

    private List<DiagnoseDto> diagnoseList;

    private List<DrgRecordOperationRespDto> operationList;

    public List<DrgRecordOperationRespDto> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<DrgRecordOperationRespDto> operationList) {
        this.operationList = operationList;
    }

    public List<DiagnoseDto> getDiagnoseList() {
        return diagnoseList;
    }

    public void setDiagnoseList(List<DiagnoseDto> diagnoseList) {
        this.diagnoseList = diagnoseList;
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getOperationHistory() {
        return operationHistory;
    }

    public void setOperationHistory(String operationHistory) {
        this.operationHistory = operationHistory;
    }

    public String getBloodTakenHistory() {
        return bloodTakenHistory;
    }

    public void setBloodTakenHistory(String bloodTakenHistory) {
        this.bloodTakenHistory = bloodTakenHistory;
    }

    public String getSignInCondition() {
        return signInCondition;
    }

    public void setSignInCondition(String signInCondition) {
        this.signInCondition = signInCondition;
    }

    public String getTreatmentProcess() {
        return treatmentProcess;
    }

    public void setTreatmentProcess(String treatmentProcess) {
        this.treatmentProcess = treatmentProcess;
    }

    public String getSingOutCondition() {
        return singOutCondition;
    }

    public void setSingOutCondition(String singOutCondition) {
        this.singOutCondition = singOutCondition;
    }

    public String getSignOutPrescription() {
        return signOutPrescription;
    }

    public void setSignOutPrescription(String signOutPrescription) {
        this.signOutPrescription = signOutPrescription;
    }
}
