package lukelin.his.dto.account.response;

import java.util.UUID;

public class MiniFeeListDto extends BaseFeeListDto{
    private String executionTime;

    private UUID uuid;

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
