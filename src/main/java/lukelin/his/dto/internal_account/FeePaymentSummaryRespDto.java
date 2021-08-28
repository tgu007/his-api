package lukelin.his.dto.internal_account;

import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.signin.response.PatientSignInRespDto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class FeePaymentSummaryRespDto {
    private PatientSignInRespDto patient;

    private BigDecimal totalFeeAmount;

    private BigDecimal totalPaidAmount;

    private BigDecimal balanceAmount;

    public PatientSignInRespDto getPatient() {
        return patient;
    }

    public void setPatient(PatientSignInRespDto patient) {
        this.patient = patient;
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
