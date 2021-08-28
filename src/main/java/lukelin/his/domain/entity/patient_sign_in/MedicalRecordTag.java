package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.dto.signin.response.MedicalRecordTagDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "patient_sign_in.medical_record_tag")
public class MedicalRecordTag extends BaseEntity implements DtoConvertible<MedicalRecordTagDto> {
    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;

    private Date tagTime;

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public Date getTagTime() {
        return tagTime;
    }

    public void setTagTime(Date tagTime) {
        this.tagTime = tagTime;
    }

    @Override
    public MedicalRecordTagDto toDto() {
        MedicalRecordTagDto tag = new MedicalRecordTagDto();
        tag.setUuid(this.getUuid());
        tag.setTagTime(this.getTagTime());
        return tag;
    }
}
