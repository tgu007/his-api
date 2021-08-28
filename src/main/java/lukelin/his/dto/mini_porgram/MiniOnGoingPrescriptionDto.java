package lukelin.his.dto.mini_porgram;

import java.util.UUID;

public class MiniOnGoingPrescriptionDto {
    private UUID uuid;

    private String description;

    private String startTime;

    private String expectFinishTime;

    private String executionTime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpectFinishTime() {
        return expectFinishTime;
    }

    public void setExpectFinishTime(String expectFinishTime) {
        this.expectFinishTime = expectFinishTime;
    }
}
