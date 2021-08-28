package lukelin.his.dto.prescription.request;

import java.util.List;
import java.util.UUID;

public class PrescriptionChangeStatusReqDto {
    private List<UUID> prescriptionIdList;

    public List<UUID> getPrescriptionIdList() {
        return prescriptionIdList;
    }

    public void setPrescriptionIdList(List<UUID> prescriptionIdList) {
        this.prescriptionIdList = prescriptionIdList;
    }

}
