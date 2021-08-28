package lukelin.his.dto.Inventory.req.item;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.item.ItemPartialOrderLine;
import lukelin.his.dto.Inventory.req.BasePartialOrderLineSaveDto;

public class ItemPartialOrderLineSaveDto extends BasePartialOrderLineSaveDto {
    public ItemPartialOrderLine toEntity() {
        ItemPartialOrderLine orderLine = new ItemPartialOrderLine();
        BeanUtils.copyPropertiesIgnoreNull(this, orderLine);
        orderLine.setUom(new UnitOfMeasure(this.getUomId()));
        orderLine.setMasterOrderLine(new ItemOrderLine(this.getMasterOrderLineId()));
        return orderLine;
    }
}
