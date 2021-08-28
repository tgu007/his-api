package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BaseOrderLineRespDto;
import lukelin.his.dto.basic.resp.entity.ItemRespDto;
import lukelin.his.dto.basic.resp.entity.ItemSnapshotRespDto;
import lukelin.his.dto.basic.resp.setup.ManufacturerItemRespDto;

public class ItemOrderLineRespDto extends BaseOrderLineRespDto {
    private ItemSnapshotRespDto item;

    private ManufacturerItemRespDto manufacturer;

    public ManufacturerItemRespDto getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerItemRespDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ItemSnapshotRespDto getItem() {
        return item;
    }

    public void setItem(ItemSnapshotRespDto item) {
        this.item = item;
    }
}
