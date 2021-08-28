package lukelin.his.dto.prescription.request;

import java.util.List;
import java.util.UUID;

public class PrescriptionCloneReqDto {
    List<UUID> prescriptionIdList;

    List<UUID> toPatientIdList;

    public List<UUID> getPrescriptionIdList() {
        return prescriptionIdList;
    }

    public void setPrescriptionIdList(List<UUID> prescriptionIdList) {
        this.prescriptionIdList = prescriptionIdList;
    }

    public List<UUID> getToPatientIdList() {
        return toPatientIdList;
    }

    public void setToPatientIdList(List<UUID> toPatientIdList) {
        this.toPatientIdList = toPatientIdList;
    }
}
