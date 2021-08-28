package lukelin.his.domain.entity.account;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.Fee.FeeStatusAction;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "account.fee_change_log")
public class FeeChangeLog extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @Column(nullable = false)
    private FeeStatusAction action;

    private String reason;

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public FeeStatusAction getAction() {
        return action;
    }

    public void setAction(FeeStatusAction action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
