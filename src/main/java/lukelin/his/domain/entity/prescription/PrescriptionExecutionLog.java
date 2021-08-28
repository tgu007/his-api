package lukelin.his.domain.entity.prescription;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "prescription.prescription_execution_log")
public class PrescriptionExecutionLog extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "execute_department_id", nullable = false)
    private DepartmentTreatment department;

    @ManyToOne
    @JoinColumn(name = "execute_by", nullable = false)
    private Employee executeBy;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "ward_name", nullable = false)
    private String WardName;

    @Column(name = "bed_name", nullable = false)
    private String bedName;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

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

    public Employee getExecuteBy() {
        return executeBy;
    }

    public void setExecuteBy(Employee executeBy) {
        this.executeBy = executeBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
