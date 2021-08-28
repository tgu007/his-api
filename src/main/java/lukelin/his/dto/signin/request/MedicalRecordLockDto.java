package lukelin.his.dto.signin.request;

import java.util.UUID;

public class MedicalRecordLockDto {
    private UUID medicalRecordId;

    private String lockedBy;

    public UUID getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(UUID medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }
}
