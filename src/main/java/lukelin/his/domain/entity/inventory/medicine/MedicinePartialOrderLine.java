package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.PartialOrderLineInterface;
import lukelin.his.domain.entity.inventory.BasePartialOrderLine;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderLineRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicinePartialOrderLineRespDto;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "inventory.medicine_order_partial_line")
public class MedicinePartialOrderLine extends BasePartialOrderLine implements DtoConvertible<MedicinePartialOrderLineRespDto>, PartialOrderLineInterface {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private MedicinePartialOrder order;

    @ManyToOne
    @JoinColumn(name = "master_order_line_id")
    private MedicineOrderLine masterOrderLine;


    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getMasterOrderLine().getMedicine();
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedMedicineTransaction();
    }

    public MedicinePartialOrder getOrder() {
        return order;
    }

    public void setOrder(MedicinePartialOrder order) {
        this.order = order;
    }

    public MedicineOrderLine getMasterOrderLine() {
        return masterOrderLine;
    }

    public void setMasterOrderLine(MedicineOrderLine masterOrderLine) {
        this.masterOrderLine = masterOrderLine;
    }

    @Override
    public MedicinePartialOrderLineRespDto toDto() {
        MedicinePartialOrderLineRespDto lineDto = DtoUtils.convertRawDto(this);
        lineDto.setUom(this.getUom().toDto());
        lineDto.setMasterOrderLine(this.getMasterOrderLine().toDto());
        if (this.getUuid() != null && this.getOrder().getOrderStatus() != OrderStatus.approved)
            this.setOrderValidationInfo(lineDto);
        return lineDto;
    }

    //Todo need to put this into interface
    private void setOrderValidationInfo(MedicinePartialOrderLineRespDto lineDto) {
        BigDecimal allowedQuantity =
                this.getOrder().getMasterOrder().getLineList().stream()
                        .filter(ml -> ml.getMedicine().getUuid().equals(this.getMasterOrderLine().getMedicine().getUuid()))
                        .map(ml -> ml.getMedicine().calculateUomQuantity(ml.getUom(), ml.getQuantity()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        BigDecimal orderQuantity =
                this.getOrder().getLineList().stream()
                        .filter(ol -> ol.getMasterOrderLine().getMedicine().getUuid().equals(this.getMasterOrderLine().getMedicine().getUuid()))
                        .map(ol -> ol.getMasterOrderLine().getMedicine().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        List<MedicinePartialOrderLine> pendingAndApprovedLineList = this.getOrder().getMasterOrder().getPartialOrderList()
                .stream()
                .filter(o -> !o.getUuid().equals(this.getOrder().getUuid()) && (o.getOrderStatus() == OrderStatus.approved || o.getOrderStatus() == OrderStatus.submitted))
                .flatMap(o -> o.getLineList().stream()
                        .filter(ol -> ol.getMasterOrderLine().getMedicine().getUuid().equals(this.getMasterOrderLine().getMedicine().getUuid())))
                .collect(Collectors.toList());

        BigDecimal pendingAndApprovedQuantity = pendingAndApprovedLineList.stream()
                .map(ol -> ol.getMasterOrderLine().getMedicine().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        if (allowedQuantity.subtract(pendingAndApprovedQuantity).subtract(orderQuantity).compareTo(BigDecimal.ZERO) < 0)
            lineDto.setExceedMasterLineQuantity(true);
        else
            lineDto.setExceedMasterLineQuantity(false);
    }

    public OrderLineDetailReq toYBOrderLineDetailDto() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        MedicineOrderLine masterOrderLine = this.getMasterOrderLine();
        BigDecimal minUomQuantity = masterOrderLine.getMedicine().calculateUomQuantity(this.getUom(), this.getQuantity());

        OrderLineDetailReq req = new OrderLineDetailReq();
        req.setCMXXH(this.getUuid().toString());
        req.setWZBH(masterOrderLine.getMedicine().getUploadResult().getServerCode());
        req.setWZMC(masterOrderLine.getMedicine().getName());
        req.setPCH(masterOrderLine.getBatchNumber());
        if (this.masterOrderLine.getOrder().isReturnOrder())
            req.setZCSL(minUomQuantity.toString());
        else
            req.setSRSL(minUomQuantity.toString());
        req.setYXQZ(df.format(masterOrderLine.getExpireDate()));
        req.setCLBZ("0");
        req.setKCDW(masterOrderLine.getMedicine().getMinUom().getName());
        req.setRKDJ(masterOrderLine.getMinUomCostPrice().toString());
        return req;
    }
}
