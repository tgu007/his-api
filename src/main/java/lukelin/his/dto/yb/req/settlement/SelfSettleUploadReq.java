package lukelin.his.dto.yb.req.settlement;

import java.math.BigDecimal;

public class SelfSettleUploadReq extends BaseSettleUploadReq {
    private SelfSettleUploadDetailReq ybjsxx;

    private String cjsbh;

    private BigDecimal fyze;

    private String xzdm;

    public String getXzdm() {
        return xzdm;
    }


    public BigDecimal getFyze() {
        return fyze;
    }

    public void setFyze(BigDecimal fyze) {
        this.fyze = fyze;
    }

    public void setXzdm(String xzdm) {
        this.xzdm = xzdm;
    }

    public String getCjsbh() {
        return cjsbh;
    }

    public void setCjsbh(String cjsbh) {
        this.cjsbh = cjsbh;
    }

    public SelfSettleUploadDetailReq getYbjsxx() {
        return ybjsxx;
    }

    public void setYbjsxx(SelfSettleUploadDetailReq ybjsxx) {
        this.ybjsxx = ybjsxx;
    }
}
