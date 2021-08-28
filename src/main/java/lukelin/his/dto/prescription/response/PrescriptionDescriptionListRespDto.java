package lukelin.his.dto.prescription.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PrescriptionDescriptionListRespDto {
    private String description;

    @JsonFormat(pattern = "yy/MM/dd HH:mm", timezone = "GMT+8")
    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
