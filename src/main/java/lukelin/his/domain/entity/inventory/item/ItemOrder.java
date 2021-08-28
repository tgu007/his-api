package lukelin.his.domain.entity.inventory.item;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.OrderInterface;
import lukelin.his.domain.entity.inventory.BaseOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrder;
import lukelin.his.dto.Inventory.resp.BaseOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRespDto;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "inventory.item_order")
public class ItemOrder extends BaseOrder implements OrderInterface {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ItemOrderLine> lineList;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemOrderRequest orderRequest;

    @OneToMany(mappedBy = "masterOrder")
    private List<ItemPartialOrder> partialOrderList;

    public List<ItemPartialOrder> getPartialOrderList() {
        return partialOrderList;
    }

    public void setPartialOrderList(List<ItemPartialOrder> partialOrderList) {
        this.partialOrderList = partialOrderList;
    }

    public ItemOrder(UUID uuid) {
        this.setUuid(uuid);
    }

    public ItemOrder() {
    }

    public ItemOrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(ItemOrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public List<ItemOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<ItemOrderLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public ItemOrderRespDto toDto() {
        ItemOrderRespDto orderDto = new ItemOrderRespDto();
        super.setOrderDtoValue(orderDto);

        List<BaseOrderLineRespDto> lineList = new ArrayList<>();
        for (ItemOrderLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        orderDto.setLineList(lineList);

        if (this.getOrderRequest() != null)
            orderDto.setOrderRequest(this.getOrderRequest().toListDto());
        return orderDto;
    }

    public ItemOrderListRespDto toListDto() {
        ItemOrderListRespDto dto = new ItemOrderListRespDto();
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
}
