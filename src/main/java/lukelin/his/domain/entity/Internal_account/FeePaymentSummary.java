package lukelin.his.domain.entity.Internal_account;

import io.ebean.annotation.View;
import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.internal_account.FeePaymentSummaryRespDto;
import lukelin.his.dto.internal_account.FeeRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;


@javax.persistence.Entity
@View(name = "internal_account.fee_payment_summary")
public class FeePaymentSummary implements DtoConvertible<FeePaymentSummaryRespDto> {
    @OneToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "totalFeeAmount")
    private BigDecimal totalFeeAmount;

    @Column(name = "total_payment")
    private BigDecimal totalPaidAmount;

    @Column(name = "balance_amount")
    private BigDecimal balanceAmount;


    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
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

    @Override
    public FeePaymentSummaryRespDto toDto() {
        FeePaymentSummaryRespDto dto = DtoUtils.convertRawDto(this);
        dto.setPatient(this.getPatientSignIn().toDto());
        return dto;
    }
}
