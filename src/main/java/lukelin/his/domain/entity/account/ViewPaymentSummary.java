package lukelin.his.domain.entity.account;

import io.ebean.annotation.View;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

@javax.persistence.Entity
@View(name = "account.payment_summary")
public class ViewPaymentSummary {
    @Column(name = "sign_in_id")
    private UUID patientSignInId;

    @Column(name = "total_payment")
    private BigDecimal totalPayment = BigDecimal.ZERO;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }
}
