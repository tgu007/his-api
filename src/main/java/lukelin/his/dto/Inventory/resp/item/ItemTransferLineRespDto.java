package lukelin.his.dto.Inventory.resp.item;

import lukelin.his.dto.Inventory.resp.BaseTransferLineRespDto;
import lukelin.his.dto.basic.resp.entity.ItemRespDto;
import lukelin.his.dto.basic.resp.entity.ItemSnapshotRespDto;

public class ItemTransferLineRespDto extends BaseTransferLineRespDto {
    private ItemSnapshotRespDto item;

    public ItemSnapshotRespDto getItem() {
        return item;
    }

    public void setItem(ItemSnapshotRespDto item) {
        this.item = item;
    }
}
