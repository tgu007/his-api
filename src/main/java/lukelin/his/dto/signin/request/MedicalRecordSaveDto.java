package lukelin.his.dto.signin.request;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.patient_sign_in.MedicalRecord;
import lukelin.his.domain.entity.patient_sign_in.MedicalRecordTag;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import java.util.*;
import java.util.UUID;

public class MedicalRecordSaveDto {
    private UUID uuid;

    private String name;

    private String content;

    private UUID typeId;

    private UUID patientSignInId;

    private UUID templateId;

    private Boolean silenceSave;

    private List<MedicalRecordTagSaveDto> tagList;

    public List<MedicalRecordTagSaveDto> getTagList() {
        return tagList;
    }

    public void setTagList(List<MedicalRecordTagSaveDto> tagList) {
        this.tagList = tagList;
    }

    public Boolean getSilenceSave() {
        return silenceSave;
    }

    public void setSilenceSave(Boolean silenceSave) {
        this.silenceSave = silenceSave;
    }

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public MedicalRecord toEntity() {
        MedicalRecord medicalRecord = new MedicalRecord();
        BeanUtils.copyPropertiesIgnoreNull(this, medicalRecord);
        medicalRecord.setType(new MedicalRecordType(this.getTypeId()));
        medicalRecord.setPatientSignIn(new PatientSignIn(this.getPatientSignInId()));
        if (this.templateId != null)
            medicalRecord.setTemplate(new MedicalRecordTemplate(this.templateId));

        if (this.getTagList() != null) {
            List<MedicalRecordTag> tagList = new ArrayList<>();
            for (MedicalRecordTagSaveDto tagDto : this.getTagList()) {
                MedicalRecordTag tag = tagDto.toEntity();
                tagList.add(tag);
            }
            medicalRecord.setTagList(tagList);
        }


        if (!this.silenceSave) {
            medicalRecord.setInEdit(false);
            medicalRecord.setInEditBy(null);
            medicalRecord.setInEditWhen(null);
        }
        return medicalRecord;
    }
}
