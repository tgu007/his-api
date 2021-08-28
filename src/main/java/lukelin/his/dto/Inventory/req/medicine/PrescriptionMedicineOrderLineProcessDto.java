package lukelin.his.dto.Inventory.req.medicine;

import java.util.UUID;

public class PrescriptionMedicineOrderLineProcessDto {
    private UUID orderLineId;

    private UUID medicineId;

    private Boolean approve;

    public UUID getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(UUID orderLineId) {
        this.orderLineId = orderLineId;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }
}
