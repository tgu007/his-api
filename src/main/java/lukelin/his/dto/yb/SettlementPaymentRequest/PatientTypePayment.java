package lukelin.his.dto.yb.SettlementPaymentRequest;

import java.math.BigDecimal;

public class PatientTypePayment {
    private BigDecimal civilTypePayment = BigDecimal.ZERO;

    private BigDecimal companyTypePayment = BigDecimal.ZERO;

    private BigDecimal otherTypePayment = BigDecimal.ZERO;

    private BigDecimal civilAdjustment = BigDecimal.ZERO;

    private BigDecimal companyAdjustment = BigDecimal.ZERO;

    private BigDecimal otherAdjustment = BigDecimal.ZERO;

    private BigDecimal totalPayment = BigDecimal.ZERO;

    public BigDecimal getCivilAdjustment() {
        return civilAdjustment;
    }

    public void setCivilAdjustment(BigDecimal civilAdjustment) {
        this.civilAdjustment = civilAdjustment;
    }

    public BigDecimal getCompanyAdjustment() {
        return companyAdjustment;
    }

    public void setCompanyAdjustment(BigDecimal companyAdjustment) {
        this.companyAdjustment = companyAdjustment;
    }

    public BigDecimal getOtherAdjustment() {
        return otherAdjustment;
    }

    public void setOtherAdjustment(BigDecimal otherAdjustment) {
        this.otherAdjustment = otherAdjustment;
    }

    public BigDecimal getCivilTypePayment() {
        return civilTypePayment;
    }

    public void setCivilTypePayment(BigDecimal civilTypePayment) {
        this.civilTypePayment = civilTypePayment;
    }

    public BigDecimal getCompanyTypePayment() {
        return companyTypePayment;
    }

    public void setCompanyTypePayment(BigDecimal companyTypePayment) {
        this.companyTypePayment = companyTypePayment;
    }

    public BigDecimal getOtherTypePayment() {
        return otherTypePayment;
    }

    public void setOtherTypePayment(BigDecimal otherTypePayment) {
        this.otherTypePayment = otherTypePayment;
    }

    public BigDecimal getTotalPayment() {
        return this.civilTypePayment.add(this.companyTypePayment).add(this.otherTypePayment);
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }
}
