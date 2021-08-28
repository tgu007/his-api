package lukelin.his.dto.signin.response;

import lukelin.his.domain.enums.PatientSignIn.DrgGroupType;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

public class DrgGroupRespDto {
    private String name;

    private DrgGroupType groupType;

    private BigDecimal lowerLimit;

    private BigDecimal upperLimit;

    private UUID uuid;

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public DrgGroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(DrgGroupType groupType) {
        this.groupType = groupType;
    }

    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }
}
