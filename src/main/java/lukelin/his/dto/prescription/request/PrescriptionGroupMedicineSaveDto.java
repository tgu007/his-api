package lukelin.his.dto.prescription.request;

import java.math.BigDecimal;
import java.util.UUID;

public class PrescriptionGroupMedicineSaveDto {
    private UUID medicineId;

    private BigDecimal serveQuantity;

    private BigDecimal issueQuantity;

    public BigDecimal getIssueQuantity() {
        return issueQuantity;
    }

    public void setIssueQuantity(BigDecimal issueQuantity) {
        this.issueQuantity = issueQuantity;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public BigDecimal getServeQuantity() {
        return serveQuantity;
    }

    public void setServeQuantity(BigDecimal serveQuantity) {
        this.serveQuantity = serveQuantity;
    }
}
