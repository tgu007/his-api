package lukelin.his.dto.yb.inventory.resp;

import java.util.List;

public class OrderSubmitResp {
    private String ywdh;

    private String cywdh;

    private String czjz;

    private List<OrderSubmitLineResp> mxxhlb;

    public String getYwdh() {
        return ywdh;
    }

    public void setYwdh(String ywdh) {
        this.ywdh = ywdh;
    }

    public String getCywdh() {
        return cywdh;
    }

    public void setCywdh(String cywdh) {
        this.cywdh = cywdh;
    }

    public String getCzjz() {
        return czjz;
    }

    public void setCzjz(String czjz) {
        this.czjz = czjz;
    }

    public List<OrderSubmitLineResp> getMxxhlb() {
        return mxxhlb;
    }

    public void setMxxhlb(List<OrderSubmitLineResp> mxxhlb) {
        this.mxxhlb = mxxhlb;
    }
}
