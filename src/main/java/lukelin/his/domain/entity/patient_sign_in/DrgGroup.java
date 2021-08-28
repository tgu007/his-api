package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.PatientSignIn.DrgGroupType;
import lukelin.his.dto.signin.response.DrgGroupRespDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "patient_sign_in.drg_group")
public class DrgGroup extends BaseEntity implements DtoConvertible<DrgGroupRespDto> {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "drg_type", nullable = false)
    private DrgGroupType groupType;

    @Column(name = "lower_limit", nullable = false)
    private BigDecimal lowerLimit;

    @Column(name = "upper_limit", nullable = false)
    private BigDecimal upperLimit;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

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

    @Override
    public DrgGroupRespDto toDto() {
        DrgGroupRespDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
