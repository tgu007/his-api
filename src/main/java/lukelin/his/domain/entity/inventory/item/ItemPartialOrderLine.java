package lukelin.his.domain.entity.inventory.item;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.PartialOrderLineInterface;
import lukelin.his.domain.entity.inventory.BasePartialOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrderLine;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.resp.item.ItemPartialOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicinePartialOrderLineRespDto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "inventory.item_order_partial_line")
public class ItemPartialOrderLine extends BasePartialOrderLine implements DtoConvertible<ItemPartialOrderLineRespDto>, PartialOrderLineInterface {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ItemPartialOrder order;

    @ManyToOne
    @JoinColumn(name = "master_order_line_id")
    private ItemOrderLine masterOrderLine;

    public ItemPartialOrder getOrder() {
        return order;
    }

    public void setOrder(ItemPartialOrder order) {
        this.order = order;
    }

    public ItemOrderLine getMasterOrderLine() {
        return masterOrderLine;
    }

    public void setMasterOrderLine(ItemOrderLine masterOrderLine) {
        this.masterOrderLine = masterOrderLine;
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getMasterOrderLine().getItem();
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedItemTransaction();
    }

    @Override
    public ItemPartialOrderLineRespDto toDto() {
        ItemPartialOrderLineRespDto lineDto = DtoUtils.convertRawDto(this);
        lineDto.setUom(this.getUom().toDto());
        lineDto.setMasterOrderLine(this.getMasterOrderLine().toDto());
        if (this.getUuid() != null && this.getOrder().getOrderStatus() != OrderStatus.approved)
            this.setOrderValidationInfo(lineDto);
        return lineDto;
    }

    private void setOrderValidationInfo(ItemPartialOrderLineRespDto lineDto) {
        BigDecimal allowedQuantity =
                this.getOrder().getMasterOrder().getLineList().stream()
                        .filter(ml -> ml.getItem().getUuid().equals(this.getMasterOrderLine().getItem().getUuid()))
                        .map(ml -> ml.getItem().calculateUomQuantity(ml.getUom(), ml.getQuantity()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        BigDecimal orderQuantity =
                this.getOrder().getLineList().stream()
                        .filter(ol -> ol.getMasterOrderLine().getItem().getUuid().equals(this.getMasterOrderLine().getItem().getUuid()))
                        .map(ol -> ol.getMasterOrderLine().getItem().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        List<ItemPartialOrderLine> pendingAndApprovedLineList = this.getOrder().getMasterOrder().getPartialOrderList()
                .stream()
                .filter(o -> !o.getUuid().equals(this.getOrder().getUuid()) && (o.getOrderStatus() == OrderStatus.approved || o.getOrderStatus() == OrderStatus.submitted))
                .flatMap(o -> o.getLineList().stream()
                        .filter(ol -> ol.getMasterOrderLine().getItem().getUuid().equals(this.getMasterOrderLine().getItem().getUuid())))
                .collect(Collectors.toList());

        BigDecimal pendingAndApprovedQuantity = pendingAndApprovedLineList.stream()
                .map(ol -> ol.getMasterOrderLine().getItem().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        if (allowedQuantity.subtract(pendingAndApprovedQuantity).subtract(orderQuantity).compareTo(BigDecimal.ZERO) < 0)
            lineDto.setExceedMasterLineQuantity(true);
        else
            lineDto.setExceedMasterLineQuantity(false);
    }
}
