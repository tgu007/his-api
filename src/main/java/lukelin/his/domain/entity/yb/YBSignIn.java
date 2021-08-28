package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "yb.patient_sign_in")
public class YBSignIn extends BaseEntity {
    @Column(name = "server_id")
    private String id;

    @Column(name = "server_code")
    private String code;

    @OneToOne()
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }
}
