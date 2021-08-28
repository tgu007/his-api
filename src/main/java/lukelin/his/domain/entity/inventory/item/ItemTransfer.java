package lukelin.his.domain.entity.inventory.item;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.Interfaces.Inventory.TransferInterface;
import lukelin.his.domain.entity.inventory.BaseTransfer;
import lukelin.his.dto.Inventory.resp.BaseTransferLineRespDto;
import lukelin.his.dto.Inventory.resp.item.*;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "inventory.item_transfer")
public class ItemTransfer extends BaseTransfer implements TransferInterface {

    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL)
    private List<ItemTransferLine> lineList;

    public List<ItemTransferLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<ItemTransferLine> lineList) {
        this.lineList = lineList;
    }

    @Override
    public ItemTransferRespDto toDto() {
        ItemTransferRespDto transferDto = new ItemTransferRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, transferDto);
        transferDto.setTransferNumber(Utils.buildDisplayCode(this.getTransferNumber()));
        transferDto.setFromWarehouse(this.getFromWarehouse().toDto());
        transferDto.setToWarehouse(this.getToWarehouse().toDto());

        List<BaseTransferLineRespDto> lineList = new ArrayList<>();
        for (ItemTransferLine line : this.getLineList()) {
            lineList.add(line.toDto());
        }
        transferDto.setLineList(lineList);
        return transferDto;
    }

    public ItemTransferListRespDto toListDto() {
        ItemTransferListRespDto dto = new ItemTransferListRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        dto.setWhoCreated(this.getWhoCreatedName());
        dto.setTransferNumber(Utils.buildDisplayCode(this.getTransferNumber()));
        dto.setFromWarehouse(this.getFromWarehouse().toDto());
        dto.setToWarehouse(this.getToWarehouse().toDto());
        return dto;
    }
}
