package lukelin.his.dto.yb.inventory.resp;

import java.util.List;

public class OrderLineResp {
    private String YWDH;

    private List<OrderLineDetailResp> YWMXLB;

    public String getYWDH() {
        return YWDH;
    }

    public void setYWDH(String YWDH) {
        this.YWDH = YWDH;
    }

    public List<OrderLineDetailResp> getYWMXLB() {
        return YWMXLB;
    }

    public void setYWMXLB(List<OrderLineDetailResp> YWMXLB) {
        this.YWMXLB = YWMXLB;
    }
}
