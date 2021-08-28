package lukelin.his.dto.basic.req.template;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.enums.Basic.MedicalRecordTemplatePermissionType;

import java.util.*;

public class MedicalRecordTemplateSaveDto {
    private UUID uuid;

    private Boolean enabled;

    private String name;

    private String template;

    private UUID typeId;

    private UUID ownedByDoctorId;

    private MedicalRecordTemplatePermissionType permissionType;

    private List<UUID> permittedDepartmentIdList;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public UUID getOwnedByDoctorId() {
        return ownedByDoctorId;
    }

    public void setOwnedByDoctorId(UUID ownedByDoctorId) {
        this.ownedByDoctorId = ownedByDoctorId;
    }

    public MedicalRecordTemplatePermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(MedicalRecordTemplatePermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public List<UUID> getPermittedDepartmentIdList() {
        return permittedDepartmentIdList;
    }

    public void setPermittedDepartmentIdList(List<UUID> permittedDepartmentIdList) {
        this.permittedDepartmentIdList = permittedDepartmentIdList;
    }

    public MedicalRecordTemplate toEntity() {
        MedicalRecordTemplate template = new MedicalRecordTemplate();
        BeanUtils.copyPropertiesIgnoreNull(this, template);

        template.setType(new MedicalRecordType(this.getTypeId()));

        if (ownedByDoctorId != null)
            template.setOwnedByDoctor(new Employee(ownedByDoctorId));

        Set<DepartmentTreatment> permittedDepartmentList = new HashSet<>();
        for (UUID departmentId : this.getPermittedDepartmentIdList()) {
            DepartmentTreatment departmentTreatment = new DepartmentTreatment(departmentId);
            permittedDepartmentList.add(departmentTreatment);
        }
        template.setPermittedDepartmentList(permittedDepartmentList);
        return template;
    }

}
