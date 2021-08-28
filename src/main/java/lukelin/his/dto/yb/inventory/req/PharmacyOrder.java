package lukelin.his.dto.yb.inventory.req;

import java.util.List;

public class PharmacyOrder {
    private String fybh;

    private String jzxh;

    private String sfglkf;

    private String bftybz;

    private List<PharmacyOrderLine> ypmx;

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
    }

    public String getJzxh() {
        return jzxh;
    }

    public void setJzxh(String jzxh) {
        this.jzxh = jzxh;
    }

    public String getSfglkf() {
        return sfglkf;
    }

    public void setSfglkf(String sfglkf) {
        this.sfglkf = sfglkf;
    }

    public String getBftybz() {
        return bftybz;
    }

    public void setBftybz(String bftybz) {
        this.bftybz = bftybz;
    }

    public List<PharmacyOrderLine> getYpmx() {
        return ypmx;
    }

    public void setYpmx(List<PharmacyOrderLine> ypmx) {
        this.ypmx = ypmx;
    }
}
