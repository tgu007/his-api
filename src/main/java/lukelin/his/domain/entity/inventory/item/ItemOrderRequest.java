package lukelin.his.domain.entity.inventory.item;

import lukelin.his.domain.entity.inventory.BaseOrderRequest;
import lukelin.his.dto.Inventory.resp.BaseOrderRequestLineRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRequestListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRequestRespDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "inventory.item_order_request")
public class ItemOrderRequest extends BaseOrderRequest {
    public ItemOrderRequest(UUID uuid) {
        this.setUuid(uuid);
    }

    public ItemOrderRequest() {

    }

    @OneToMany(mappedBy = "orderRequest", cascade = CascadeType.ALL)
    private List<ItemOrderRequestLine> lineList;

    @OneToMany(mappedBy = "orderRequest")
    private List<ItemOrder> orderList;

    public List<ItemOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ItemOrder> orderList) {
        this.orderList = orderList;
    }

    public List<ItemOrderRequestLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<ItemOrderRequestLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public ItemOrderRequestRespDto toDto() {
        ItemOrderRequestRespDto dto = new ItemOrderRequestRespDto();
        super.setOrderRequestDtoValue(dto);

        List<BaseOrderRequestLineRespDto> lineList = new ArrayList<>();
        this.getLineList().sort(Comparator.comparing(ItemOrderRequestLine::getWhenCreated));
        for (ItemOrderRequestLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }

        dto.setLineList(lineList);
        return dto;
    }

    public ItemOrderRequestListRespDto toListDto() {
        ItemOrderRequestListRespDto dto = new ItemOrderRequestListRespDto();
        super.copyListDtoValue(dto);
        return dto;
    }
}
