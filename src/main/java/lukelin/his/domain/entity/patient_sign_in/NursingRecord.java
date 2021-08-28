package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.SnapshotSignInInfoInterface;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.enums.PatientSignIn.Consciousness;
import lukelin.his.domain.enums.PatientSignIn.PipingCondition;
import lukelin.his.domain.enums.PatientSignIn.PupilCondition;
import lukelin.his.dto.signin.response.NursingRecordRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "patient_sign_in.nursing_record")
public class NursingRecord extends BaseEntity implements DtoConvertible<NursingRecordRespDto>, SnapshotSignInInfoInterface {
    @Column(name = "temp_body")
    private Double bodyTemp;

    private Double pulse;

    private Double breath;

    @Column(name = "blood_pressure_high")
    private Double bloodPressureHigh;

    @Column(name = "blood_pressure_low")
    private Double bloodPressureLow;

    private Double spo2;

    private Double oxygen;

    private Consciousness consciousness;

    @Column(name = "pupil_left")
    private PupilCondition leftPupil;

    @Column(name = "pupil_right")
    private PupilCondition rightPupil;

    @Column(name = "pupil_left_size")
    private String leftPupilSize;

    @Column(name = "pupil_right_size")
    private String rightPupilSize;

    @Column(name = "in_name")
    private String inName;

    @Column(name = "in_volume")
    private Double inVolume;

    @Column(name = "out_name")
    private String outName;

    @Column(name = "out_volume")
    private Double outVolume;

    @Column(name = "out_condition")
    private String outCondition;

    @Column(name = "piping_condition")
    private PipingCondition pipingCondition;

    @Column(name = "skin_condition")
    private String skinCondition;

    @Column(name = "pressure_sore_score")
    private Double pressureSoreScore;

    @Column(name = "fall_score")
    private Double fallScore;

    @Column(name = "care_oneself_score")
    private Double careOneselfScore;

    private String description;

    @Column(name = "record_date")
    private Date recordDate;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private WardRoomBed bed;

    @Column(name = "ward_name", nullable = false)
    private String WardName;

    @ManyToOne
    @JoinColumn(name = "ward_department_id")
    private DepartmentTreatment wardDepartment;

    @Column(name = "ward_department_name", nullable = false)
    private String wardDepartmentName;

    public String getLeftPupilSize() {
        return leftPupilSize;
    }

    public void setLeftPupilSize(String leftPupilSize) {
        this.leftPupilSize = leftPupilSize;
    }

    public String getRightPupilSize() {
        return rightPupilSize;
    }

    public void setRightPupilSize(String rightPupilSize) {
        this.rightPupilSize = rightPupilSize;
    }

    public WardRoomBed getBed() {
        return bed;
    }

    public void setBed(WardRoomBed bed) {
        this.bed = bed;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public DepartmentTreatment getWardDepartment() {
        return wardDepartment;
    }

    public void setWardDepartment(DepartmentTreatment wardDepartment) {
        this.wardDepartment = wardDepartment;
    }

    public String getWardDepartmentName() {
        return wardDepartmentName;
    }

    public void setWardDepartmentName(String wardDepartmentName) {
        this.wardDepartmentName = wardDepartmentName;
    }

    public Double getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(Double bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public Double getPulse() {
        return pulse;
    }

    public void setPulse(Double pulse) {
        this.pulse = pulse;
    }

    public Double getBreath() {
        return breath;
    }

    public void setBreath(Double breath) {
        this.breath = breath;
    }

    public Double getBloodPressureHigh() {
        return bloodPressureHigh;
    }

    public void setBloodPressureHigh(Double bloodPressureHigh) {
        this.bloodPressureHigh = bloodPressureHigh;
    }

    public Double getBloodPressureLow() {
        return bloodPressureLow;
    }

    public void setBloodPressureLow(Double bloodPressureLow) {
        this.bloodPressureLow = bloodPressureLow;
    }

    public Double getSpo2() {
        return spo2;
    }

    public void setSpo2(Double spo2) {
        this.spo2 = spo2;
    }

    public Double getOxygen() {
        return oxygen;
    }

    public void setOxygen(Double oxygen) {
        this.oxygen = oxygen;
    }

    public Consciousness getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(Consciousness consciousness) {
        this.consciousness = consciousness;
    }

    public PupilCondition getLeftPupil() {
        return leftPupil;
    }

    public void setLeftPupil(PupilCondition leftPupil) {
        this.leftPupil = leftPupil;
    }

    public PupilCondition getRightPupil() {
        return rightPupil;
    }

    public void setRightPupil(PupilCondition rightPupil) {
        this.rightPupil = rightPupil;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public Double getInVolume() {
        return inVolume;
    }

    public void setInVolume(Double inVolume) {
        this.inVolume = inVolume;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public Double getOutVolume() {
        return outVolume;
    }

    public void setOutVolume(Double outVolume) {
        this.outVolume = outVolume;
    }

    public String getOutCondition() {
        return outCondition;
    }

    public void setOutCondition(String outCondition) {
        this.outCondition = outCondition;
    }

    public PipingCondition getPipingCondition() {
        return pipingCondition;
    }

    public void setPipingCondition(PipingCondition pipingCondition) {
        this.pipingCondition = pipingCondition;
    }

    public String getSkinCondition() {
        return skinCondition;
    }

    public void setSkinCondition(String skinCondition) {
        this.skinCondition = skinCondition;
    }

    public Double getPressureSoreScore() {
        return pressureSoreScore;
    }

    public void setPressureSoreScore(Double pressureSoreScore) {
        this.pressureSoreScore = pressureSoreScore;
    }

    public Double getFallScore() {
        return fallScore;
    }

    public void setFallScore(Double fallScore) {
        this.fallScore = fallScore;
    }

    public Double getCareOneselfScore() {
        return careOneselfScore;
    }

    public void setCareOneselfScore(Double careOneselfScore) {
        this.careOneselfScore = careOneselfScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
        this.snapshotSignIn(patientSignIn.getUuid());
    }

    @Override
    public NursingRecordRespDto toDto() {
        NursingRecordRespDto dto = DtoUtils.convertRawDto(this);
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());
        dto.setPatientName(this.patientSignIn.getPatient().getName());
        dto.setWhoCreatedName(this.getWhoCreatedName());
        return dto;
    }
}
