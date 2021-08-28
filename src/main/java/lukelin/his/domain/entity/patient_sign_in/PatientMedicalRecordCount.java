package lukelin.his.domain.entity.patient_sign_in;

import io.ebean.annotation.View;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

@javax.persistence.Entity
@View(name = "patient_sign_in.patient_medicial_record_summary")
public class PatientMedicalRecordCount {
    @Column(name = "patient_sign_in_id")
    private UUID patientSignInId;

    @Column(name = "record_type_id")
    private UUID medicalRecordTypeId;

    @Column(name = "record_count")
    private Integer recordCount;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public UUID getMedicalRecordTypeId() {
        return medicalRecordTypeId;
    }

    public void setMedicalRecordTypeId(UUID medicalRecordTypeId) {
        this.medicalRecordTypeId = medicalRecordTypeId;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }
}
