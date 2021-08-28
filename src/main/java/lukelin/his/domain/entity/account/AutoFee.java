package lukelin.his.domain.entity.account;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.*;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.account.response.AutoFeeListDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "account.auto_fee")
public class AutoFee extends BaseFee {
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frequency_id", nullable = false)
    private Dictionary frequency;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Dictionary getFrequency() {
        return frequency;
    }

    public void setFrequency(Dictionary frequency) {
        this.frequency = frequency;
    }

    public AutoFeeListDto toListDto() {
        AutoFeeListDto dto = new AutoFeeListDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());
        if (this.getFrequency() != null)
            dto.setFrequencyName(this.getFrequency().getName());
        else if (this.getPrescription() != null && this.getPrescription().getPrescriptionType() != PrescriptionType.Text)
            dto.setFrequencyName(this.getPrescription().getFrequency().getName());


        BigDecimal listPrice = BigDecimal.ZERO;
        String uomName = "";
        if (this.getEntityType() == EntityType.treatment) {
            listPrice = this.getTreatment().getListPrice();
            uomName = this.getTreatment().getMinUom().getName();
        } else if (this.getEntityType() == EntityType.item) {
            listPrice = this.getItem().getListPrice();
            uomName = this.getItem().getMinUom().getName();
            dto.setDepartmentModel(this.getItem().getDepartmentModel());
            dto.setManufacturer(this.getItem().getManufacturerItem().getName());
        }
        dto.setQuantityInfo(this.getQuantity() + uomName);
        dto.setUnitAmountInfo(listPrice + "/" + uomName);
        dto.setUnitAmount(listPrice);
        if (this.getPrescription() != null)
            dto.setPrescriptionDescription(this.getPrescription().getDescription());
        return dto;
    }

    public Fee generateFee(Date feeDate) {
        Fee newFee = new Fee();
        newFee.setFeeRecordMethod(FeeRecordMethod.auto);
        newFee.setFeeDate(feeDate);
        newFee.setPatientSignIn(this.getPatientSignIn());
        //Todo, 这里需改为创建用户所属的科室ID
        newFee.setDepartment(this.getPatientSignIn().getDepartmentTreatment());

        newFee.setQuantity(this.getQuantity()); //最小单位
        newFee.setFeeEntityId(this.getFeeEntityId());
        newFee.setEntityType(this.getEntityType());
        newFee.setPrescription(this.getPrescription());
        newFee.setFeeStatus(FeeStatus.confirmed);
        newFee.setDepartmentName(newFee.getDepartment().getDepartment().getName());

        if (this.getEntityType() == EntityType.treatment) {
            //TreatmentSnapshot latestSnapShot = this.getTreatment().findLatestSnapshot();
            this.getTreatment().setFeeValue(newFee);
        } else if (this.getEntityType() == EntityType.item) {
            //ItemSnapshot latestSnapShot = this.getItem().findLatestSnapshot();
            this.getItem().setFeeValue(newFee);
        }
        newFee.setAutoFee(this);
        return newFee;
    }
}
