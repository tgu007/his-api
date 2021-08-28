package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.PatientSignIn.PatientSignOutStatus;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.signin.response.PatientSignOutRequestDto;
import lukelin.his.dto.yb.resp.SignOutReqDto;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "patient_sign_in.patient_sign_out_request")
public class PatientSignOutRequest extends BaseEntity {
    private PatientSignOutStatus status;

    @OneToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne
    @JoinColumn(name = "sign_out_reason_id", nullable = false)
    private Dictionary signOutReason;

    @Column(name = "sign_out_date")
    private Date signOutDate;

    private String reference;

    private String advise;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentTreatment departmentTreatment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Employee doctor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "patient_sign_in.patient_sign_out_diagnose",
            joinColumns = {@JoinColumn(name = "sign_out_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "diagnose_id", referencedColumnName = "uuid")})
    private Set<Diagnose> diagnoseSet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sign_out_prescription_id")
    private Prescription signOutPrescription;

    public Prescription getSignOutPrescription() {
        return signOutPrescription;
    }

    public void setSignOutPrescription(Prescription signOutPrescription) {
        this.signOutPrescription = signOutPrescription;
    }

    public DepartmentTreatment getDepartmentTreatment() {
        return departmentTreatment;
    }

    public void setDepartmentTreatment(DepartmentTreatment departmentTreatment) {
        this.departmentTreatment = departmentTreatment;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public Set<Diagnose> getDiagnoseSet() {
        return diagnoseSet;
    }

    public void setDiagnoseSet(Set<Diagnose> diagnoseSet) {
        this.diagnoseSet = diagnoseSet;
    }

    public PatientSignOutStatus getStatus() {
        return status;
    }

    public void setStatus(PatientSignOutStatus status) {
        this.status = status;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public Dictionary getSignOutReason() {
        return signOutReason;
    }

    public void setSignOutReason(Dictionary signOutReason) {
        this.signOutReason = signOutReason;
    }

    public Date getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(Date signOutDate) {
        this.signOutDate = signOutDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public SignOutReqDto toYBSignOutReqDto() {
        SignOutReqDto reqDto = new SignOutReqDto();
        reqDto.setJzxh(this.getPatientSignIn().getYbSignIn().getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        reqDto.setCysj(df.format(this.getSignOutDate()));
        reqDto.setCyyy(this.getSignOutReason().getExtraInfo());
        reqDto.setZdks(this.getDepartmentTreatment().getDepartment().getSequence().toString());
        reqDto.setZdys(this.getDoctor().getName());

        Diagnose diagnose = (Diagnose) this.getDiagnoseSet().toArray()[0];
        reqDto.setJbbm(diagnose.getCode());
        reqDto.setJbmc(diagnose.getName());
        reqDto.setCyczks(reqDto.getZdks());
        reqDto.setCyxj(this.getReference());
        return reqDto;
    }

    public PatientSignOutRequestDto toDto() {
        PatientSignOutRequestDto dto = new PatientSignOutRequestDto();
        BeanUtils.copyProperties(this, dto);
        dto.setDepartmentTreatment(this.getDepartmentTreatment().toDto());
        dto.setDoctor(this.getDoctor().toDto());
        dto.setSignOutReason(this.getSignOutReason().toDto());
        dto.setDrgSignOutReasonId(this.getSignOutReason().getDrgSignOutReasonMapping().getSignOutReason().getUuid());
        List<DiagnoseDto> diagnoseDtoList = new ArrayList<>();
        for (Diagnose diagnose : this.getDiagnoseSet())
            diagnoseDtoList.add(diagnose.toDto());
        dto.setDiagnoseList(diagnoseDtoList);
        return dto;
    }
}
