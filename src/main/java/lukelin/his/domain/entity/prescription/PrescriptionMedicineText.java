package lukelin.his.domain.entity.prescription;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.dto.prescription.response.PrescriptionListRespDto;
import lukelin.his.dto.prescription.response.PrescriptionNursingCardRespDto;
import lukelin.his.dto.prescription.response.PrescriptionMedicineTextRespDto;

import javax.persistence.*;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "prescription.prescription_medicine_text")
public class PrescriptionMedicineText extends BaseEntity implements DtoConvertible<PrescriptionMedicineTextRespDto> {
    @Column(name = "description")
    private String medicineName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "use_method_id", nullable = false)
    private Dictionary useMethod;

    @Column(name = "drop_speed")
    private String dropSpeed;

    @ManyToOne
    @JoinColumn(name = "frequency_id", nullable = false)
    private Dictionary frequency;

    private String quantity;

    @Column(name = "quantity_first_day")
    private String firstDayQuantity;

    @Column(name = "serve_quantity")
    private String serveQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_prescription_medicine_id")
    private PrescriptionMedicine fromPrescriptionMedicine;

    @Column(name = "self_prepared")
    private Boolean selfPrepared;

    public Boolean getSelfPrepared() {
        return selfPrepared;
    }

    public void setSelfPrepared(Boolean selfPrepared) {
        this.selfPrepared = selfPrepared;
    }

    public PrescriptionMedicine getFromPrescriptionMedicine() {
        return fromPrescriptionMedicine;
    }

    public void setFromPrescriptionMedicine(PrescriptionMedicine fromPrescriptionMedicine) {
        this.fromPrescriptionMedicine = fromPrescriptionMedicine;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Dictionary getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(Dictionary useMethod) {
        this.useMethod = useMethod;
    }

    public String getDropSpeed() {
        return dropSpeed;
    }

    public void setDropSpeed(String dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public Dictionary getFrequency() {
        return frequency;
    }

    public void setFrequency(Dictionary frequency) {
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


    public PrescriptionListRespDto copyValueToListDto(PrescriptionListRespDto prescriptionListRespDto) {
        BeanUtils.copyPropertiesIgnoreNull(this, prescriptionListRespDto);
        prescriptionListRespDto.setQuantityInfo(this.getQuantity());
        prescriptionListRespDto.setServeInfo(this.getServeQuantity());
        prescriptionListRespDto.setFirstDayQuantityInfo(this.getFirstDayQuantity());
        prescriptionListRespDto.setDropSpeed(this.getDropSpeed());
        prescriptionListRespDto.setUseMethod(this.getUseMethod().getName());
        prescriptionListRespDto.setDescription(this.getMedicineName());
        prescriptionListRespDto.setPrescriptionDetailId(this.getUuid());
        prescriptionListRespDto.setFrequency(this.getFrequency().getCode());
        prescriptionListRespDto.setSelfPrepared(this.getSelfPrepared());
        //prescriptionListRespDto.setAllowEdit(true);
        return prescriptionListRespDto;
    }

    public PrescriptionMedicineTextRespDto toDto() {
        PrescriptionMedicineTextRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setUseMethod(this.getUseMethod().toDto());
        respDto.setFrequency(this.getFrequency().toDto());
        respDto.setPrescription(this.getPrescription().toDto());
        return respDto;
    }

    public PrescriptionMedicineText toCloneEntity() {
        PrescriptionMedicineText cloneEntity = new PrescriptionMedicineText();
        BeanUtils.copyPropertiesIgnoreNull(this, cloneEntity);
        cloneEntity.setUuid(null);
        cloneEntity.setPrescription(null);
        //cloneEntity.setUseMethod(this.getUseMethod());
        //cloneEntity.setFrequency(this.getFrequency());

        return cloneEntity;
    }

    public PrescriptionNursingCardRespDto toMedicineCardDto() {
        PrescriptionNursingCardRespDto dto = new PrescriptionNursingCardRespDto();
        dto.setPrescriptionId(this.getPrescription().getUuid());
        dto.setDropSpeed(this.getDropSpeed());
        dto.setFrequency(this.getFrequency().getCode());
        dto.setEntityName(this.getMedicineName());
        dto.setServeInfo(this.getServeQuantity());
        dto.setUseMethod(this.getUseMethod().getName());
        dto.setQuantityInfo(this.getQuantity());
        Prescription prescription = this.getPrescription();
        if (prescription.getPrescriptionGroup() != null)
            dto.setGroupId(prescription.getPrescriptionGroup().getUuid());
        dto.setReference(this.getPrescription().getReference());
        dto.setSelfMedicine(this.getSelfPrepared());
        dto.setFirstDayQuantityInfo(prescription.getPrescriptionMedicineText().getFirstDayQuantity());
        return dto;
    }


    public Integer firstDayIntQuantity() {
        return Integer.valueOf(this.getFirstDayQuantity());
    }

    public boolean executeOnPrescriptionStartDate() {
        if (this.getFirstDayQuantity() == null)
            return false;
        else
            return this.firstDayIntQuantity() > 0;
    }
}
