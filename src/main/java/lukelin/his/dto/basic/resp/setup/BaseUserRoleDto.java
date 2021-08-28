package lukelin.his.dto.basic.resp.setup;

import lukelin.his.domain.enums.Basic.UserRoleType;

public abstract class BaseUserRoleDto extends BaseCodeDto {
    private UserRoleType userRoleType;

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }
}
