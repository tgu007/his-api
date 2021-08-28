package lukelin.his.domain.entity.basic.template;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.patient_sign_in.MedicalRecord;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.Basic.MedicalRecordTemplatePermissionType;
import lukelin.his.dto.basic.resp.template.MedicalRecordTemplateDto;
import lukelin.his.dto.basic.resp.template.MedicalRecordTemplateListDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.medical_record_template")
public class MedicalRecordTemplate extends BaseEntity implements DtoConvertible<MedicalRecordTemplateDto> {
    public MedicalRecordTemplate(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    public MedicalRecordTemplate() {

    }

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String template;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private MedicalRecordType type;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee ownedByDoctor;

    @Column(nullable = false, name = "permission_type")
    private MedicalRecordTemplatePermissionType permissionType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "basic.medical_record_template_department",
            joinColumns = {@JoinColumn(name = "template_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "uuid")})
    private Set<DepartmentTreatment> permittedDepartmentList;


    public Employee getOwnedByDoctor() {
        return ownedByDoctor;
    }

    public void setOwnedByDoctor(Employee ownedByDoctor) {
        this.ownedByDoctor = ownedByDoctor;
    }

    public MedicalRecordTemplatePermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(MedicalRecordTemplatePermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public Set<DepartmentTreatment> getPermittedDepartmentList() {
        return permittedDepartmentList;
    }

    public void setPermittedDepartmentList(Set<DepartmentTreatment> permittedDepartmentList) {
        this.permittedDepartmentList = permittedDepartmentList;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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

    public MedicalRecordType getType() {
        return type;
    }

    public void setType(MedicalRecordType type) {
        this.type = type;
    }

    @Override
    public MedicalRecordTemplateDto toDto() {
        MedicalRecordTemplateDto dto = DtoUtils.convertRawDto(this);
        this.updateDtoProperties(dto);
        return dto;
    }

    public MedicalRecordTemplateListDto toListDto() {
        MedicalRecordTemplateListDto dto = new MedicalRecordTemplateListDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        this.updateDtoProperties(dto);
        return dto;
    }

    private void updateDtoProperties(MedicalRecordTemplateListDto dto) {
        dto.setRecordType(this.getType().toDto());
        dto.setWriter(this.getWhoCreatedName());
        dto.setLastModifier(this.getWhoModifiedName());

        if (this.getOwnedByDoctor() != null) {
            dto.setOwner(this.getOwnedByDoctor().getName());
            dto.setOwnedByDoctorId(this.ownedByDoctor.getUuid());
        }

        List<UUID> permittedDepartmentIdList = new ArrayList<>();
        String joinedDepartmentName = "";
        for (DepartmentTreatment department : this.getPermittedDepartmentList()) {
            permittedDepartmentIdList.add(department.getUuid());
            joinedDepartmentName += department.getDepartment().getName() + ";";
        }
        dto.setPermittedDepartmentIdList(permittedDepartmentIdList);
        dto.setJoinedDepartmentName(joinedDepartmentName);
    }

    public MedicalRecord toMockMedicalRecord(PatientSignIn patientSignIn) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setTemplate(this);
        medicalRecord.setName(this.name);
        medicalRecord.setType(this.type);
        medicalRecord.setPatientSignIn(patientSignIn);
        return medicalRecord;
    }
}
