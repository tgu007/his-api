package lukelin.his.domain.entity.basic.template;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.his.domain.entity.basic.entity.ItemPriceLog;
import lukelin.his.dto.basic.resp.template.MedicalRecordTypeDto;
import lukelin.his.dto.signin.response.PatientMedicalRecordTypeDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.medical_record_type")
public class MedicalRecordType extends BaseEntity implements DtoConvertible<MedicalRecordTypeDto> {
    public MedicalRecordType(UUID typeId) {
        this.setUuid(typeId);
    }

    public MedicalRecordType() {

    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal order;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "fixed_format", nullable = false)
    private boolean fixedFormat;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private List<MedicalRecordTemplate> templateList;

    @ManyToMany
    @JoinTable(name = "basic.medical_record_type_permitted_role",
            joinColumns = {@JoinColumn(name = "medical_record_type_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "user_role_id", referencedColumnName = "uuid")})
    private Set<UserRole> permittedRoleSet;

    public Set<UserRole> getPermittedRoleSet() {
        return permittedRoleSet;
    }

    public void setPermittedRoleSet(Set<UserRole> permittedRoleSet) {
        this.permittedRoleSet = permittedRoleSet;
    }

    public List<MedicalRecordTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<MedicalRecordTemplate> templateList) {
        this.templateList = templateList;
    }

    public boolean isFixedFormat() {
        return fixedFormat;
    }

    public void setFixedFormat(boolean fixedFormat) {
        this.fixedFormat = fixedFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOrder() {
        return order;
    }

    public void setOrder(BigDecimal order) {
        this.order = order;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public MedicalRecordTypeDto toDto() {
        MedicalRecordTypeDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }

    public PatientMedicalRecordTypeDto toPatientRecordDto() {
        PatientMedicalRecordTypeDto dto = new PatientMedicalRecordTypeDto();
        List<UUID> roleIdList = new ArrayList<>();
        for (UserRole role : this.getPermittedRoleSet())
            roleIdList.add(role.getUuid());
        dto.setPermittedRoleIdList(roleIdList);
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        return dto;
    }
}
