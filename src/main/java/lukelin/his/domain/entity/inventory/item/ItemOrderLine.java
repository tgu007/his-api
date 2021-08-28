package lukelin.his.domain.entity.inventory.item;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.OrderLineInterface;
import lukelin.his.domain.Interfaces.Inventory.PartialOrderLineInterface;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerItem;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.entity.ItemSnapshot;
import lukelin.his.domain.entity.inventory.BaseOrderLine;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.inventory.medicine.MedicinePartialOrderLine;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderLineRespDto;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "inventory.item_order_line")
public class ItemOrderLine extends BaseOrderLine implements DtoConvertible<ItemOrderLineRespDto>, OrderLineInterface {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ItemOrder order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "origin_purchase_line_id")
    private ItemOrderLine originPurchaseLine;

    @ManyToOne()
    @JoinColumn(name = "item_snapshot_id", nullable = false)
    private ItemSnapshot itemSnapshot;

    @OneToMany(mappedBy = "masterOrderLine")
    private List<ItemPartialOrderLine> partialOrderLineList;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerItem manufacturerItem;

    public ManufacturerItem getManufacturerItem() {
        return manufacturerItem;
    }

    public void setManufacturerItem(ManufacturerItem manufacturerItem) {
        this.manufacturerItem = manufacturerItem;
    }

    public List<ItemPartialOrderLine> getPartialOrderLineList() {
        return partialOrderLineList;
    }

    public void setPartialOrderLineList(List<ItemPartialOrderLine> partialOrderLineList) {
        this.partialOrderLineList = partialOrderLineList;
    }

    public ItemOrderLine(UUID uuid) {
        this.setUuid(uuid);
    }

    public ItemOrderLine() {
    }

    public ItemSnapshot getItemSnapshot() {
        return itemSnapshot;
    }

    public void setItemSnapshot(ItemSnapshot itemSnapshot) {
        this.itemSnapshot = itemSnapshot;
    }

    public ItemOrder getOrder() {
        return order;
    }

    public void setOrder(ItemOrder order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemOrderLine getOriginPurchaseLine() {
        return originPurchaseLine;
    }


    public void setOriginPurchaseLine(ItemOrderLine originPurchaseLine) {
        this.originPurchaseLine = originPurchaseLine;
    }

    @Override
    public OrderLineInterface getOrderLine() {
        return this;
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedItemTransaction();
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getItem();
    }


    @Override
    public BigDecimal getMinUomQuantity() {
        return this.getItem().calculateUomQuantity(this.getUom(), this.getQuantity());
    }

    @Override
    public ItemOrderLineRespDto toDto() {
        ItemOrderLineRespDto lineDto = DtoUtils.convertRawDto(this);
        lineDto.setItem(this.getItemSnapshot().toDto());
        lineDto.setUom(this.getUom().toDto());
        lineDto.setTotalCost(this.getTotalCost());
        if (this.getManufacturerItem() != null)
            lineDto.setManufacturer(this.getManufacturerItem().toDto());

        if (this.getBrand() != null)
            lineDto.setBrand(this.getBrand().toDto());

        BigDecimal totalPartialLineMinUomQuantity = this.getPartialOrderLineList().stream()
                .filter(l -> l.getOrder().getOrderStatus() == OrderStatus.approved)
                .map(l -> this.getItem().calculateUomQuantity(l.getUom(), l.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        lineDto.setTotalPartialLineQuantity(this.getItem().getDisplayQuantity(WarehouseType.levelOneWarehouse, totalPartialLineMinUomQuantity));

        if (this.getOrder() != null) {
            lineDto.setOrderNumber(this.getOrder().getOrderNumberCode());

            if (this.getOrder().isReturnOrder())
                lineDto.setOriginPurchaseLine(this.getOriginPurchaseLine().toDto());

            if (this.getOrder().getOrderRequest() != null && this.getOrder().getOrderStatus() != OrderStatus.approved)
                this.setRequestValidationInfo(lineDto);

        }
        return lineDto;
    }

    public NZTransferRespDto toNZTransferDto() {
        NZTransferRespDto dto = new NZTransferRespDto();
        dto.setKey(this.getUuid().toString());
        dto.setTitle(this.getItem().getName());
        return dto;
    }

    public ItemPartialOrderLine toPartialOrderLine() {
        ItemPartialOrderLine partialOrderLine = new ItemPartialOrderLine();
        partialOrderLine.setMasterOrderLine(this);
        partialOrderLine.setUom(this.getUom());
        partialOrderLine.setQuantity(this.getQuantity());
        return partialOrderLine;
    }

    private void setRequestValidationInfo(ItemOrderLineRespDto lineDto) {
        Boolean inRequest = this.getOrder().getOrderRequest().getLineList()
                .stream().anyMatch(rl -> rl.getItem().getUuid().equals(this.getItem().getUuid()));
        lineDto.setInRequest(inRequest);

        if (inRequest) {
            BigDecimal allowedQuantity =
                    this.getOrder().getOrderRequest().getLineList().stream()
                            .filter(rl -> rl.getItem().getUuid().equals(this.getItem().getUuid()))
                            .map(rl -> rl.getItem().calculateUomQuantity(rl.getRequestUom(), rl.getRequestQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

            BigDecimal orderQuantity =
                    this.getOrder().getLineList().stream()
                            .filter(ol -> ol.getItem().getUuid().equals(this.getItem().getUuid()))
                            .map(ol -> ol.getItem().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

            List<ItemOrderLine> approvedLineList = this.getOrder().getOrderRequest().getOrderList()
                    .stream()
                    .filter(o -> o.getOrderStatus() == OrderStatus.approved || o.getOrderStatus() == OrderStatus.submitted)
                    .flatMap(o -> o.getLineList().stream()
                            .filter(ol -> ol.getItem().getUuid().equals(this.getItem().getUuid())))
                    .collect(Collectors.toList());

            BigDecimal approvedQuantity = approvedLineList.stream()
                    .map(ol -> ol.getItem().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

            if (allowedQuantity.subtract(approvedQuantity).subtract(orderQuantity).compareTo(BigDecimal.ZERO) < 0)
                lineDto.setExceedRequestQuantity(true);
            else
                lineDto.setExceedRequestQuantity(false);
        }
    }


}
