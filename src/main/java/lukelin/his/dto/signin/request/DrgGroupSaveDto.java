package lukelin.his.dto.signin.request;

import io.ebean.Ebean;
import lukelin.his.domain.entity.patient_sign_in.DrgGroup;
import lukelin.his.domain.enums.PatientSignIn.DrgGroupType;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class DrgGroupSaveDto {
    private String name;

    private DrgGroupType groupType;

    private BigDecimal lowerLimit;

    private BigDecimal upperLimit;

    private UUID uuid;

    private Boolean enabled;

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

    public DrgGroup toEntity() {
        DrgGroup drgGroup = null;
        if (this.getUuid() == null) {
            drgGroup = new DrgGroup();
        } else
            drgGroup = Ebean.find(DrgGroup.class, this.getUuid());
        BeanUtils.copyProperties(this, drgGroup);
        drgGroup.setEnabled(this.enabled);
        return drgGroup;
    }
}
