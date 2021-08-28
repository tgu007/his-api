package lukelin.his.dto.yb_drg;

import com.alibaba.fastjson.annotation.JSONField;

public class DrgUploadLeaveHospitalDto {
    @JSONField(name = "DischargeOutcome")
    private String dischargeOutcome;

    @JSONField(name = "HospitalizationSituation")
    private String hospitalizationSituation;

    @JSONField(name = "DtProcess")
    private String dtProcess;

    @JSONField(name = "LeaveHospitalStatus")
    private String leaveHospitalStatus;

    @JSONField(name = "LeaveDoctorAdvice")
    private String leaveDoctorAdvice;

    public String getDischargeOutcome() {
        return dischargeOutcome;
    }

    public void setDischargeOutcome(String dischargeOutcome) {
        this.dischargeOutcome = dischargeOutcome;
    }

    public String getHospitalizationSituation() {
        return hospitalizationSituation;
    }

    public void setHospitalizationSituation(String hospitalizationSituation) {
        this.hospitalizationSituation = hospitalizationSituation;
    }

    public String getDtProcess() {
        return dtProcess;
    }

    public void setDtProcess(String dtProcess) {
        this.dtProcess = dtProcess;
    }

    public String getLeaveHospitalStatus() {
        return leaveHospitalStatus;
    }

    public void setLeaveHospitalStatus(String leaveHospitalStatus) {
        this.leaveHospitalStatus = leaveHospitalStatus;
    }

    public String getLeaveDoctorAdvice() {
        return leaveDoctorAdvice;
    }

    public void setLeaveDoctorAdvice(String leaveDoctorAdvice) {
        this.leaveDoctorAdvice = leaveDoctorAdvice;
    }
}
