package lukelin.his.domain.entity.account;

import io.ebean.annotation.View;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

@javax.persistence.Entity
@View(name = "account.fee_summary")
public class ViewFeeSummary {
    @Column(name = "patient_sign_in_id")
    private UUID patientSignInId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "pending_fee_amount")
    private BigDecimal pendingFeeAmount = BigDecimal.ZERO;

    @Column(name = "total_self_amount")
    private BigDecimal totalSelfPayAmount = BigDecimal.ZERO;

    @Column(name = "covered_fee_amount")
    private BigDecimal coveredFeeAmount = BigDecimal.ZERO;

    @Column(name = "self_zl_amount")
    private BigDecimal selfRatioAmount = BigDecimal.ZERO;

    @Column(name = "self_zf_amount")
    private BigDecimal selfPayAmount = BigDecimal.ZERO;

    public BigDecimal getSelfRatioAmount() {
        return selfRatioAmount;
    }

    public void setSelfRatioAmount(BigDecimal selfRatioAmount) {
        this.selfRatioAmount = selfRatioAmount;
    }

    public BigDecimal getSelfPayAmount() {
        return selfPayAmount;
    }

    public void setSelfPayAmount(BigDecimal selfPayAmount) {
        this.selfPayAmount = selfPayAmount;
    }

    public BigDecimal getPendingFeeAmount() {
        return pendingFeeAmount;
    }

    public void setPendingFeeAmount(BigDecimal pendingFeeAmount) {
        this.pendingFeeAmount = pendingFeeAmount;
    }

    public BigDecimal getTotalSelfPayAmount() {
        return totalSelfPayAmount;
    }

    public void setTotalSelfPayAmount(BigDecimal totalSelfPayAmount) {
        this.totalSelfPayAmount = totalSelfPayAmount;
    }

    public BigDecimal getCoveredFeeAmount() {
        return coveredFeeAmount;
    }

    public void setCoveredFeeAmount(BigDecimal coveredFeeAmount) {
        this.coveredFeeAmount = coveredFeeAmount;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
