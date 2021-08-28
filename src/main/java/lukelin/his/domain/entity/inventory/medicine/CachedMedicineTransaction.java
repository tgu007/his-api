package lukelin.his.domain.entity.inventory.medicine;

import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.OrderLineInterface;
import lukelin.his.domain.Interfaces.Inventory.PartialOrderLineInterface;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "inventory.cached_medicine_transaction")
public class CachedMedicineTransaction extends BaseCachedMedicineInventoryTransaction implements CachedTransactionInterface {

    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(nullable = false, name = "reason_Id")
    private UUID reasonId;

    public UUID getReasonId() {
        return reasonId;
    }

    public void setReasonId(UUID reasonId) {
        this.reasonId = reasonId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public void setInventoryEntity(InventoryEntityInterface inventoryEntity) {
        this.setMedicine((Medicine) inventoryEntity);
    }

    @Override
    public void setOriginPurchaseLine(OrderLineInterface orderLine) {
        this.setOriginPurchaseLine((MedicineOrderLine) orderLine);
    }

    public CachedMedicineTransaction createReverseTransaction() {
        CachedMedicineTransaction newTransaction = new CachedMedicineTransaction();
        newTransaction.setMedicine(this.getMedicine());
        newTransaction.setOriginPurchaseLine(this.getOriginPurchaseLine());
        newTransaction.setReasonId(this.reasonId);
        newTransaction.setWarehouse(this.getWarehouse());
        newTransaction.setMinUomQuantity(this.getMinUomQuantity().multiply(new BigDecimal(-1)));
        return newTransaction;
    }

    public OrderLineDetailReq toYBOrderLineDetailReq(SimpleDateFormat df, boolean isOut) {
        OrderLineDetailReq detailReq =  new OrderLineDetailReq();
        detailReq.setCMXXH(this.getUuid().toString());
        detailReq.setWZBH(this.getMedicine().getUploadResult().getServerCode());
        detailReq.setWZMC(this.getMedicine().getName());
        detailReq.setPCH(this.getOriginPurchaseLine().getBatchNumber());
        BigDecimal quantity = this.getMinUomQuantity();
        if(quantity.compareTo(BigDecimal.ZERO) < 0)
            quantity = quantity.multiply(new BigDecimal("-1"));
        if(isOut)
            detailReq.setZCSL(quantity.toString());
        else
            detailReq.setSRSL(quantity.toString());
        detailReq.setYXQZ(df.format(this.getOriginPurchaseLine().getExpireDate()));
        detailReq.setCLBZ("0");
        detailReq.setKCDW(this.getMedicine().getMinUom().getName());
        detailReq.setRKDJ(this.getOriginPurchaseLine().getMinUomCostPrice().toString());
        return detailReq;
    }
}
