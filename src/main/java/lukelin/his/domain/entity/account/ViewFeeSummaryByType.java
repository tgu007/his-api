package lukelin.his.domain.entity.account;

import io.ebean.annotation.View;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

@javax.persistence.Entity
@View(name = "account.fee_summary_by_type")
public class ViewFeeSummaryByType {
    @Column(name = "patient_sign_in_id")
    private UUID patientSignInId;

    @Column(name = "fee_xy")
    private BigDecimal feeXY;

    @Column(name = "fee_xy_self")
    private BigDecimal feeXYSelf;

    @Column(name = "fee_zc")
    private BigDecimal feeZC;

    @Column(name = "fee_zc_self")
    private BigDecimal feeZCSelf;

    @Column(name = "fee_zcy")
    private BigDecimal feeZCY;

    @Column(name = "fee_zcy_self")
    private BigDecimal feeZCYSelf;

    @Column(name = "fee_cw")
    private BigDecimal feeCW;

    @Column(name = "fee_cw_self")
    private BigDecimal feeCWSelf;

    @Column(name = "fee_zhencha")
    private BigDecimal feeZhenCha;

    @Column(name = "fee_zhencha_self")
    private BigDecimal feeZhenChaSelf;

    @Column(name = "fee_hy")
    private BigDecimal feeHY;

    @Column(name = "fee_hy_self")
    private BigDecimal feeHYSelf;

    @Column(name = "fee_zl")
    private BigDecimal feeZL;

    @Column(name = "fee_zl_self")
    private BigDecimal feeZLSelf;

    @Column(name = "fee_hl")
    private BigDecimal feeHL;

    @Column(name = "fee_hl_self")
    private BigDecimal feeHLSelf;

    @Column(name = "fee_hc")
    private BigDecimal feeHC;

    @Column(name = "fee_hc_self")
    private BigDecimal feeHCSelf;

    @Column(name = "fee_zhenliao")
    private BigDecimal feeZhenliao;

    @Column(name = "fee_zhenliao_self")
    private BigDecimal feeZhenliaoSelf;

    @Column(name = "fee_qt")
    private BigDecimal feeQT;

    @Column(name = "fee_qt_self")
    private BigDecimal feeQTSelf;

    @Column(name = "total_amount")
    private BigDecimal feeTotal;

    @Column(name = "total_amount_slef")
    private BigDecimal feeTotalSelf;

    @Column(name = "fee_sy")
    private BigDecimal feeShuYang;

    @Column(name = "fee_sy_self")
    private BigDecimal feeShuYangSelf;

    public BigDecimal getFeeShuYang() {
        return feeShuYang;
    }

    public void setFeeShuYang(BigDecimal feeShuYang) {
        this.feeShuYang = feeShuYang;
    }

    public BigDecimal getFeeShuYangSelf() {
        return feeShuYangSelf;
    }

    public void setFeeShuYangSelf(BigDecimal feeShuYangSelf) {
        this.feeShuYangSelf = feeShuYangSelf;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public BigDecimal getFeeXY() {
        return feeXY;
    }

    public void setFeeXY(BigDecimal feeXY) {
        this.feeXY = feeXY;
    }

    public BigDecimal getFeeXYSelf() {
        return feeXYSelf;
    }

    public void setFeeXYSelf(BigDecimal feeXYSelf) {
        this.feeXYSelf = feeXYSelf;
    }

    public BigDecimal getFeeZC() {
        return feeZC;
    }

    public void setFeeZC(BigDecimal feeZC) {
        this.feeZC = feeZC;
    }

    public BigDecimal getFeeZCSelf() {
        return feeZCSelf;
    }

    public void setFeeZCSelf(BigDecimal feeZCSelf) {
        this.feeZCSelf = feeZCSelf;
    }

    public BigDecimal getFeeZCY() {
        return feeZCY;
    }

    public void setFeeZCY(BigDecimal feeZCY) {
        this.feeZCY = feeZCY;
    }

    public BigDecimal getFeeZCYSelf() {
        return feeZCYSelf;
    }

    public void setFeeZCYSelf(BigDecimal feeZCYSelf) {
        this.feeZCYSelf = feeZCYSelf;
    }

    public BigDecimal getFeeCW() {
        return feeCW;
    }

    public void setFeeCW(BigDecimal feeCW) {
        this.feeCW = feeCW;
    }

    public BigDecimal getFeeCWSelf() {
        return feeCWSelf;
    }

    public void setFeeCWSelf(BigDecimal feeCWSelf) {
        this.feeCWSelf = feeCWSelf;
    }

    public BigDecimal getFeeZhenCha() {
        return feeZhenCha;
    }

    public void setFeeZhenCha(BigDecimal feeZhenCha) {
        this.feeZhenCha = feeZhenCha;
    }

    public BigDecimal getFeeZhenChaSelf() {
        return feeZhenChaSelf;
    }

    public void setFeeZhenChaSelf(BigDecimal feeZhenChaSelf) {
        this.feeZhenChaSelf = feeZhenChaSelf;
    }

    public BigDecimal getFeeHY() {
        return feeHY;
    }

    public void setFeeHY(BigDecimal feeHY) {
        this.feeHY = feeHY;
    }

    public BigDecimal getFeeHYSelf() {
        return feeHYSelf;
    }

    public void setFeeHYSelf(BigDecimal feeHYSelf) {
        this.feeHYSelf = feeHYSelf;
    }

    public BigDecimal getFeeZL() {
        return feeZL;
    }

    public void setFeeZL(BigDecimal feeZL) {
        this.feeZL = feeZL;
    }

    public BigDecimal getFeeZLSelf() {
        return feeZLSelf;
    }

    public void setFeeZLSelf(BigDecimal feeZLSelf) {
        this.feeZLSelf = feeZLSelf;
    }

    public BigDecimal getFeeHL() {
        return feeHL;
    }

    public void setFeeHL(BigDecimal feeHL) {
        this.feeHL = feeHL;
    }

    public BigDecimal getFeeHLSelf() {
        return feeHLSelf;
    }

    public void setFeeHLSelf(BigDecimal feeHLSelf) {
        this.feeHLSelf = feeHLSelf;
    }

    public BigDecimal getFeeHC() {
        return feeHC;
    }

    public void setFeeHC(BigDecimal feeHC) {
        this.feeHC = feeHC;
    }

    public BigDecimal getFeeHCSelf() {
        return feeHCSelf;
    }

    public void setFeeHCSelf(BigDecimal feeHCSelf) {
        this.feeHCSelf = feeHCSelf;
    }

    public BigDecimal getFeeZhenliao() {
        return feeZhenliao;
    }

    public void setFeeZhenliao(BigDecimal feeZhenliao) {
        this.feeZhenliao = feeZhenliao;
    }

    public BigDecimal getFeeZhenliaoSelf() {
        return feeZhenliaoSelf;
    }

    public void setFeeZhenliaoSelf(BigDecimal feeZhenliaoSelf) {
        this.feeZhenliaoSelf = feeZhenliaoSelf;
    }

    public BigDecimal getFeeQT() {
        return feeQT;
    }

    public void setFeeQT(BigDecimal feeQT) {
        this.feeQT = feeQT;
    }

    public BigDecimal getFeeQTSelf() {
        return feeQTSelf;
    }

    public void setFeeQTSelf(BigDecimal feeQTSelf) {
        this.feeQTSelf = feeQTSelf;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }

    public BigDecimal getFeeTotalSelf() {
        return feeTotalSelf;
    }

    public void setFeeTotalSelf(BigDecimal feeTotalSelf) {
        this.feeTotalSelf = feeTotalSelf;
    }
}
