package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.util.Date;
import java.util.UUID;

public class MedicalRecordListDto {
    private UUID uuid;

    private String name;

    private UUID typeId;

    private UUID templateId;

    private UUID patientSignInId;

    private Boolean inEdit;

    private String inEditBy;

    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inEditWhen;

    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModified;

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Boolean getInEdit() {
        return inEdit;
    }

    public void setInEdit(Boolean inEdit) {
        this.inEdit = inEdit;
    }

    public String getInEditBy() {
        return inEditBy;
    }

    public void setInEditBy(String inEditBy) {
        this.inEditBy = inEditBy;
    }

    public Date getInEditWhen() {
        return inEditWhen;
    }

    public void setInEditWhen(Date inEditWhen) {
        this.inEditWhen = inEditWhen;
    }

    public UUID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(UUID templateId) {
        this.templateId = templateId;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
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

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }
}
