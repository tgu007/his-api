package lukelin.his.dto.prescription.request;

import java.util.*;
import java.util.UUID;

public class PrescriptionExecutionReqDto {
    private UUID uuid;

    private Integer executionQuantity;

    private List<UUID> childTreatmentIdList;

    public List<UUID> getChildTreatmentIdList() {
        return childTreatmentIdList;
    }

    public void setChildTreatmentIdList(List<UUID> childTreatmentIdList) {
        this.childTreatmentIdList = childTreatmentIdList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getExecutionQuantity() {
        return executionQuantity;
    }

    public void setExecutionQuantity(Integer executionQuantity) {
        this.executionQuantity = executionQuantity;
    }
}
