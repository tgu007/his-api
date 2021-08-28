package lukelin.his.dto.basic.resp.template;

import lukelin.his.domain.enums.Basic.MedicalRecordTemplatePermissionType;

import java.util.List;
import java.util.UUID;

public class MedicalRecordTemplateListDto {
    private UUID uuid;

    private String name;

    private Boolean enabled;

    private MedicalRecordTemplatePermissionType permissionType;

    private MedicalRecordTypeDto recordType;

    private String writer;

    private String joinedDepartmentName;

    private String owner;

    private String lastModifier;

    private UUID ownedByDoctorId;

    private List<UUID> permittedDepartmentIdList;

    public UUID getOwnedByDoctorId() {
        return ownedByDoctorId;
    }

    public void setOwnedByDoctorId(UUID ownedByDoctorId) {
        this.ownedByDoctorId = ownedByDoctorId;
    }

    public List<UUID> getPermittedDepartmentIdList() {
        return permittedDepartmentIdList;
    }

    public void setPermittedDepartmentIdList(List<UUID> permittedDepartmentIdList) {
        this.permittedDepartmentIdList = permittedDepartmentIdList;
    }

    public String getJoinedDepartmentName() {
        return joinedDepartmentName;
    }

    public void setJoinedDepartmentName(String joinedDepartmentName) {
        this.joinedDepartmentName = joinedDepartmentName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public MedicalRecordTypeDto getRecordType() {
        return recordType;
    }

    public void setRecordType(MedicalRecordTypeDto recordType) {
        this.recordType = recordType;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public MedicalRecordTemplatePermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(MedicalRecordTemplatePermissionType permissionType) {
        this.permissionType = permissionType;
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
}
