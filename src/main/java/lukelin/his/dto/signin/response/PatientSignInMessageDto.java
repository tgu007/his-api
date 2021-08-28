package lukelin.his.dto.signin.response;

import lukelin.his.dto.basic.resp.ward.PatientSignInBedDto;

import java.util.UUID;

public class PatientSignInMessageDto {
    private UUID patientSignInId;

    private PatientSignInBedDto currentBedDto;

    private String name;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public PatientSignInBedDto getCurrentBedDto() {
        return currentBedDto;
    }

    public void setCurrentBedDto(PatientSignInBedDto currentBedDto) {
        this.currentBedDto = currentBedDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
