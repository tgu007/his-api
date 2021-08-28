package lukelin.his.domain.entity.account;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "account.invoice")
public class Invoice extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "ybjsh")
    private String ybjsh;

    @Column(name = "yb_type")
    private String ybType;

    @Column(name = "invoice_year")
    private String invoiceYear;

    @Column(name = "invoice_month")
    private String invoiceMonth;

    @Column(name = "invoice_day")
    private String invoiceDay;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "work_unit")
    private String workUnit;

    @Column(name = "sign_in_number")
    private String signInNumber;

    @Column(name = "sign_in_date")
    private String signInDate;

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

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Column(name = "remaining_payment")
    private BigDecimal remainingPayment;

    @Column(name = "refund")
    private BigDecimal refund;

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    public String getYbjsh() {
        return ybjsh;
    }

    public void setYbjsh(String ybjsh) {
        this.ybjsh = ybjsh;
    }

    public String getYbType() {
        return ybType;
    }

    public void setYbType(String ybType) {
        this.ybType = ybType;
    }

    public String getInvoiceYear() {
        return invoiceYear;
    }

    public void setInvoiceYear(String invoiceYear) {
        this.invoiceYear = invoiceYear;
    }

    public String getInvoiceMonth() {
        return invoiceMonth;
    }

    public void setInvoiceMonth(String invoiceMonth) {
        this.invoiceMonth = invoiceMonth;
    }

    public String getInvoiceDay() {
        return invoiceDay;
    }

    public void setInvoiceDay(String invoiceDay) {
        this.invoiceDay = invoiceDay;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getSignInNumber() {
        return signInNumber;
    }

    public void setSignInNumber(String signInNumber) {
        this.signInNumber = signInNumber;
    }

    public String getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(String signInDate) {
        this.signInDate = signInDate;
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(BigDecimal remainingPayment) {
        this.remainingPayment = remainingPayment;
    }

    public BigDecimal getRefund() {
        return refund;
    }

    public void setRefund(BigDecimal refund) {
        this.refund = refund;
    }
}
