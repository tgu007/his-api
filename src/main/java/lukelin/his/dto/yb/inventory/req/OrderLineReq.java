package lukelin.his.dto.yb.inventory.req;

import java.util.List;

public class OrderLineReq {
    private String YWDH;

    private String CYWDH;

    private List<OrderLineDetailReq> YWMXLB;

    public String getYWDH() {
        return YWDH;
    }

    public void setYWDH(String YWDH) {
        this.YWDH = YWDH;
    }

    public String getCYWDH() {
        return CYWDH;
    }

    public void setCYWDH(String CYWDH) {
        this.CYWDH = CYWDH;
    }

    public List<OrderLineDetailReq> getYWMXLB() {
        return YWMXLB;
    }

    public void setYWMXLB(List<OrderLineDetailReq> YWMXLB) {
        this.YWMXLB = YWMXLB;
    }
}
