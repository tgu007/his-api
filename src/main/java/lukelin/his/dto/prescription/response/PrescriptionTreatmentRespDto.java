package lukelin.his.dto.prescription.response;

import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.dto.basic.resp.entity.MedicineSnapshotRespDto;
import lukelin.his.dto.basic.resp.entity.TreatmentRespDto;
import lukelin.his.dto.basic.resp.entity.TreatmentSnapshotRespDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PrescriptionTreatmentRespDto {
    private UUID uuid;

    private TreatmentRespDto treatment;

    private PrescriptionChargeableRespDto prescriptionChargeable;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public TreatmentRespDto getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentRespDto treatment) {
        this.treatment = treatment;
    }

    public PrescriptionChargeableRespDto getPrescriptionChargeable() {
        return prescriptionChargeable;
    }

    public void setPrescriptionChargeable(PrescriptionChargeableRespDto prescriptionChargeable) {
        this.prescriptionChargeable = prescriptionChargeable;
    }
}
