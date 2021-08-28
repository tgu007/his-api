package lukelin.his.domain.entity.account;

import lukelin.his.domain.entity.BaseEntity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "account.auto_fee_change_log")
public class AutoFeeChangeLog extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "auto_fee_id", nullable = false)
    private AutoFee autoFee;

    @Column(nullable = false)
    private String action;

    public AutoFee getAutoFee() {
        return autoFee;
    }

    public void setAutoFee(AutoFee autoFee) {
        this.autoFee = autoFee;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
