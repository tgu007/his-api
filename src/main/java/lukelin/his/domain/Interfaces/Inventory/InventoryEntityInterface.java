package lukelin.his.domain.Interfaces.Inventory;

import lukelin.common.springboot.exception.ApiClientException;
import lukelin.his.domain.entity.basic.codeEntity.UnitOfMeasure;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.dto.Inventory.resp.BaseStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.BaseEntityStockSummaryRespDto;
import lukelin.his.system.NoStockException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface InventoryEntityInterface {
    UnitOfMeasure getMinUom();

    UnitOfMeasure getWarehouseUom();

    UnitOfMeasure getDepartmentUom();

    BigDecimal getWarehouseToMinRate();

    BigDecimal getDepartmentToMinRate();

    BigDecimal getListPrice();

    String getWarehouseModel();

    String getDepartmentModel();

    EntityType getEntityType();

    String getName();

    List<? extends CachedEntityStockInterface> getStockList();

    default String getDisplayQuantity(WarehouseType warehouseType, BigDecimal totalMinUomQuantity) {
        String displayQuantity = "";
        String displayUom;
        BigDecimal conversionRate;
        if (warehouseType == WarehouseType.levelOneWarehouse) {
            displayUom = this.getWarehouseUom().getName();
            conversionRate = this.getWarehouseToMinRate();
        } else {
            displayUom = this.getDepartmentUom().getName();
            conversionRate = this.getDepartmentToMinRate();
        }

        if (totalMinUomQuantity.compareTo(BigDecimal.ZERO) > 0) {
            if ((warehouseType == WarehouseType.wardWarehouse && conversionRate.compareTo(new BigDecimal(1)) == 0)
                    || (warehouseType == WarehouseType.levelOneWarehouse && conversionRate.compareTo(new BigDecimal(1)) == 0)
            )
                displayQuantity = totalMinUomQuantity.intValue() + displayUom;
            else {
                Integer roundupQuantity = totalMinUomQuantity.divide(conversionRate, 0, BigDecimal.ROUND_DOWN).intValue();
                BigDecimal remainMinUomQuantity = totalMinUomQuantity.subtract(new BigDecimal(roundupQuantity).multiply(conversionRate));
                if (roundupQuantity != 0)
                    displayQuantity = roundupQuantity.toString() + displayUom;
                if (remainMinUomQuantity.compareTo(BigDecimal.ZERO) != 0)
                    displayQuantity = displayQuantity + remainMinUomQuantity.intValue() + this.getMinUom().getName();
            }
        } else if (totalMinUomQuantity.compareTo(BigDecimal.ZERO) == 0)
            return 0 + displayUom;
        else
            throw new NoStockException("inventory.error.notLessThenZero");
        return displayQuantity;
    }

    default BigDecimal calculateUomQuantity(UnitOfMeasure uom, BigDecimal quantity) {
        if (uom == this.getMinUom())
            return quantity;
        else if (this.getWarehouseUom() == uom)
            return quantity.multiply(this.getWarehouseToMinRate());
        else if (this.getDepartmentUom() == uom)
            return quantity.multiply(this.getDepartmentToMinRate());
        else
            throw new ApiClientException("inventory.error.invalidUom");
    }

    default void assignEntityAggregatedStockInfo(BaseEntityStockSummaryRespDto entityStockDto) {
        List<BaseStockSummaryRespDto> stockSummaryList = this.buildAggregatedStockList();
        BigDecimal totalMinUomQuantity = stockSummaryList.stream().map(BaseStockSummaryRespDto::getMinUomQuantity).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        entityStockDto.setStockSummaryList(stockSummaryList);
        entityStockDto.setTotalQuantity(totalMinUomQuantity);
        entityStockDto.setMinUomDisplayQuantity(totalMinUomQuantity.toString() + this.getMinUom().getName());
        entityStockDto.setDepartmentDisplayQuantity(this.getDisplayQuantity(WarehouseType.wardWarehouse, totalMinUomQuantity));
        entityStockDto.setWarehouseDisplayQuantity(this.getDisplayQuantity(WarehouseType.levelOneWarehouse, totalMinUomQuantity));

    }

    default List<BaseStockSummaryRespDto> buildAggregatedStockList() {
        List<BaseStockSummaryRespDto> baseStockSummaryList = new ArrayList<>();
        BaseStockSummaryRespDto stockSummaryRespDto;
        for (CachedEntityStockInterface stock : this.getStockList()) {
            if (stock.getWarehouse().getWarehouseType() == WarehouseType.levelTwoWarehouse)
                continue; //后勤库房没有出库记录，不在库存里显示
            Optional<BaseStockSummaryRespDto> optionalStockSummaryRespDto = baseStockSummaryList.stream()
                    .filter(s -> s.getWarehouse().getUuid() == stock.getWarehouse().getUuid()).findFirst();

            if (optionalStockSummaryRespDto.isPresent()) {
                stockSummaryRespDto = optionalStockSummaryRespDto.get();
                stockSummaryRespDto.setMinUomQuantity(stock.getMinUomQuantity().add(stockSummaryRespDto.getMinUomQuantity()));
                stockSummaryRespDto.setTotalCost(stockSummaryRespDto.getTotalCost().add(this.calculateStockCost(stock)));
            } else {
                stockSummaryRespDto = new BaseStockSummaryRespDto();
                stockSummaryRespDto.setWarehouse(stock.getWarehouse().toDto());
                stockSummaryRespDto.setMinUomQuantity(stock.getMinUomQuantity());
                stockSummaryRespDto.setTotalCost(this.calculateStockCost(stock));
                stockSummaryRespDto.setStockIdList(new ArrayList<>());
                baseStockSummaryList.add(stockSummaryRespDto);
            }
            stockSummaryRespDto.getStockIdList().add(stock.getUuid());
        }

        //After aggregate set the display quantity string
        for (BaseStockSummaryRespDto dto : baseStockSummaryList)
            dto.setDisplayQuantity(this.getDisplayQuantity(dto.getWarehouse().getWarehouseType(), dto.getMinUomQuantity()));

        return baseStockSummaryList;
    }

    default BigDecimal calculateStockCost(CachedEntityStockInterface stock) {
        OrderLineInterface originOrderLine = stock.getOriginPurchaseLine();
        return originOrderLine.getTotalCost().multiply(stock.getMinUomQuantity()).divide(originOrderLine.getMinUomQuantity(), 2, RoundingMode.HALF_UP);
    }
}
