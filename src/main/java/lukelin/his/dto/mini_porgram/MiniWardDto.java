package lukelin.his.dto.mini_porgram;

import java.util.List;
import java.util.UUID;

public class MiniWardDto {
    private UUID uuid;

    private String name;

    private List<MiniPatientDto> patientList;

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

    public List<MiniPatientDto> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<MiniPatientDto> patientList) {
        this.patientList = patientList;
    }
}
