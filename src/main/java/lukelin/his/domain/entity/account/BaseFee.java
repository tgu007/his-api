package lukelin.his.domain.entity.account;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.EntityType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseFee extends BaseEntity {
    @Column(nullable = false)
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentTreatment department;

    @Column(name = "fee_entity_id", nullable = false)
    private UUID feeEntityId;

    @Column(name = "fee_type", nullable = false)
    private EntityType entityType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "fee_date", nullable = false)
    private Date feeDate;

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public DepartmentTreatment getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentTreatment department) {
        this.department = department;
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
}
