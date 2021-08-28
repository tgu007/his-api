package lukelin.his.domain.entity.yb.drg;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.sign_out_reason_mapping")
public class SignOutReasonMapping extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "drg_id")
    private SignOutReason signOutReason;


    @OneToOne
    @JoinColumn(name = "his_id")
    private Dictionary  hisSignOutReason;

    public SignOutReason getSignOutReason() {
        return signOutReason;
    }

    public void setSignOutReason(SignOutReason signOutReason) {
        this.signOutReason = signOutReason;
    }

    public Dictionary getHisSignOutReason() {
        return hisSignOutReason;
    }

    public void setHisSignOutReason(Dictionary hisSignOutReason) {
        this.hisSignOutReason = hisSignOutReason;
    }
}
