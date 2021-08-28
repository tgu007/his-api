package lukelin.his.dto.yb.req.settlement;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.PreSettlement;
import lukelin.his.domain.entity.yb.PreSettlementFee;

import java.math.BigDecimal;
import java.util.UUID;

public class SettlementFeeSaveDto {
    private String id;

    private String fybh;

    private UUID cfybh;

    private String kpxm;

    private String kpxmmc;

    private BigDecimal xmje;

    private BigDecimal zlje;

    private BigDecimal zfje;

    private BigDecimal ybje;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
    }

    public UUID getCfybh() {
        return cfybh;
    }

    public void setCfybh(UUID cfybh) {
        this.cfybh = cfybh;
    }

    public String getKpxm() {
        return kpxm;
    }

    public void setKpxm(String kpxm) {
        this.kpxm = kpxm;
    }

    public String getKpxmmc() {
        return kpxmmc;
    }

    public void setKpxmmc(String kpxmmc) {
        this.kpxmmc = kpxmmc;
    }

    public BigDecimal getXmje() {
        return xmje;
    }

    public void setXmje(BigDecimal xmje) {
        this.xmje = xmje;
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

    public BigDecimal getYbje() {
        return ybje;
    }

    public void setYbje(BigDecimal ybje) {
        this.ybje = ybje;
    }

    public PreSettlementFee toEntity()
    {
        PreSettlementFee settlementFee = new PreSettlementFee();
        Fee fee = Ebean.find(Fee.class, this.getCfybh());
        settlementFee.setFee(fee);
        BeanUtils.copyPropertiesIgnoreNull(this, settlementFee);
        return settlementFee;
    }
}
