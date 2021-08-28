package lukelin.his.domain.entity.yb;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.dto.yb.resp.CenterTreatmentRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb.doctor_agreement")
public class DoctorAgreement extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    @Column(name = "agreement_number")
    private String agreementNumber;

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
}
