package lukelin.his.dto.yb.SettlementPaymentRequest;

import java.math.BigDecimal;

public class SettlementAccount {
    public SettlementAccount(String accountCode, String accountName) {
        this.accountCode = accountCode;
        this.accountName = accountName;
        retiredPayment = new PatientTypePayment();
        workingPayment = new PatientTypePayment();
    }

    private String accountCode;

    private String accountName;

    private PatientTypePayment retiredPayment;

    private PatientTypePayment workingPayment;

    private BigDecimal totalPayment = BigDecimal.ZERO;



    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public PatientTypePayment getRetiredPayment() {
        return retiredPayment;
    }

    public void setRetiredPayment(PatientTypePayment retiredPayment) {
        this.retiredPayment = retiredPayment;
    }

    public PatientTypePayment getWorkingPayment() {
        return workingPayment;
    }

    public void setWorkingPayment(PatientTypePayment workingPayment) {
        this.workingPayment = workingPayment;
    }

    public BigDecimal getTotalPayment() {
        return this.retiredPayment.getTotalPayment().add(this.workingPayment.getTotalPayment());
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }
}
