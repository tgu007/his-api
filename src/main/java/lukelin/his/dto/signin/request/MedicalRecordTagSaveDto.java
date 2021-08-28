package lukelin.his.dto.signin.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.entity.patient_sign_in.MedicalRecord;
import lukelin.his.domain.entity.patient_sign_in.MedicalRecordTag;

import java.util.Date;
import java.util.UUID;

public class MedicalRecordTagSaveDto {
    private UUID tagId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tagTime;

    private UUID medicalRecordId;

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    public UUID getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(UUID medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public Date getTagTime() {
        return tagTime;
    }

    public void setTagTime(Date tagTime) {
        this.tagTime = tagTime;
    }

    public MedicalRecordTag toEntity()
    {
        MedicalRecordTag tag = new MedicalRecordTag();
        tag.setUuid(this.getTagId());
        tag.setTagTime(this.getTagTime());
//        MedicalRecord record = new MedicalRecord();
//        record.setUuid(this.getMedicalRecordId());
//        tag.setMedicalRecord(record);
        return tag;
    }
}
