package lukelin.his.dto.prescription.request;

import java.math.BigDecimal;
import java.util.UUID;

public class TreatmentPrescriptionQuantityAdjust {
    private UUID prescriptionTreatmentId;

    private BigDecimal adjustQuantity;

    public UUID getPrescriptionTreatmentId() {
        return prescriptionTreatmentId;
    }

    public void setPrescriptionTreatmentId(UUID prescriptionTreatmentId) {
        this.prescriptionTreatmentId = prescriptionTreatmentId;
    }

    public BigDecimal getAdjustQuantity() {
        return adjustQuantity;
    }

    public void setAdjustQuantity(BigDecimal adjustQuantity) {
        this.adjustQuantity = adjustQuantity;
    }
}
