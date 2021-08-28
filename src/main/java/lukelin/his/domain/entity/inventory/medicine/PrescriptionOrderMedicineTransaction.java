package lukelin.his.domain.entity.inventory.medicine;

import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.enums.Inventory.PrescriptionOrderTransactionStatus;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.Inventory.TransferTransactionStatus;

import javax.persistence.*;

@Entity
@Table(name = "inventory.prescription_order_medicine_transaction")
public class PrescriptionOrderMedicineTransaction extends BaseIdEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_order_line_id", nullable = false)
    private PrescriptionMedicineOrderLine prescriptionMedicineOrderLine;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private CachedMedicineTransaction medicineTransaction;

    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "status", nullable = false)
    private PrescriptionOrderTransactionStatus status;

    public PrescriptionMedicineOrderLine getPrescriptionMedicineOrderLine() {
        return prescriptionMedicineOrderLine;
    }

    public void setPrescriptionMedicineOrderLine(PrescriptionMedicineOrderLine prescriptionMedicineOrderLine) {
        this.prescriptionMedicineOrderLine = prescriptionMedicineOrderLine;
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

    public PrescriptionOrderTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionOrderTransactionStatus status) {
        this.status = status;
    }
}
