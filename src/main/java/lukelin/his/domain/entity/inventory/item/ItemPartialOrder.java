package lukelin.his.domain.entity.inventory.item;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.BasePartialOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrderLine;
import lukelin.his.dto.Inventory.resp.BaseOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.BasePartialOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemPartialOrderListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemPartialOrderRespDto;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "inventory.item_order_partial")
public class ItemPartialOrder extends BasePartialOrder {
    @ManyToOne
    @JoinColumn(name = "master_order_id")
    private ItemOrder masterOrder;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ItemPartialOrderLine> lineList;

    public List<ItemPartialOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<ItemPartialOrderLine> lineList) {
        this.lineList = lineList;
    }

    public ItemOrder getMasterOrder() {
        return masterOrder;
    }

    public void setMasterOrder(ItemOrder masterOrder) {
        this.masterOrder = masterOrder;
    }

    @Override
    public ItemPartialOrderRespDto toDto() {
        ItemPartialOrderRespDto orderDto = new ItemPartialOrderRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, orderDto);
        orderDto.setOrderNumber(Utils.buildDisplayCode(this.getOrderNumber()));

        List<BasePartialOrderLineRespDto> lineList = new ArrayList<>();
        for (ItemPartialOrderLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        orderDto.setLineList(lineList);
        orderDto.setMasterOrder(this.getMasterOrder().toListDto());
        return orderDto;
    }

    public ItemPartialOrderListRespDto toListDto() {
        ItemPartialOrderListRespDto dto = new ItemPartialOrderListRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setOrderNumber(Utils.buildDisplayCode(this.getOrderNumber()));
        dto.setMasterOrder(this.getMasterOrder().toListDto());
        if (this.getApprovedBy() != null)
            dto.setApprovedBy(this.getApprovedBy().getName());
        return dto;
    }
}
