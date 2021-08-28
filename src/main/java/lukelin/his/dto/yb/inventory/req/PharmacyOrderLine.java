package lukelin.his.dto.yb.inventory.req;

import java.util.List;

public class PharmacyOrderLine {
    private String kfbh;

    private String pch;

    private String fysl;

    private List<PharmacyOrderLineTrackingCode> ypzsmlb;

    private String clkcbh;

    public String getKfbh() {
        return kfbh;
    }

    public void setKfbh(String kfbh) {
        this.kfbh = kfbh;
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch;
    }

    public String getFysl() {
        return fysl;
    }

    public void setFysl(String fysl) {
        this.fysl = fysl;
    }

    public List<PharmacyOrderLineTrackingCode> getYpzsmlb() {
        return ypzsmlb;
    }

    public void setYpzsmlb(List<PharmacyOrderLineTrackingCode> ypzsmlb) {
        this.ypzsmlb = ypzsmlb;
    }

    public String getClkcbh() {
        return clkcbh;
    }

    public void setClkcbh(String clkcbh) {
        this.clkcbh = clkcbh;
    }
}
