package lukelin.his.dto.yb.inventory.req;

import java.math.BigDecimal;

public class PriceAdjustment {
    private String kfbh;

    private String cwzbh;

    private String wzbh;

    private BigDecimal thdj;

    private String tjrq;

    public String getKfbh() {
        return kfbh;
    }

    public void setKfbh(String kfbh) {
        this.kfbh = kfbh;
    }

    public String getCwzbh() {
        return cwzbh;
    }

    public void setCwzbh(String cwzbh) {
        this.cwzbh = cwzbh;
    }

    public String getWzbh() {
        return wzbh;
    }

    public void setWzbh(String wzbh) {
        this.wzbh = wzbh;
    }

    public BigDecimal getThdj() {
        return thdj;
    }

    public void setThdj(BigDecimal thdj) {
        this.thdj = thdj;
    }

    public String getTjrq() {
        return tjrq;
    }

    public void setTjrq(String tjrq) {
        this.tjrq = tjrq;
    }
}
