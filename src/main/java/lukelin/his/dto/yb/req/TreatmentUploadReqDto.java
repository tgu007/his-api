package lukelin.his.dto.yb.req;

import java.util.List;
import java.util.UUID;

public class TreatmentUploadReqDto {
    private UUID uuid;

    private String cfwdm;

    private String fwdm;

    private String kpxm;

    private String fwmc;

    private String dw;

    private String dj;

    private String zxks;

    private String zxbz;

    private List<TreatmentUploadCenterInfo> dzxxy;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCfwdm() {
        return cfwdm;
    }

    public void setCfwdm(String cfwdm) {
        this.cfwdm = cfwdm;
    }

    public String getFwdm() {
        return fwdm;
    }

    public void setFwdm(String fwdm) {
        this.fwdm = fwdm;
    }

    public String getKpxm() {
        return kpxm;
    }

    public void setKpxm(String kpxm) {
        this.kpxm = kpxm;
    }

    public String getFwmc() {
        return fwmc;
    }

    public void setFwmc(String fwmc) {
        this.fwmc = fwmc;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getZxks() {
        return zxks;
    }

    public void setZxks(String zxks) {
        this.zxks = zxks;
    }

    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz;
    }

    public List<TreatmentUploadCenterInfo> getDzxxy() {
        return dzxxy;
    }

    public void setDzxxy(List<TreatmentUploadCenterInfo> dzxxy) {
        this.dzxxy = dzxxy;
    }
}
