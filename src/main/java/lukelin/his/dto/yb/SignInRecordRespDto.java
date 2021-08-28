package lukelin.his.dto.yb;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.YBSignInRecord;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

public class SignInRecordRespDto {
    private UUID signInId;

    private UUID appliedByDoctorId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Integer numberOfDays;

    private String referenceNumber;

    private Integer leftDays;

    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getSignInId() {
        return signInId;
    }

    public void setSignInId(UUID signInId) {
        this.signInId = signInId;
    }

    public UUID getAppliedByDoctorId() {
        return appliedByDoctorId;
    }

    public void setAppliedByDoctorId(UUID appliedByDoctorId) {
        this.appliedByDoctorId = appliedByDoctorId;
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

    public YBSignInRecord toEntity() {
        YBSignInRecord signInRecord;
        if (this.getUuid() != null)
            signInRecord = Ebean.find(YBSignInRecord.class, this.getUuid());
        else
            signInRecord = new YBSignInRecord();
        BeanUtils.copyProperties(this, signInRecord);
        signInRecord.setAppliedByDoctor(Ebean.find(Employee.class, this.getAppliedByDoctorId()));
        signInRecord.setPatientSignIn(Ebean.find(PatientSignIn.class, this.getSignInId()));
        return signInRecord;
    }
}
