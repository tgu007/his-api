package lukelin.his.domain.entity.inventory.medicine;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.TransferLineInterface;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.inventory.BaseTransferLine;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.TransactionType;
import lukelin.his.domain.enums.Inventory.TransferTransactionStatus;
import lukelin.his.dto.Inventory.resp.medicine.MedicineTransferLineRespDto;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "inventory.medicine_transfer_line")
public class MedicineTransferLine extends BaseTransferLine implements TransferLineInterface {
    @ManyToOne
    @JoinColumn(name = "transfer_id")
    private MedicineTransfer transfer;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "medicine_snapshot_id", nullable = false)
    private MedicineSnapshot medicineSnapshot;

    @OneToMany(mappedBy = "transferLine", cascade = CascadeType.ALL)
    private List<TransferMedicineTransaction> transferTransactionList;

    public List<TransferMedicineTransaction> getTransferTransactionList() {
        return transferTransactionList;
    }

    public void setTransferTransactionList(List<TransferMedicineTransaction> transferTransactionList) {
        this.transferTransactionList = transferTransactionList;
    }

    public MedicineSnapshot getMedicineSnapshot() {
        return medicineSnapshot;
    }

    public void setMedicineSnapshot(MedicineSnapshot medicineSnapshot) {
        this.medicineSnapshot = medicineSnapshot;
    }


    public MedicineTransfer getTransfer() {
        return transfer;
    }

    public void setTransfer(MedicineTransfer transfer) {
        this.transfer = transfer;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }


    @Override
    public MedicineTransferLineRespDto toDto() {
        MedicineTransferLineRespDto lineDto = new MedicineTransferLineRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, lineDto);
        lineDto.setMedicine(this.getMedicineSnapshot().toDto());
        lineDto.setUom(this.getUom().toDto());
        return lineDto;
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getMedicine();
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedMedicineTransaction();
    }

    public void createTransferTransactionList(List<CachedTransactionInterface> newTransactionList, TransferTransactionStatus status, TransactionType transactionType) {
        List<TransferMedicineTransaction> transferMedicineTransactionList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : newTransactionList) {
            TransferMedicineTransaction transferMedicineTransaction = new TransferMedicineTransaction();
            transferMedicineTransaction.setMedicineTransaction((CachedMedicineTransaction) cachedTransactionInterface);
            transferMedicineTransaction.setStatus(status);
            transferMedicineTransaction.setTransactionType(transactionType);
            transferMedicineTransaction.setTransferLine(this);
            transferMedicineTransactionList.add(transferMedicineTransaction);
        }
        this.setTransferTransactionList(transferMedicineTransactionList);
    }

    public List<CachedTransactionInterface> generateTransferTransactionList(DepartmentWarehouse toWarehouse, TransactionType transactionType) {
        List<TransferMedicineTransaction> transferMedicineTransactionList = this.getTransferTransactionList().stream()
                .filter(t -> t.getStatus() == TransferTransactionStatus.pendingConfirm && t.getTransactionType() == TransactionType.transferOut).collect(Collectors.toList());
        List<CachedTransactionInterface> newTransactionList = new ArrayList<>();
        for (TransferMedicineTransaction transferMedicineTransaction : transferMedicineTransactionList) {
            //入库记录或者退还记录
            CachedMedicineTransaction cachedMedicineTransaction = transferMedicineTransaction.getMedicineTransaction().createReverseTransaction();
            cachedMedicineTransaction.setType(transactionType);
            cachedMedicineTransaction.setWarehouse(toWarehouse);
            newTransactionList.add(cachedMedicineTransaction);

            //更新出库记录状态
            transferMedicineTransaction.setStatus(TransferTransactionStatus.confirmed);
            Ebean.save(transferMedicineTransaction);
        }
        return newTransactionList;
    }

    public List<OrderLineDetailReq> toYBOrderLineDetailDto() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        //BigDecimal minUomQuantity = this.getMedicine().calculateUomQuantity(this.getUom(), this.getQuantity());
        List<OrderLineDetailReq> orderLineDetailReqList = new ArrayList<>();
        for(TransferMedicineTransaction transferMedicineTransaction : this.getTransferTransactionList())
        {
            OrderLineDetailReq req = new OrderLineDetailReq();
            req.setCMXXH(transferMedicineTransaction.getMedicineTransaction().getUuid().toString());
            req.setWZBH(this.getMedicine().getUploadResult().getServerCode());
            req.setWZMC(this.getMedicine().getName());
            req.setPCH(transferMedicineTransaction.getMedicineTransaction().getOriginPurchaseLine().getBatchNumber());
            BigDecimal quantity = transferMedicineTransaction.getMedicineTransaction().getMinUomQuantity().multiply(new BigDecimal("-1"));
            if(this.getTransfer().getFromWarehouse().getWarehouseType() == WarehouseType.pharmacy) {
                req.setZCSL(quantity.toString());
            }
            else {
                req.setSRSL(quantity.toString());
            }
            req.setYXQZ(df.format(transferMedicineTransaction.getMedicineTransaction().getOriginPurchaseLine().getExpireDate()));
            req.setCLBZ("0");
            req.setKCDW(this.getMedicine().getMinUom().getName());
            req.setRKDJ(transferMedicineTransaction.getMedicineTransaction().getOriginPurchaseLine().getMinUomCostPrice().toString());
            orderLineDetailReqList.add(req);
        }
        return orderLineDetailReqList;
    }
}
