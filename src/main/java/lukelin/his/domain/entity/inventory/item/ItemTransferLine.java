package lukelin.his.domain.entity.inventory.item;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.TransferLineInterface;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.BaseTransferLine;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.inventory.medicine.TransferMedicineTransaction;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.Inventory.TransferTransactionStatus;
import lukelin.his.dto.Inventory.resp.item.ItemTransferLineRespDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "inventory.item_transfer_line")
public class ItemTransferLine extends BaseTransferLine implements TransferLineInterface {
    @ManyToOne
    @JoinColumn(name = "transfer_id")
    private ItemTransfer transfer;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "item_snapshot_id", nullable = false)
    private ItemSnapshot itemSnapshot;

    @OneToMany(mappedBy = "transferLine", cascade = CascadeType.ALL)
    private List<TransferItemTransaction> transferTransactionList;

    public List<TransferItemTransaction> getTransferTransactionList() {
        return transferTransactionList;
    }

    public void setTransferTransactionList(List<TransferItemTransaction> transferTransactionList) {
        this.transferTransactionList = transferTransactionList;
    }

    public ItemSnapshot getItemSnapshot() {
        return itemSnapshot;
    }

    public void setItemSnapshot(ItemSnapshot itemSnapshot) {
        this.itemSnapshot = itemSnapshot;
    }

    public ItemTransfer getTransfer() {
        return transfer;
    }

    public void setTransfer(ItemTransfer transfer) {
        this.transfer = transfer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


    @Override
    public ItemTransferLineRespDto toDto() {
        ItemTransferLineRespDto lineDto = new ItemTransferLineRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, lineDto);
        lineDto.setItem(this.getItemSnapshot().toDto());
        lineDto.setUom(this.getUom().toDto());
        return lineDto;
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getItem();
    }


    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedItemTransaction();
    }

    public void createTransferTransactionList(List<CachedTransactionInterface> newTransactionList, TransferTransactionStatus status, TransactionType transactionType) {
        List<TransferItemTransaction> transferItemTransactionList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : newTransactionList) {
            TransferItemTransaction transferItemTransaction = new TransferItemTransaction();
            transferItemTransaction.setItemTransaction((CachedItemTransaction) cachedTransactionInterface);
            transferItemTransaction.setStatus(status);
            transferItemTransaction.setTransactionType(transactionType);
            transferItemTransaction.setTransferLine(this);
            transferItemTransactionList.add(transferItemTransaction);
        }
        this.setTransferTransactionList(transferItemTransactionList);
        //return transferItemTransactionList;
    }

    public List<CachedTransactionInterface> generateTransferTransactionList(DepartmentWarehouse toWarehouse, TransactionType transactionType) {
        List<TransferItemTransaction> transferItemTransactionList = this.getTransferTransactionList().stream()
                .filter(t -> t.getStatus() == TransferTransactionStatus.pendingConfirm && t.getTransactionType() == TransactionType.transferOut).collect(Collectors.toList());
        List<CachedTransactionInterface> newTransactionList = new ArrayList<>();
        for (TransferItemTransaction transferItemTransaction : transferItemTransactionList) {
            //入库记录或者退还记录
            CachedItemTransaction cachedItemTransaction = transferItemTransaction.getItemTransaction().createReverseTransaction();
            cachedItemTransaction.setType(transactionType);
            cachedItemTransaction.setWarehouse(toWarehouse);
            newTransactionList.add(cachedItemTransaction);

            //更新出库记录状态
            transferItemTransaction.setStatus(TransferTransactionStatus.confirmed);
            Ebean.save(transferItemTransaction);
        }
        return newTransactionList;
    }
}
