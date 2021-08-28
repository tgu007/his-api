package lukelin.his.dto.signin.request;

import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignInBed;

import java.util.Date;
import java.util.UUID;

public class PatientSignInAssignBedDto {
    private UUID patientSignInId;

    private UUID bedId;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public UUID getBedId() {
        return bedId;
    }

    public void setBedId(UUID bedId) {
        this.bedId = bedId;
    }

    public PatientSignInBed toEntity() {
        PatientSignInBed newPatientSignInBed = new PatientSignInBed();
        newPatientSignInBed.setPatientSignIn(new PatientSignIn(this.getPatientSignInId()));
        newPatientSignInBed.setWardRoomBed(new WardRoomBed(this.getBedId()));
        newPatientSignInBed.setStartDate(new Date());
        return newPatientSignInBed;
    }
}
