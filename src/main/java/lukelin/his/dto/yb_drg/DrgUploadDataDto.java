package lukelin.his.dto.yb_drg;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.*;

public class DrgUploadDataDto {
    @JSONField(name = "Medical")
    private DrgUploadMedicalDto medical;

    @JSONField(name = "LeaveHospital")
    private DrgUploadLeaveHospitalDto leaveHospital;

    //private DrgUploadListCheckDto ListCheck;

    @JSONField(name = "ListOperation")
    private List<DrgUploadListOperationDto> listOperation;

    public DrgUploadMedicalDto getMedical() {
        return medical;
    }

    public void setMedical(DrgUploadMedicalDto medical) {
        this.medical = medical;
    }

    public DrgUploadLeaveHospitalDto getLeaveHospital() {
        return leaveHospital;
    }

    public void setLeaveHospital(DrgUploadLeaveHospitalDto leaveHospital) {
        this.leaveHospital = leaveHospital;
    }

    public List<DrgUploadListOperationDto> getListOperation() {
        return listOperation;
    }

    public void setListOperation(List<DrgUploadListOperationDto> listOperation) {
        this.listOperation = listOperation;
    }
}
