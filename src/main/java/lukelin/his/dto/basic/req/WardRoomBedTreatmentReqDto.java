package lukelin.his.dto.basic.req;

import java.util.UUID;

public class WardRoomBedTreatmentReqDto {
    private UUID bedId;

    private UUID treatmentId;

    public UUID getBedId() {
        return bedId;
    }

    public void setBedId(UUID bedId) {
        this.bedId = bedId;
    }

    public UUID getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(UUID treatmentId) {
        this.treatmentId = treatmentId;
    }
}
