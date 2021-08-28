package lukelin.his.domain.entity.inventory.medicine;

import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.Inventory.TransferTransactionStatus;

import javax.persistence.*;

@Entity
@Table(name = "inventory.transfer_medicine_transaction")
public class TransferMedicineTransaction extends BaseIdEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transfer_line_id", nullable = false)
    private MedicineTransferLine transferLine;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private CachedMedicineTransaction medicineTransaction;

    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "status", nullable = false)
    private TransferTransactionStatus status;

    public MedicineTransferLine getTransferLine() {
        return transferLine;
    }

    public void setTransferLine(MedicineTransferLine transferLine) {
        this.transferLine = transferLine;
    }

    public CachedMedicineTransaction getMedicineTransaction() {
        return medicineTransaction;
    }

    public void setMedicineTransaction(CachedMedicineTransaction medicineTransaction) {
        this.medicineTransaction = medicineTransaction;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransferTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransferTransactionStatus status) {
        this.status = status;
    }
}
