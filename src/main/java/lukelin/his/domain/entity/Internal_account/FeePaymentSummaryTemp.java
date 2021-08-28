package lukelin.his.domain.entity.Internal_account;

import io.ebean.annotation.View;

import javax.persistence.Column;
import java.math.BigDecimal;


@javax.persistence.Entity
@View(name = "internal_account.fee_payment_temp_summary")
public class FeePaymentSummaryTemp {
    @Column(name = "sign_in_number")
    private String singInNumber;

    @Column(name = "patient_info")
    private String patientInfo;

    @Column(name = "totalFeeAmount")
    private BigDecimal totalFeeAmount;

    @Column(name = "total_payment")
    private BigDecimal totalPaidAmount;

    @Column(name = "balance_amount")
    private BigDecimal balanceAmount;

    public String getSingInNumber() {
        return singInNumber;
    }

    public void setSingInNumber(String singInNumber) {
        this.singInNumber = singInNumber;
    }

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }

    public BigDecimal getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(BigDecimal totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
