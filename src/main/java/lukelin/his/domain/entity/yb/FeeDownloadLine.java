package lukelin.his.domain.entity.yb;


import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.dto.yb.resp.FeeDownLoadLineRespDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@javax.persistence.Entity
@Table(name = "yb.fee_download_line")
public class FeeDownloadLine extends BaseEntity implements DtoConvertible<FeeDownLoadLineRespDto> {
    @ManyToOne
    @JoinColumn(name = "fee_download_id", nullable = false)
    private FeeDownload feeDownload;

    @OneToOne()
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @OneToMany(mappedBy = "feeDownloadLine", cascade = CascadeType.ALL)
    private List<FeeDownloadMedTrack> medTrackList;

    @Column(name = "check_message")
    private String validationMessage;

    @Column(name = "check_valid")
    private Boolean isValid;

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    private String fybh;

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

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
    }

    public FeeDownload getFeeDownload() {
        return feeDownload;
    }

    public void setFeeDownload(FeeDownload feeDownload) {
        this.feeDownload = feeDownload;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public List<FeeDownloadMedTrack> getMedTrackList() {
        return medTrackList;
    }

    public void setMedTrackList(List<FeeDownloadMedTrack> medTrackList) {
        this.medTrackList = medTrackList;
    }

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

    @Override
    public FeeDownLoadLineRespDto toDto() {
        FeeDownLoadLineRespDto dto = DtoUtils.convertRawDto(this);
        if (this.getFee() != null)
            dto.setFee(this.getFee().toListDto());
        return dto;
    }
}
