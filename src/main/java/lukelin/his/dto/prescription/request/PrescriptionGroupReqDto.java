package lukelin.his.dto.prescription.request;

import java.util.List;
import java.util.UUID;

public class PrescriptionGroupReqDto {
    private List<UUID> prescriptionIdList;

    private boolean group;

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public List<UUID> getPrescriptionIdList() {
        return prescriptionIdList;
    }

    public void setPrescriptionIdList(List<UUID> prescriptionIdList) {
        this.prescriptionIdList = prescriptionIdList;
    }
}
