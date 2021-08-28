package lukelin.his.domain.entity.inventory.medicine;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.BasePartialOrder;
import lukelin.his.domain.entity.yb.InventoryOrder;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.Inventory.resp.BasePartialOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicinePartialOrderListRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicinePartialOrderRespDto;
import lukelin.his.dto.yb.inventory.req.OrderHeaderReq;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;
import lukelin.his.dto.yb.inventory.req.OrderLineReq;
import lukelin.his.dto.yb.inventory.resp.OrderLineDetailResp;
import lukelin.his.dto.yb.inventory.resp.OrderLineResp;
import lukelin.his.dto.yb.inventory.resp.OrderSubmitLineResp;
import lukelin.his.dto.yb.inventory.resp.OrderSubmitResp;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@javax.persistence.Entity
@Table(name = "inventory.medicine_order_partial")
public class MedicinePartialOrder extends BasePartialOrder {
    @ManyToOne
    @JoinColumn(name = "master_order_id")
    private MedicineOrder masterOrder;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<MedicinePartialOrderLine> lineList;

    public MedicineOrder getMasterOrder() {
        return masterOrder;
    }

    public void setMasterOrder(MedicineOrder masterOrder) {
        this.masterOrder = masterOrder;
    }

    public List<MedicinePartialOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<MedicinePartialOrderLine> lineList) {
        this.lineList = lineList;
    }


    @Override
    public MedicinePartialOrderRespDto toDto() {
        MedicinePartialOrderRespDto orderDto = new MedicinePartialOrderRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, orderDto);
        orderDto.setOrderNumber(Utils.buildDisplayCode(this.getOrderNumber()));

        List<BasePartialOrderLineRespDto> lineList = new ArrayList<>();
        for (MedicinePartialOrderLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        orderDto.setLineList(lineList);
        orderDto.setMasterOrder(this.getMasterOrder().toListDto());
        Optional<InventoryOrder> optionalInventoryOrder = Ebean.find(InventoryOrder.class).where()
                .eq("hisId", this.getUuid())
                .eq("orderType", InventoryOrderType.buyOrder)
                .findOneOrEmpty();
        if(optionalInventoryOrder.isPresent())
        {
            InventoryOrder inventoryOrder = optionalInventoryOrder.get();
            orderDto.setYBOrderId(inventoryOrder.getYbId());
            orderDto.setYbImageId(inventoryOrder.getImageNumber());
        }
        return orderDto;
    }

    public MedicinePartialOrderListRespDto toListDto() {
        MedicinePartialOrderListRespDto dto = new MedicinePartialOrderListRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setOrderNumber(Utils.buildDisplayCode(this.getOrderNumber()));
        dto.setMasterOrder(this.getMasterOrder().toListDto());
        if (this.getApprovedBy() != null)
            dto.setApprovedBy(this.getApprovedBy().getName());
        return dto;
    }

    public OrderHeaderReq toYBOrderHeader() {
        MedicineOrder masterOrder = this.getMasterOrder();
        OrderHeaderReq req = new OrderHeaderReq();
        req.setCYWDH(this.getUuid().toString());
        req.setKFBH(masterOrder.getToWarehouse().getWarehouseUploaded().getServerCode());
        req.setYWDM(InventoryOrderType.buyOrder.toString());
        req.setYWBM(masterOrder.getToWarehouse().getDepartment().getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        req.setFSRQ(df.format(new Date()));
        req.setGYSMC(masterOrder.getSupplier().getName());
        return req;
    }

    public OrderLineReq toYBOrderLineDto() {
        OrderLineReq req = new OrderLineReq();
        req.setCYWDH(this.getUuid().toString());
        List<OrderLineDetailReq> lineDetailList = new ArrayList<>();
        for (MedicinePartialOrderLine line : this.getLineList())
            if (!line.getMasterOrderLine().getMedicine().getSelfPay())
                lineDetailList.add(line.toYBOrderLineDetailDto());
        req.setYWMXLB(lineDetailList);
        return req;
    }
}
