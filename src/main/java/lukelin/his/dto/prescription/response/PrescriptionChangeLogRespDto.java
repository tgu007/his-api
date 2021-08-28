package lukelin.his.dto.prescription.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;

import java.util.Date;
import java.util.UUID;

public class PrescriptionChangeLogRespDto {
    private UUID uuid;

    private PrescriptionChangeAction action;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date whenCreated;

    private String whoCreated;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PrescriptionChangeAction getAction() {
        return action;
    }

    public void setAction(PrescriptionChangeAction action) {
        this.action = action;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }
}
