package lukelin.his.domain.entity.basic.entity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.dto.basic.resp.entity.ItemSnapshotRespDto;

import javax.persistence.*;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.snapshot_item")
public class ItemSnapshot extends BaseItem implements DtoConvertible<ItemSnapshotRespDto> {
    public ItemSnapshot() {
    }

    public ItemSnapshot(UUID uuid) {
        this.setUuid(uuid);
    }

    private Integer code;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public ItemSnapshotRespDto toDto() {
        ItemSnapshotRespDto responseDto = DtoUtils.convertRawDto(this);
        this.copyDtoProperty(responseDto);
        //前台调用时DTO UUID 为ITEM ID
        responseDto.setSnapshotId(this.getUuid());
        responseDto.setUuid(this.getItem().getUuid());

        if (this.getManufacturerItem() != null)
            responseDto.setManufacturer(this.getManufacturerItem().toDto());
        return responseDto;
    }



}
