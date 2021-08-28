package lukelin.his.dto.prescription.request;

import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.prescription.PreDefinedPrescriptionMedicine;
import lukelin.his.domain.entity.prescription.PreDefinedPrescriptionTreatment;

import java.math.BigDecimal;
import java.util.UUID;

public class PreDefinedPrescriptionEntitySaveDto {

    private UUID uuid;

    private UUID entityId;

    private Integer frequencyId;

    private BigDecimal quantity;

    private BigDecimal firstDayQuantity;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFirstDayQuantity() {
        return firstDayQuantity;
    }

    public void setFirstDayQuantity(BigDecimal firstDayQuantity) {
        this.firstDayQuantity = firstDayQuantity;
    }

    public PreDefinedPrescriptionTreatment toTreatmentEntity() {
        PreDefinedPrescriptionTreatment treatmentLine = null;
//        if (this.getUuid() != null)
//            treatmentLine = Ebean.find(PreDefinedPrescriptionTreatment.class, this.getUuid());
//        else
        treatmentLine = new PreDefinedPrescriptionTreatment();
        treatmentLine.setFirstDayQuantity(this.getFirstDayQuantity());
        treatmentLine.setQuantity(this.getQuantity());
        treatmentLine.setFrequency(Ebean.find(Dictionary.class, this.getFrequencyId()));
        treatmentLine.setTreatment(Ebean.find(Treatment.class, this.getEntityId()));
        return treatmentLine;
    }

    public PreDefinedPrescriptionMedicine toMedicineEntity() {
        //Todo hardcode for now
        Dictionary frequency = Ebean.find(Dictionary.class).where().eq("group.code", "用药频次").eq("code", "once").findOne();
        PreDefinedPrescriptionMedicine medicineLine =  new PreDefinedPrescriptionMedicine();
        medicineLine.setFirstDayQuantity(BigDecimal.ONE);
        medicineLine.setQuantity(this.getQuantity());
        medicineLine.setFrequency(frequency);
        medicineLine.setMedicine(Ebean.find(Medicine.class, this.getEntityId()));
        return medicineLine;
    }
}
