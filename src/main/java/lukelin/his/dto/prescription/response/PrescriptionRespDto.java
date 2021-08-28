package lukelin.his.dto.prescription.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;

import java.util.Date;
import java.util.UUID;

public class PrescriptionRespDto {
    private UUID uuid;

    private String reference;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    private DepartmentTreatmentDto department;

    public DepartmentTreatmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentTreatmentDto department) {
        this.department = department;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
