package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.enums.Basic.UserRoleType;

import java.util.*;
import java.util.UUID;

public class EmployeeFilter {
    private Boolean enabled;

    private UserRoleType userRoleType;

    private String phoneSearchCode;

    private String nameSearchCode;

    private UUID departmentId;

    private List<UUID> departmentIdList;

    private Boolean allowSupervise;

    private UUID userRoleId;

    public List<UUID> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<UUID> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    public UUID getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UUID userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Boolean getAllowSupervise() {
        return allowSupervise;
    }

    public void setAllowSupervise(Boolean allowSupervise) {
        this.allowSupervise = allowSupervise;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public String getPhoneSearchCode() {
        return phoneSearchCode;
    }

    public void setPhoneSearchCode(String phoneSearchCode) {
        this.phoneSearchCode = phoneSearchCode;
    }

    public String getNameSearchCode() {
        return nameSearchCode;
    }

    public void setNameSearchCode(String nameSearchCode) {
        this.nameSearchCode = nameSearchCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }
}
