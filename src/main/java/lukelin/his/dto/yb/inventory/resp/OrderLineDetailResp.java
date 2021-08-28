package lukelin.his.dto.yb.inventory.resp;

import java.util.UUID;

public class OrderLineDetailResp {
    private UUID CMXXH;

    private String MXXH;

    public UUID getCMXXH() {
        return CMXXH;
    }

    public void setCMXXH(UUID CMXXH) {
        this.CMXXH = CMXXH;
    }

    public String getMXXH() {
        return MXXH;
    }

    public void setMXXH(String MXXH) {
        this.MXXH = MXXH;
    }
}
