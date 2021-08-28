package lukelin.his.dto.prescription.response;

import lukelin.his.dto.basic.resp.entity.MedicineRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineSnapshotRespDto;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.UUID;

public class PrescriptionMedicineRespDto {
    private UUID uuid;

    private MedicineRespDto medicine;

    private String dropSpeed;

    private DictionaryDto useMethod;

    private PrescriptionChargeableRespDto prescriptionChargeable;

    private BigDecimal issueQuantity;

    private BigDecimal serveQuantity;

    private BigDecimal fixedQuantity;

    private DiagnoseDto diagnose;

    private String slipDescription;

    public String getSlipDescription() {
        return slipDescription;
    }

    public void setSlipDescription(String slipDescription) {
        this.slipDescription = slipDescription;
    }

    public DiagnoseDto getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(DiagnoseDto diagnose) {
        this.diagnose = diagnose;
    }

    public BigDecimal getFixedQuantity() {
        return fixedQuantity;
    }

    public void setFixedQuantity(BigDecimal fixedQuantity) {
        this.fixedQuantity = fixedQuantity;
    }

    public BigDecimal getServeQuantity() {
        return serveQuantity;
    }

    public void setServeQuantity(BigDecimal serveQuantity) {
        this.serveQuantity = serveQuantity;
    }

    public BigDecimal getIssueQuantity() {
        return issueQuantity;
    }

    public void setIssueQuantity(BigDecimal issueQuantity) {
        this.issueQuantity = issueQuantity;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public MedicineRespDto getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineRespDto medicine) {
        this.medicine = medicine;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public DictionaryDto getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(DictionaryDto useMethod) {
        this.useMethod = useMethod;
    }

    public PrescriptionChargeableRespDto getPrescriptionChargeable() {
        return prescriptionChargeable;
    }

    public void setPrescriptionChargeable(PrescriptionChargeableRespDto prescriptionChargeable) {
        this.prescriptionChargeable = prescriptionChargeable;
    }
}
