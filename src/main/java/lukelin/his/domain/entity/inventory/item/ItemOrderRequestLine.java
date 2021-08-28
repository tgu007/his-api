package lukelin.his.domain.entity.inventory.item;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.inventory.BaseOrderRequestLine;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRequestLineRespDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "inventory.item_order_request_line")
public class ItemOrderRequestLine extends BaseOrderRequestLine {
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemOrderRequest orderRequest;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemOrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(ItemOrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    @Override
    public ItemOrderRequestLineRespDto toDto() {
        ItemOrderRequestLineRespDto lineDto = new ItemOrderRequestLineRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, lineDto);
        lineDto.setEntityStockSummary(this.getItem().toItemStockRespDto());
        super.setOrderRequestLineDtoValue(lineDto);
        return lineDto;
    }

    public NZTransferRespDto toNZTransferDto() {
        NZTransferRespDto dto = new NZTransferRespDto();
        dto.setKey(this.getUuid().toString());
        dto.setTitle(this.getItem().getName());
        return dto;
    }

    public ItemOrderLine toOrderLine() {
        ItemOrderLine orderLine = new ItemOrderLine();
        orderLine.setItem(this.getItem());
        orderLine.setItemSnapshot(this.getItem().findLatestSnapshot());
        orderLine.setUom(this.getRequestUom());
        orderLine.setQuantity(this.getRequestQuantity());
        orderLine.setCost(new BigDecimal(0));
        return orderLine;
    }
}
