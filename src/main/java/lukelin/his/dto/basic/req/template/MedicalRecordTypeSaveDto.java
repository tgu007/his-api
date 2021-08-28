package lukelin.his.dto.basic.req.template;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;

import java.math.BigDecimal;
import java.util.UUID;

public class MedicalRecordTypeSaveDto {
    private UUID uuid;

    private String name;

    private BigDecimal order;

    private boolean enabled;

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

    public MedicalRecordType toEntity() {
        MedicalRecordType medicalRecordType = new MedicalRecordType();
        BeanUtils.copyPropertiesIgnoreNull(this, medicalRecordType);
        return medicalRecordType;
    }
}
