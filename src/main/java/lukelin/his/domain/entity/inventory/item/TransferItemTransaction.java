package lukelin.his.domain.entity.inventory.item;

import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.entity.inventory.medicine.CachedMedicineTransaction;
import lukelin.his.domain.entity.inventory.medicine.MedicineTransferLine;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.Inventory.TransferTransactionStatus;

import javax.persistence.*;

@Entity
@Table(name = "inventory.transfer_item_transaction")
public class TransferItemTransaction extends BaseIdEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transfer_line_id", nullable = false)
    private ItemTransferLine transferLine;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private CachedItemTransaction itemTransaction;

    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "status", nullable = false)
    private TransferTransactionStatus status;

    public ItemTransferLine getTransferLine() {
        return transferLine;
    }

    public void setTransferLine(ItemTransferLine transferLine) {
        this.transferLine = transferLine;
    }

    public CachedItemTransaction getItemTransaction() {
        return itemTransaction;
    }

    public void setItemTransaction(CachedItemTransaction itemTransaction) {
        this.itemTransaction = itemTransaction;
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
