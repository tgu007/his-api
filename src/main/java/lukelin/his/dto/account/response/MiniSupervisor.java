package lukelin.his.dto.account.response;

import java.util.Date;
import java.util.UUID;

public class MiniSupervisor {
    private String supervisorName;

    private UUID supervisorId;

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public UUID getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(UUID supervisorId) {
        this.supervisorId = supervisorId;
    }
}
