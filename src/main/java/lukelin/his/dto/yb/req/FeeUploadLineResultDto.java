package lukelin.his.dto.yb.req;

import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.yb.FeeUploadResult;

import java.math.BigDecimal;
import java.util.UUID;

public class FeeUploadLineResultDto {
    private UUID cfybh;

    private String fybh;

    //医保属性
    private String ybsx;

    //自理比例
    private BigDecimal zlbl;

    //自理金额
    private BigDecimal zlje;

    //自费金额
    private BigDecimal zfje;

    //单价限额
    private BigDecimal djxe;

    //医保上传状态
    private String ybscbz;

    //附加信息
    private String fjxx;

    public UUID getCfybh() {
        return cfybh;
    }

    public void setCfybh(UUID cfybh) {
        this.cfybh = cfybh;
    }

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
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

    public String getFjxx() {
        return fjxx;
    }

    public void setFjxx(String fjxx) {
        this.fjxx = fjxx;
    }

    public FeeUploadResult toEntity() {
        FeeUploadResult result = new FeeUploadResult();
        result.setFee(new Fee(this.getCfybh()));
        result.setServerId(this.getFybh());
        result.setInsuranceAttribute(this.getYbsx());
        result.setSelfRatio(this.getZlbl());
        result.setSelfRatioPayAmount(this.getZlje());
        result.setSelfPayAmount(this.getZfje());
        result.setUnitAmountLimit(this.getDjxe());
        result.setUploadStatus(this.getYbscbz());
        result.setReference(this.getFjxx());
        return result;
    }
}
