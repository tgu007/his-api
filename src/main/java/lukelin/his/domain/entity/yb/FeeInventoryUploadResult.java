package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.account.Fee;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "yb.fee_inventory_uploaded")
public class FeeInventoryUploadResult extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

}
