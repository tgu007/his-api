package lukelin.his.dto.prescription.request;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.PrescriptionSaveDtoInterface;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionMedicine;
import lukelin.his.domain.entity.prescription.PrescriptionMedicineText;

import java.math.BigDecimal;
import java.util.UUID;

public class PrescriptionMedicineTextSaveDto implements PrescriptionSaveDtoInterface {
    private UUID uuid;

    private String medicineName;

    private Integer useMethodId;

    private String dropSpeed;

    private PrescriptionSaveDto prescriptionSaveDto;

    private Integer frequencyId;

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

    public String getServeQuantity() {
        return serveQuantity;
    }

    public void setServeQuantity(String serveQuantity) {
        this.serveQuantity = serveQuantity;
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

    public Integer getUseMethodId() {
        return useMethodId;
    }

    public void setUseMethodId(Integer useMethodId) {
        this.useMethodId = useMethodId;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public PrescriptionSaveDto getPrescriptionSaveDto() {
        return prescriptionSaveDto;
    }

    public void setPrescriptionSaveDto(PrescriptionSaveDto prescriptionSaveDto) {
        this.prescriptionSaveDto = prescriptionSaveDto;
    }

    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
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

    public String getServerQuantity() {
        return serveQuantity;
    }

    public void setServerQuantity(String serverQuantity) {
        this.serveQuantity = serverQuantity;
    }

    public Prescription toEntity() {
        Prescription prescription = this.prescriptionSaveDto.toEntity();
        PrescriptionMedicineText prescriptionMedicineText = new PrescriptionMedicineText();
        prescription.setPrescriptionMedicineText(prescriptionMedicineText);

        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionMedicineText);
        prescriptionMedicineText.setUseMethod(new Dictionary(this.useMethodId));
        prescriptionMedicineText.setFrequency(new Dictionary(this.getFrequencyId()));
        prescription.setDescription(prescriptionMedicineText.getMedicineName());
        return prescription;
    }
}
