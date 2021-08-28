package lukelin.his.domain.entity.yb;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "yb.settlement_pre_fee")
public class PreSettlementFee extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "settlement_id", nullable = false)
    private PreSettlement preSettlement;

    @OneToOne
    @JoinColumn(name = "cfybh", nullable = false)
    private Fee fee;

    private String id;

    private String fybh;

    private String kpxm;

    private String kpxmmc;

    private BigDecimal xmje;

    private BigDecimal zlje;

    private BigDecimal zfje;

    private BigDecimal ybje;

    public PreSettlement getPreSettlement() {
        return preSettlement;
    }

    public void setPreSettlement(PreSettlement preSettlement) {
        this.preSettlement = preSettlement;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

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


}
