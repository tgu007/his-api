package lukelin.his.domain.entity.inventory.medicine;


import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.entity.account.Fee;

import javax.persistence.*;

@Entity
@Table(name = "inventory.fee_medicine_transaction")
public class FeeMedicineTransaction extends BaseIdEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private CachedMedicineTransaction medicineTransaction;

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public CachedMedicineTransaction getMedicineTransaction() {
        return medicineTransaction;
    }

    public void setMedicineTransaction(CachedMedicineTransaction medicineTransaction) {
        this.medicineTransaction = medicineTransaction;
    }
}
