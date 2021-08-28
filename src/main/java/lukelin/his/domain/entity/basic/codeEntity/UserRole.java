package lukelin.his.domain.entity.basic.codeEntity;

import io.ebean.annotation.DbJsonB;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.enums.Basic.UserRoleType;
import lukelin.his.dto.basic.resp.setup.UserRoleDto;
import lukelin.his.dto.basic.resp.setup.UserRoleListDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Map;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_role")
public class UserRole extends BaseCodeEntity implements DtoConvertible<UserRoleDto> {
    public UserRole(UUID uuid) {
        this.setUuid(uuid);
    }

    @Column(name = "role_type")
    private UserRoleType userRoleType;

    @DbJsonB
    @Column(name = "ui_permission")
    private Map<String, Object> uiPermission;

    @ManyToOne
    @JoinColumn(name = "default_department_id", nullable = false)
    private DepartmentTreatment defaultDepartment;

    public DepartmentTreatment getDefaultDepartment() {
        return defaultDepartment;
    }

    public void setDefaultDepartment(DepartmentTreatment defaultDepartment) {
        this.defaultDepartment = defaultDepartment;
    }

    public Map<String, Object> getUiPermission() {
        return uiPermission;
    }

    public void setUiPermission(Map<String, Object> uiPermission) {
        this.uiPermission = uiPermission;
    }

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }

    @Override
    public UserRoleDto toDto() {
        UserRoleDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }

    public UserRoleListDto toListDto() {
        UserRoleListDto dto = new UserRoleListDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        return dto;
    }

}
