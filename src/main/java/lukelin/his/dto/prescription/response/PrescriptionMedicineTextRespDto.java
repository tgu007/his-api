package lukelin.his.dto.prescription.response;

import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.dto.basic.resp.entity.MedicineSnapshotRespDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.math.BigDecimal;
import java.util.UUID;

public class PrescriptionMedicineTextRespDto {
    private UUID uuid;

    private String medicineName;

    private DictionaryDto useMethod;

    private String dropSpeed;

    private PrescriptionRespDto prescription;

    private DictionaryDto frequency;

    private String quantity;

    private String firstDayQuantity;

    private String serveQuantity;

    private Boolean selfPrepared;

    public Boolean getSelfPrepared() {
        return selfPrepared;
    }

    public void setSelfPrepared(Boolean selfPrepared) {
        this.selfPrepared = selfPrepared;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public DictionaryDto getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(DictionaryDto useMethod) {
        this.useMethod = useMethod;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public PrescriptionRespDto getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionRespDto prescription) {
        this.prescription = prescription;
    }

    public DictionaryDto getFrequency() {
        return frequency;
    }

    public void setFrequency(DictionaryDto frequency) {
        this.frequency = frequency;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFirstDayQuantity() {
        return firstDayQuantity;
    }

    public void setFirstDayQuantity(String firstDayQuantity) {
        this.firstDayQuantity = firstDayQuantity;
    }

    public String getServeQuantity() {
        return serveQuantity;
    }

    public void setServeQuantity(String serveQuantity) {
        this.serveQuantity = serveQuantity;
    }
}
