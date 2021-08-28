package lukelin.his.dto.account.request;

import java.util.List;
import java.util.UUID;

public class FullFeeCancelListReq {
    private List<UUID> uuidList;

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<UUID> getUuidList() {
        return uuidList;
    }

    public void setUuidList(List<UUID> uuidList) {
        this.uuidList = uuidList;
    }
}
