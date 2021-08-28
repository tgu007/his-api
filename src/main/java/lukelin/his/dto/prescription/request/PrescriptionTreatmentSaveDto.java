package lukelin.his.dto.prescription.request;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.PrescriptionSaveDtoInterface;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;
import lukelin.his.domain.entity.prescription.PrescriptionTreatment;
import lukelin.his.dto.prescription.request.PrescriptionChargeableSaveDto;

import java.math.BigDecimal;
import java.util.UUID;

public class PrescriptionTreatmentSaveDto implements PrescriptionSaveDtoInterface {
    private UUID uuid;

    private UUID treatmentId;

    private PrescriptionChargeableSaveDto prescriptionChargeableSaveDto;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(UUID treatmentId) {
        this.treatmentId = treatmentId;
    }

    public PrescriptionChargeableSaveDto getPrescriptionChargeableSaveDto() {
        return prescriptionChargeableSaveDto;
    }

    public void setPrescriptionChargeableSaveDto(PrescriptionChargeableSaveDto prescriptionChargeableSaveDto) {
        this.prescriptionChargeableSaveDto = prescriptionChargeableSaveDto;
    }

    public Prescription toEntity() {
        Prescription prescription = this.prescriptionChargeableSaveDto.toEntity();
        PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment();
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionTreatment);
        prescription.getPrescriptionChargeable().setPrescriptionTreatment(prescriptionTreatment);
        Treatment treatment = new Treatment(this.treatmentId);
        prescriptionTreatment.setTreatment(treatment);
        prescriptionTreatment.setTreatmentSnapshot(treatment.findLatestSnapshot());
        prescription.setDescription(prescriptionTreatment.getTreatmentSnapshot().getName());
        return prescription;
    }
}
