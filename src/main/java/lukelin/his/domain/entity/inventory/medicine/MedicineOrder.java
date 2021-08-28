package lukelin.his.domain.entity.inventory.medicine;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.OrderInterface;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.inventory.BaseOrder;
import lukelin.his.domain.entity.yb.InventoryOrder;
import lukelin.his.domain.enums.Inventory.TransferStatus;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.Inventory.resp.BaseOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderListRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderRespDto;
import lukelin.his.dto.yb.inventory.req.OrderHeaderReq;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;
import lukelin.his.dto.yb.inventory.req.OrderLineReq;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "inventory.medicine_order")
public class MedicineOrder extends BaseOrder implements OrderInterface {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<MedicineOrderLine> lineList;

    @OneToMany(mappedBy = "masterOrder")
    private List<MedicinePartialOrder> partialOrderList;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private MedicineOrderRequest orderRequest;

    public List<MedicinePartialOrder> getPartialOrderList() {
        return partialOrderList;
    }

    public void setPartialOrderList(List<MedicinePartialOrder> partialOrderList) {
        this.partialOrderList = partialOrderList;
    }

    public MedicineOrder(UUID uuid) {
        this.setUuid(uuid);
    }

    public MedicineOrder() {

    }

    public MedicineOrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(MedicineOrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public List<MedicineOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<MedicineOrderLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public MedicineOrderRespDto toDto() {
        MedicineOrderRespDto orderDto = new MedicineOrderRespDto();
        super.setOrderDtoValue(orderDto);
        List<BaseOrderLineRespDto> lineList = new ArrayList<>();
        for (MedicineOrderLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        orderDto.setLineList(lineList);

        if (this.getOrderRequest() != null)
            orderDto.setOrderRequest(this.getOrderRequest().toListDto());

        if (this.isReturnOrder()) {
            Optional<InventoryOrder> optionalInventoryOrder = Ebean.find(InventoryOrder.class).where()
                    .eq("hisId", this.getUuid())
                    .eq("orderType", InventoryOrderType.buyReturn)
                    .findOneOrEmpty();
            optionalInventoryOrder.ifPresent(inventoryOrder -> orderDto.setYBOrderId(inventoryOrder.getYbId()));
        }
        return orderDto;
    }

    public MedicineOrderListRespDto toListDto() {
        MedicineOrderListRespDto dto = new MedicineOrderListRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setOrderNumber(Utils.buildDisplayCode(this.getOrderNumber()));
        if (this.getSupplier() != null)
            dto.setSupplier(this.getSupplier().getName());
        if (this.getApprovedBy() != null)
            dto.setApprovedBy(this.getApprovedBy().getName());
        if (this.getOrderRequest() != null)
            dto.setOrderRequest(this.getOrderRequest().toListDto());
        dto.setToWarehouse(this.getToWarehouse().toDto());
        return dto;
    }

    public OrderHeaderReq toYBOrderHeader() {
        OrderHeaderReq req = new OrderHeaderReq();
        req.setCYWDH(this.getUuid().toString());
        req.setKFBH(this.getToWarehouse().getWarehouseUploaded().getServerCode());
        req.setYWDM(InventoryOrderType.buyReturn.toString());
        req.setYWBM(this.getToWarehouse().getDepartment().getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        req.setFSRQ(df.format(new Date()));
        req.setGYSMC(this.getSupplier().getName());
        return req;
    }

    public OrderLineReq toYBOrderLineDto() {
        OrderLineReq req = new OrderLineReq();
        req.setCYWDH(this.getUuid().toString());
        List<OrderLineDetailReq> lineDetailList = new ArrayList<>();
        for (MedicineOrderLine line : this.getLineList())
            if (!line.getMedicine().getSelfPay())
                lineDetailList.add(line.toYBOrderLineDetailDto());
        req.setYWMXLB(lineDetailList);
        return req;
    }


//    public MedicineTransfer generateTransfer(DepartmentWarehouse toWarehouse) {
//        MedicineTransfer transfer = new MedicineTransfer();
//        transfer.setFromWarehouse(this.getToWarehouse());
//        transfer.setReference(this.getReference());
//        transfer.setToWarehouse(toWarehouse);
//        transfer.setTransferDate(new Date());
//        transfer.setTransferStatus(TransferStatus.created);
//        List<MedicineTransferLine> transferLineList = new ArrayList<>();
//        for (MedicineOrderLine orderLine : this.getLineList())
//            transferLineList.add(orderLine.generateTransferLine());
//        transfer.setLineList(transferLineList);
//        return transfer;
//    }
}
