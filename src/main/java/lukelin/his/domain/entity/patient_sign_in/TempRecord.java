package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.SnapshotSignInInfoInterface;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.enums.PatientSignIn.UrineVolume;
import lukelin.his.dto.signin.response.TempRecordRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "patient_sign_in.temp_record")
public class TempRecord extends BaseEntity implements DtoConvertible<TempRecordRespDto>, SnapshotSignInInfoInterface {
    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    private Double pulse;

    private Double breath;

    @Column(name = "blood_pressure_high")
    private Double bloodPressureHigh;

    @Column(name = "blood_pressure_low")
    private Double bloodPressureLow;

    @Column(name = "heart_beat")
    private Double heartBeat;

    private Double bowels;

    @Column(name = "temp_armpit")
    private Double armpitTemp;

    @Column(name = "temp_mouth")
    private Double mouthTemp;

    @Column(name = "temp_ear")
    private Double earTemp;

    @Column(name = "temp_rectal")
    private Double rectalTemp;

    @Column(name = "temp_adjust")
    private Double adjustTemp;

    @Column(name = "in_volume")
    private Double inVolume;

    @Column(name = "out_volume")
    private Double outVolume;

    @Column(name = "urine_volume")
    private Double urineVolume;

    private Double height;

    private Double weight;

    private String allergy;

    private String reference;

    @Column(name = "record_date")
    private Date recordDate;

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

    @Column(name = "can_not_stand")
    private Boolean canNotStand;

    @Column(name = "urine_volume_selection")
    private UrineVolume urineVolumeSelection;

    public UrineVolume getUrineVolumeSelection() {
        return urineVolumeSelection;
    }

    public void setUrineVolumeSelection(UrineVolume urineVolumeSelection) {
        this.urineVolumeSelection = urineVolumeSelection;
    }

    public Boolean getCanNotStand() {
        return canNotStand;
    }

    public void setCanNotStand(Boolean canNotStand) {
        this.canNotStand = canNotStand;
    }

    public void setWardDepartment(DepartmentTreatment wardDepartment) {
        this.wardDepartment = wardDepartment;
    }


    public void setWardDepartmentName(String wardDepartmentName) {
        this.wardDepartmentName = wardDepartmentName;
    }

    public DepartmentTreatment getWardDepartment() {
        return wardDepartment;
    }

    public String getWardDepartmentName() {
        return wardDepartmentName;
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

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
        this.snapshotSignIn(patientSignIn.getUuid());
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

    public Double getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(Double heartBeat) {
        this.heartBeat = heartBeat;
    }

    public Double getBowels() {
        return bowels;
    }

    public void setBowels(Double bowels) {
        this.bowels = bowels;
    }

    public Double getArmpitTemp() {
        return armpitTemp;
    }

    public void setArmpitTemp(Double armpitTemp) {
        this.armpitTemp = armpitTemp;
    }

    public Double getMouthTemp() {
        return mouthTemp;
    }

    public void setMouthTemp(Double mouthTemp) {
        this.mouthTemp = mouthTemp;
    }

    public Double getEarTemp() {
        return earTemp;
    }

    public void setEarTemp(Double earTemp) {
        this.earTemp = earTemp;
    }

    public Double getRectalTemp() {
        return rectalTemp;
    }

    public void setRectalTemp(Double rectalTemp) {
        this.rectalTemp = rectalTemp;
    }

    public Double getAdjustTemp() {
        return adjustTemp;
    }

    public void setAdjustTemp(Double adjustTemp) {
        this.adjustTemp = adjustTemp;
    }

    public Double getInVolume() {
        return inVolume;
    }

    public void setInVolume(Double inVolume) {
        this.inVolume = inVolume;
    }

    public Double getOutVolume() {
        return outVolume;
    }

    public void setOutVolume(Double outVolume) {
        this.outVolume = outVolume;
    }

    public Double getUrineVolume() {
        return urineVolume;
    }

    public void setUrineVolume(Double urineVolume) {
        this.urineVolume = urineVolume;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getWeightString() {
        if (this.canNotStand != null && this.canNotStand)
            return "卧床";
        else if (this.weight != null)
            return this.weight.intValue() + "";
        else
            return null;
    }

    public String getBloodPressureString() {
        if (this.getBloodPressureLow() != null && this.getBloodPressureHigh() != null)
            return this.getBloodPressureLow().intValue() + "/" + this.getBloodPressureHigh().intValue();
        else
            return "";
    }

    @Override
    public TempRecordRespDto toDto() {
        TempRecordRespDto dto = DtoUtils.convertRawDto(this);
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());
        dto.setPatientName(this.patientSignIn.getPatient().getName());
        return dto;
    }
}




