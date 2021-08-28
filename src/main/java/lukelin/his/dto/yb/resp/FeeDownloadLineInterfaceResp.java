package lukelin.his.dto.yb.resp;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.yb.FeeDownloadLine;
import lukelin.his.domain.entity.yb.FeeDownloadMedTrack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FeeDownloadLineInterfaceResp {
    private String cfybh;

    private String fydm;

    private String fylb;

    private String kpxm;

    private String pch;

    private String mc;

    private String gg;

    private String dw;

    private BigDecimal sl;

    private BigDecimal dj;

    private BigDecimal je;

    private String ffbz;

    private String ts;

    private BigDecimal mtsl;

    private String mtyl;

    private BigDecimal yyts;

    private String yf;

    private String ybsx;

    private BigDecimal zlbl;

    private BigDecimal zlje;

    private BigDecimal zfje;

    private BigDecimal djxe;

    private String ybscbz;

    private String fybh;

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
    }

    private List<FeeDownloadInterfaceMedTrackResp> ypzsmlb;

    public String getCfybh() {
        return cfybh;
    }

    public void setCfybh(String cfybh) {
        this.cfybh = cfybh;
    }

    public String getFydm() {
        return fydm;
    }

    public void setFydm(String fydm) {
        this.fydm = fydm;
    }

    public String getFylb() {
        return fylb;
    }

    public void setFylb(String fylb) {
        this.fylb = fylb;
    }

    public String getKpxm() {
        return kpxm;
    }

    public void setKpxm(String kpxm) {
        this.kpxm = kpxm;
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getDj() {
        return dj;
    }

    public void setDj(BigDecimal dj) {
        this.dj = dj;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getFfbz() {
        return ffbz;
    }

    public void setFfbz(String ffbz) {
        this.ffbz = ffbz;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public BigDecimal getMtsl() {
        return mtsl;
    }

    public void setMtsl(BigDecimal mtsl) {
        this.mtsl = mtsl;
    }

    public String getMtyl() {
        return mtyl;
    }

    public void setMtyl(String mtyl) {
        this.mtyl = mtyl;
    }

    public BigDecimal getYyts() {
        return yyts;
    }

    public void setYyts(BigDecimal yyts) {
        this.yyts = yyts;
    }

    public String getYf() {
        return yf;
    }

    public void setYf(String yf) {
        this.yf = yf;
    }

    public String getYbsx() {
        return ybsx;
    }

    public void setYbsx(String ybsx) {
        this.ybsx = ybsx;
    }

    public BigDecimal getZlbl() {
        return zlbl;
    }

    public void setZlbl(BigDecimal zlbl) {
        this.zlbl = zlbl;
    }

    public BigDecimal getZlje() {
        return zlje;
    }

    public void setZlje(BigDecimal zlje) {
        this.zlje = zlje;
    }

    public BigDecimal getZfje() {
        return zfje;
    }

    public void setZfje(BigDecimal zfje) {
        this.zfje = zfje;
    }

    public BigDecimal getDjxe() {
        return djxe;
    }

    public void setDjxe(BigDecimal djxe) {
        this.djxe = djxe;
    }

    public String getYbscbz() {
        return ybscbz;
    }

    public void setYbscbz(String ybscbz) {
        this.ybscbz = ybscbz;
    }

    public List<FeeDownloadInterfaceMedTrackResp> getYpzsmlb() {
        return ypzsmlb;
    }

    public void setYpzsmlb(List<FeeDownloadInterfaceMedTrackResp> ypzsmlb) {
        this.ypzsmlb = ypzsmlb;
    }

    public FeeDownloadLine toEntity() {
        FeeDownloadLine downloadLine = new FeeDownloadLine();
        BeanUtils.copyPropertiesIgnoreNull(this, downloadLine);
        if(this.getCfybh() != null)
        {
            Fee fee = Ebean.find(Fee.class, UUID.fromString(this.getCfybh()));
            downloadLine.setFee(fee);
        }
        List<FeeDownloadMedTrack> medTrackList = new ArrayList<>();
        if(this.getYpzsmlb() != null)
            for(FeeDownloadInterfaceMedTrackResp medTrackDto: this.getYpzsmlb())
            {
                FeeDownloadMedTrack medTrack = new FeeDownloadMedTrack();
                medTrack.setYpzsm(medTrackDto.getYpzsm());
                medTrackList.add(medTrack);
            }
        downloadLine.setMedTrackList(medTrackList);
        return downloadLine;
    }
}
