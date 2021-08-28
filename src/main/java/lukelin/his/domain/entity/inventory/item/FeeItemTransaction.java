package lukelin.his.domain.entity.inventory.item;


import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.entity.account.Fee;

import javax.persistence.*;

@Entity
@Table(name = "inventory.fee_item_transaction")
public class FeeItemTransaction extends BaseIdEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private CachedItemTransaction itemTransaction;


    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public CachedItemTransaction getItemTransaction() {
        return itemTransaction;
    }

    public void setItemTransaction(CachedItemTransaction itemTransaction) {
        this.itemTransaction = itemTransaction;
    }
}
