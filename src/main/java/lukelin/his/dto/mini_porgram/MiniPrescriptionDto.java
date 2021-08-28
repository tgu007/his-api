package lukelin.his.dto.mini_porgram;

import java.util.UUID;

public class MiniPrescriptionDto {
    private UUID uuid;

    private String description;

    private String frequency;

    private Integer allowedExecutionCount;

    public Integer getAllowedExecutionCount() {
        return allowedExecutionCount;
    }

    public void setAllowedExecutionCount(Integer allowedExecutionCount) {
        this.allowedExecutionCount = allowedExecutionCount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
