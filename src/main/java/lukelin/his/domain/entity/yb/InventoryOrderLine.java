package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.enums.YB.InventoryOrderType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "yb.inventory_order_line")
public class InventoryOrderLine extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private InventoryOrder order;

    @Column(name = "stock_number")
    private String stockNumber;

    @Column(name = "yb_id")
    private String ybId;

    @Column(name = "his_id")
    private UUID hisId;

    public InventoryOrder getOrder() {
        return order;
    }

    public void setOrder(InventoryOrder order) {
        this.order = order;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getYbId() {
        return ybId;
    }

    public void setYbId(String ybId) {
        this.ybId = ybId;
    }

    public UUID getHisId() {
        return hisId;
    }

    public void setHisId(UUID hisId) {
        this.hisId = hisId;
    }
}
