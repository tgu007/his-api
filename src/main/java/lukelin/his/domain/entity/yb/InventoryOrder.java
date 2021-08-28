package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrderLine;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.yb.inventory.resp.OrderLineDetailResp;
import lukelin.his.dto.yb.inventory.resp.OrderLineResp;
import lukelin.his.dto.yb.inventory.resp.OrderSubmitLineResp;
import lukelin.his.dto.yb.inventory.resp.OrderSubmitResp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

@javax.persistence.Entity
@Table(name = "yb.inventory_order")
public class InventoryOrder extends BaseEntity {

    @Column(name = "order_type")
    private InventoryOrderType orderType;

    @Column(name = "yb_id")
    private String ybId;

    @Column(name = "his_id")
    private UUID hisId;

    @Column(name = "image_id")
    private String imageNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<InventoryOrderLine> lineList;

    public String getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(String imageNumber) {
        this.imageNumber = imageNumber;
    }

    public InventoryOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(InventoryOrderType orderType) {
        this.orderType = orderType;
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

    public List<InventoryOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<InventoryOrderLine> lineList) {
        this.lineList = lineList;
    }

    public void createLineList(OrderLineResp lineResp) {

        List<InventoryOrderLine> newList = new ArrayList<>();
        for(OrderLineDetailResp lineDetail: lineResp.getYWMXLB())
        {
            InventoryOrderLine newLine = new InventoryOrderLine();
            newLine.setHisId(lineDetail.getCMXXH());
            newLine.setYbId(lineDetail.getMXXH());
            newList.add(newLine);
        }
        this.setLineList(newList);
    }

    public void updateLineYBStockNumber(OrderSubmitResp resp) {
        InventoryOrderLine orderLine;
        for(OrderSubmitLineResp lineDetail: resp.getMxxhlb())
        {
            orderLine = this.getLineList().stream().filter(l -> l.getYbId().equals(lineDetail.getMxxh())).findFirst().get();
            orderLine.setStockNumber(lineDetail.getKcbh());
        }
    }
}
