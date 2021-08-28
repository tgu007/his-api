package lukelin.his.dto.Inventory.req.item;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.item.ItemOrderRequest;
import lukelin.his.domain.entity.inventory.item.ItemOrderRequestLine;
import lukelin.his.dto.Inventory.req.BaseOrderLineSaveDto;
import lukelin.his.dto.Inventory.req.BaseOrderRequestLineSaveDto;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


public class ItemOrderRequestLineSaveDto extends BaseOrderRequestLineSaveDto {
    private UUID itemId;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    @Bean
    public ItemOrderRequestLine toEntity() {
        ItemOrderRequestLine orderRequestLine = new ItemOrderRequestLine();
        super.setRequestLineProperty(orderRequestLine);
        BeanUtils.copyPropertiesIgnoreNull(this, orderRequestLine);
        orderRequestLine.setItem(new Item(this.getItemId()));
        return orderRequestLine;
    }

}
