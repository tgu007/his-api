package lukelin.his.domain.entity.patient_sign_in;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "patient_sign_in.department_change")
public class PatientSignInDepartmentChange extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne
    @JoinColumn(name = "from_department_id", nullable = false)
    private DepartmentTreatment fromDepartment;

    @ManyToOne
    @JoinColumn(name = "to_department_id", nullable = false)
    private DepartmentTreatment toDepartment;

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public DepartmentTreatment getFromDepartment() {
        return fromDepartment;
    }

    public void setFromDepartment(DepartmentTreatment fromDepartment) {
        this.fromDepartment = fromDepartment;
    }

    public DepartmentTreatment getToDepartment() {
        return toDepartment;
    }

    public void setToDepartment(DepartmentTreatment toDepartment) {
        this.toDepartment = toDepartment;
    }
}
