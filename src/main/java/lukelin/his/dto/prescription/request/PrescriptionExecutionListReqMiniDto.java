package lukelin.his.dto.prescription.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PrescriptionExecutionListReqMiniDto extends PrescriptionExecutionListReqDto {

    public PrescriptionExecutionListReqMiniDto() {
        this.setFeeRecordMethod(FeeRecordMethod.miniProgram);
    }

    private UUID patientSignInId;

    private UUID employeeId;

    private UUID supervisorId;

    public UUID getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(UUID supervisorId) {
        this.supervisorId = supervisorId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }
}
