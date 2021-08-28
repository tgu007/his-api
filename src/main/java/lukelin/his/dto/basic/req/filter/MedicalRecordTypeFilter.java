package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.entity.basic.codeEntity.UserRole;

import java.util.List;
import java.util.UUID;

public class MedicalRecordTypeFilter {
    private List<UUID> userRoleIIdList;

    private Boolean enabled;

    private Boolean fixedFormat;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getFixedFormat() {
        return fixedFormat;
    }

    public void setFixedFormat(Boolean fixedFormat) {
        this.fixedFormat = fixedFormat;
    }

    public List<UUID> getUserRoleIIdList() {
        return userRoleIIdList;
    }

    public void setUserRoleIIdList(List<UUID> userRoleIIdList) {
        this.userRoleIIdList = userRoleIIdList;
    }
}
