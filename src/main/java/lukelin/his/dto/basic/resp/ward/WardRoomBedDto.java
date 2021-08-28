package lukelin.his.dto.basic.resp.ward;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;
import lukelin.his.dto.signin.response.PatientSignInRespDto;

import java.util.UUID;

public class WardRoomBedDto extends BaseCodeDto {
    private String comment;

    private boolean occupied;

    private PatientSignInRespDto currentSignIn;

    private UUID treatmentId;

    public UUID getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(UUID treatmentId) {
        this.treatmentId = treatmentId;
    }

    public PatientSignInRespDto getCurrentSignIn() {
        return currentSignIn;
    }

    public void setCurrentSignIn(PatientSignInRespDto currentSignIn) {
        this.currentSignIn = currentSignIn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
