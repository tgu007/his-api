package lukelin.his.dto.internal_account;

import java.util.UUID;

public class PatientFilterDto {
    private String signInNumber;

    private String patientInfo;

    private UUID patientSignInId;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getSignInNumber() {
        return signInNumber;
    }

    public void setSignInNumber(String signInNumber) {
        this.signInNumber = signInNumber;
    }

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }
}
