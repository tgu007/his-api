package lukelin.his.dto.Inventory.req.item;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.Brand;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerItem;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.dto.Inventory.req.BaseOrderLineSaveDto;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


public class ItemOrderLineSaveDto extends BaseOrderLineSaveDto {
    private UUID itemId;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    @Bean
    public ItemOrderLine toEntity() {
        ItemOrderLine orderLine = new ItemOrderLine();
        super.setOrderLineProperty(orderLine);
        BeanUtils.copyPropertiesIgnoreNull(this, orderLine);
        if (this.getManufacturerId() != null)
            orderLine.setManufacturerItem(new ManufacturerItem(this.getManufacturerId()));

        if (this.getBrandId() != null)
            orderLine.setBrand(new Brand(this.getBrandId()));

        Item item = new Item(this.getItemId());
        orderLine.setItem(item);

        if (this.getOriginPurchaseLineId() != null) {
            //for return order
            ItemOrderLine originPurchaseLine = Ebean.find(ItemOrderLine.class, this.getOriginPurchaseLineId());
            orderLine.setOriginPurchaseLine(originPurchaseLine);
            //退单找到当时订单的对应ITEM SNAPSHOT
            orderLine.setItemSnapshot(originPurchaseLine.getItemSnapshot());
        } else {
            //找到最新的SNAPSHOT
            ItemSnapshot snapshot = item.findLatestSnapshot();
            orderLine.setItemSnapshot(snapshot);
        }

        return orderLine;
    }

}
