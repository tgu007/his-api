package lukelin.his.dto.yb.inventory.resp;

import lukelin.his.domain.entity.yb.InventoryOrder;
import lukelin.his.domain.enums.YB.InventoryOrderType;

import java.util.UUID;

public class OrderHeaderResp {
    private UUID CYWDH;

    private String YWDH;

    public UUID getCYWDH() {
        return CYWDH;
    }

    public void setCYWDH(UUID CYWDH) {
        this.CYWDH = CYWDH;
    }

    public String getYWDH() {
        return YWDH;
    }

    public void setYWDH(String YWDH) {
        this.YWDH = YWDH;
    }

    public InventoryOrder toInventoryOrderEntity(InventoryOrderType orderType) {
        InventoryOrder newOrder = new InventoryOrder();
        newOrder.setOrderType(orderType);
        newOrder.setHisId(this.getCYWDH());
        newOrder.setYbId(this.getYWDH());
        return newOrder;
    }
}
