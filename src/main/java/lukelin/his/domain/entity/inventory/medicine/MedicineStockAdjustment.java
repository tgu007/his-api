package lukelin.his.domain.entity.inventory.medicine;


import lukelin.his.domain.Interfaces.Inventory.AdjustmentInterface;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.entity.inventory.BaseStockAdjustment;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.yb.inventory.req.OrderHeaderReq;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;
import lukelin.his.dto.yb.inventory.req.OrderLineReq;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Table(name = "inventory.medicine_stock_adjustment")
public class MedicineStockAdjustment extends BaseStockAdjustment implements AdjustmentInterface {
    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedMedicineTransaction();
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getMedicine();
    }

    public OrderHeaderReq toYBOrderHeader() {
        OrderHeaderReq req = new OrderHeaderReq();
        req.setCYWDH(this.getUuid().toString());
        req.setKFBH(this.getWarehouse().getWarehouseUploaded().getServerCode());
        req.setYWDM(InventoryOrderType.adjust.toString());
        req.setYWBM(this.getWarehouse().getDepartment().getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        req.setFSRQ(df.format(new Date()));
        req.setGYSMC("不适用");
        return req;
    }

    public OrderLineReq toYBOrderLineDto(List<CachedTransactionInterface> adjustmentTransactionList) {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        OrderLineReq req = new OrderLineReq();
        req.setCYWDH(this.getUuid().toString());
        List<OrderLineDetailReq> lineDetailList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : adjustmentTransactionList)
        {
            CachedMedicineTransaction medicineTransaction = (CachedMedicineTransaction)cachedTransactionInterface;
            boolean isOut = this.getOldQuantity().compareTo(this.getNewQuantity()) > 0;
            OrderLineDetailReq detailReq = medicineTransaction.toYBOrderLineDetailReq(df, isOut);
            lineDetailList.add(detailReq);
        }
        req.setYWMXLB(lineDetailList);
        return req;

    }
}
