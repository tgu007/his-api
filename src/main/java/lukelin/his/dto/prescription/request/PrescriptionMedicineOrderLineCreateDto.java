package lukelin.his.dto.prescription.request;

import java.util.UUID;

public class PrescriptionMedicineOrderLineCreateDto {
    private UUID prescriptionMedicineId;

    private Integer orderQuantity;

    public UUID getPrescriptionMedicineId() {
        return prescriptionMedicineId;
    }

    public void setPrescriptionMedicineId(UUID prescriptionMedicineId) {
        this.prescriptionMedicineId = prescriptionMedicineId;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
