package lukelin.his.dto.account.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.account.AutoFee;
import lukelin.his.domain.entity.account.BaseFee;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.Department;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.entity.*;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Fee.FeeStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FeeSaveDto {
    private boolean autoFee;

    private Integer frequencyId;

    private BigDecimal quantity;

    private UUID patientSignInId;

    private UUID feeEntityId;

    private EntityType entityType;

    private UUID prescriptionId;

    private UUID warehouseId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date feeDate;

    private UUID supervisorId;

    private Boolean selfPay;

    private UUID departmentId;

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public UUID getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(UUID supervisorId) {
        this.supervisorId = supervisorId;
    }

    public boolean isAutoFee() {
        return autoFee;
    }

    public void setAutoFee(boolean autoFee) {
        this.autoFee = autoFee;
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

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public UUID getFeeEntityId() {
        return feeEntityId;
    }

    public void setFeeEntityId(UUID feeEntityId) {
        this.feeEntityId = feeEntityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public Fee toFeeEntity() {
        Fee newFee = new Fee();
        newFee.setFeeRecordMethod(FeeRecordMethod.manual);
        this.assignBaseFeeProperty(newFee);


        newFee.setFeeStatus(FeeStatus.confirmed);
        newFee.setManualRecorded(true);
        newFee.setDepartmentName(newFee.getDepartment().getDepartment().getName());
        if (this.getSelfPay() != null)
            newFee.setSelfPay(this.getSelfPay());

        if (this.getEntityType() == EntityType.medicine) {
            Medicine medicine = Ebean.find(Medicine.class, this.getFeeEntityId());
            //MedicineSnapshot latestSnapShot = medicine.findLatestSnapshot();
            medicine.setMedicineFeeValue(newFee);
        } else if (this.getEntityType() == EntityType.treatment) {
            Treatment treatment = Ebean.find(Treatment.class, this.getFeeEntityId());
            // TreatmentSnapshot latestSnapShot = treatment.findLatestSnapshot();
            treatment.setFeeValue(newFee);
        } else if (this.getEntityType() == EntityType.item) {
            Item item = Ebean.find(Item.class, this.getFeeEntityId());
            //ItemSnapshot latestSnapShot = item.findLatestSnapshot();
            item.setFeeValue(newFee);
        }


        return newFee;
    }

    public AutoFee toAutoFeeEntity() {
        AutoFee newAutoFee = new AutoFee();

        newAutoFee.setEnabled(true);
        Dictionary dicFrequency = new Dictionary(this.getFrequencyId());
        newAutoFee.setFrequency(dicFrequency);

        this.assignBaseFeeProperty(newAutoFee);
        if (this.getEntityType() == EntityType.treatment) {
            Treatment treatment = Ebean.find(Treatment.class, this.getFeeEntityId());
            newAutoFee.setTreatment(treatment);
            newAutoFee.setDescription(treatment.getName());
        } else if (this.getEntityType() == EntityType.item) {
            Item item = Ebean.find(Item.class, this.getFeeEntityId());
            newAutoFee.setItem(item);
            newAutoFee.setDescription(item.getName());
        }
        return newAutoFee;
    }

    private void assignBaseFeeProperty(BaseFee baseFee) {
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, this.getPatientSignInId());

        if (feeDate != null)
            baseFee.setFeeDate(feeDate);
        else
            baseFee.setFeeDate(new Date());
        baseFee.setPatientSignIn(patientSignIn);
        //Todo, 这里需改为创建用户所属的科室ID
        if (departmentId == null)
            baseFee.setDepartment(patientSignIn.getDepartmentTreatment());
        else
            baseFee.setDepartment(Ebean.find(DepartmentTreatment.class, this.getDepartmentId()));

        baseFee.setQuantity(this.getQuantity()); //最小单位
        baseFee.setFeeEntityId(this.getFeeEntityId());
        baseFee.setEntityType(this.getEntityType());

        if (this.getPrescriptionId() != null) {
            Prescription prescription = Ebean.find(Prescription.class, this.getPrescriptionId());
            baseFee.setPrescription(prescription);
        }
    }
}
