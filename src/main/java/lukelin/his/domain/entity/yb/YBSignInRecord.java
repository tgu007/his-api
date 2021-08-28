package lukelin.his.domain.entity.yb;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.signin.response.PatientSignInRespDto;
import lukelin.his.dto.yb.SignInRecordRespDto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "yb.patient_sign_in_record")
public class YBSignInRecord extends BaseEntity implements DtoConvertible<SignInRecordRespDto> {
    @OneToOne()
    @JoinColumn(name = "apply_by_doctor_id", nullable = false)
    private Employee appliedByDoctor;

    @OneToOne()
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "left_days")
    private Integer leftDays;

    public Employee getAppliedByDoctor() {
        return appliedByDoctor;
    }

    public void setAppliedByDoctor(Employee appliedByDoctor) {
        this.appliedByDoctor = appliedByDoctor;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Integer getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(Integer leftDays) {
        this.leftDays = leftDays;
    }

    @Override
    public SignInRecordRespDto toDto() {
        SignInRecordRespDto respDto = DtoUtils.convertRawDto(this);
        respDto.setSignInId(this.patientSignIn.getUuid());
        respDto.setAppliedByDoctorId(this.getAppliedByDoctor().getUuid());
        return respDto;
    }
}
