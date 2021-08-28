package lukelin.his.dto.prescription.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public class PrescriptionChangeLogReqDto {
    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date manualDate;

    private UUID manualUserId;

    public UUID getManualUserId() {
        return manualUserId;
    }

    public void setManualUserId(UUID manualUserId) {
        this.manualUserId = manualUserId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getManualDate() {
        return manualDate;
    }

    public void setManualDate(Date manualDate) {
        this.manualDate = manualDate;
    }
}
