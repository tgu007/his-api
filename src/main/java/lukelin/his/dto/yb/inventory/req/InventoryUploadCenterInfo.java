package lukelin.his.dto.yb.inventory.req;

import java.math.BigDecimal;

public class InventoryUploadCenterInfo {
    private String ybmc;

    private String zxnm;

    private String bzbm;

    private BigDecimal zfbl;

    public String getYbmc() {
        return ybmc;
    }

    public void setYbmc(String ybmc) {
        this.ybmc = ybmc;
    }

    public String getZxnm() {
        return zxnm;
    }

    public void setZxnm(String zxnm) {
        this.zxnm = zxnm;
    }

    public String getBzbm() {
        return bzbm;
    }

    public void setBzbm(String bzbm) {
        this.bzbm = bzbm;
    }

    public BigDecimal getZfbl() {
        return zfbl;
    }

    public void setZfbl(BigDecimal zfbl) {
        this.zfbl = zfbl;
    }
}
