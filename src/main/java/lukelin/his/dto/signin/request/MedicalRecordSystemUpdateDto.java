package lukelin.his.dto.signin.request;

import java.util.UUID;

public class MedicalRecordSystemUpdateDto {
    private UUID doctorId;

    private UUID patientSignInId;

    private String currentPageContent;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getCurrentPageContent() {
        return currentPageContent;
    }

    public void setCurrentPageContent(String currentPageContent) {
        this.currentPageContent = currentPageContent;
    }
}
