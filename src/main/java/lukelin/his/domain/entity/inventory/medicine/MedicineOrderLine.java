package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.Interfaces.Inventory.InventoryEntityInterface;
import lukelin.his.domain.Interfaces.Inventory.MedicineOrderLineInterface;
import lukelin.his.domain.Interfaces.Inventory.OrderLineInterface;
import lukelin.his.domain.entity.basic.entity.MedicineSnapshot;
import lukelin.his.domain.entity.inventory.BaseOrderLine;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderLineRespDto;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "inventory.medicine_order_line")
public class MedicineOrderLine extends BaseOrderLine implements DtoConvertible<MedicineOrderLineRespDto>, MedicineOrderLineInterface {


    @Column(name = "batch_txt")
    private String batchText;

    @Column(name = "expire_date")
    private Date expireDate;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerMedicine manufacturerMedicine;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private MedicineOrder order;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;


    @ManyToOne
    @JoinColumn(name = "origin_purchase_line_id")
    private MedicineOrderLine originPurchaseLine;

    @ManyToOne()
    @JoinColumn(name = "medicine_snapshot_id", nullable = false)
    private MedicineSnapshot medicineSnapshot;

    @OneToMany(mappedBy = "masterOrderLine")
    private List<MedicinePartialOrderLine> partialOrderLineList;

    public MedicineOrderLine(UUID uuid) {
        this.setUuid(uuid);
    }

    public MedicineOrderLine() {
    }

    public List<MedicinePartialOrderLine> getPartialOrderLineList() {
        return partialOrderLineList;
    }

    public void setPartialOrderLineList(List<MedicinePartialOrderLine> partialOrderLineList) {
        this.partialOrderLineList = partialOrderLineList;
    }

    public MedicineSnapshot getMedicineSnapshot() {
        return medicineSnapshot;
    }

    public void setMedicineSnapshot(MedicineSnapshot medicineSnapshot) {
        this.medicineSnapshot = medicineSnapshot;
    }


    public String getBatchText() {
        return batchText;
    }

    public void setBatchText(String batchText) {
        this.batchText = batchText;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public ManufacturerMedicine getManufacturerMedicine() {
        return manufacturerMedicine;
    }

    public void setManufacturerMedicine(ManufacturerMedicine manufacturerMedicine) {
        this.manufacturerMedicine = manufacturerMedicine;
    }

    public MedicineOrder getOrder() {
        return order;
    }

    public void setOrder(MedicineOrder order) {
        this.order = order;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public MedicineOrderLine getOriginPurchaseLine() {
        return originPurchaseLine;
    }


    public void setOriginPurchaseLine(MedicineOrderLine originPurchaseLine) {
        this.originPurchaseLine = originPurchaseLine;
    }

    @Override
    public OrderLineInterface getOrderLine() {
        return this;
    }

    @Override
    public CachedTransactionInterface createNewTransactionInstance() {
        return new CachedMedicineTransaction();
    }

    @Override
    public InventoryEntityInterface getInventoryEntity() {
        return this.getMedicine();
    }

    @Override
    public BigDecimal getMinUomQuantity() {
        return this.getMedicine().calculateUomQuantity(this.getUom(), this.getQuantity());
    }

    @Override
    public MedicineOrderLineRespDto toDto() {
        MedicineOrderLineRespDto lineDto = DtoUtils.convertRawDto(this);
        lineDto.setMedicine(this.getMedicineSnapshot().toDto());
        lineDto.setUom(this.getUom().toDto());
        lineDto.setTotalCost(this.getTotalCost());
        if (this.getManufacturerMedicine() != null)
            lineDto.setManufacturer(this.getManufacturerMedicine().toDto());

        if (this.getBrand() != null)
            lineDto.setBrand(this.getBrand().toDto());

        BigDecimal totalPartialLineMinUomQuantity = this.getPartialOrderLineList().stream()
                .filter(l -> l.getOrder().getOrderStatus() == OrderStatus.approved)
                .map(l -> this.getMedicine().calculateUomQuantity(l.getUom(), l.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        lineDto.setTotalPartialLineQuantity(this.getMedicine().getDisplayQuantity(WarehouseType.levelOneWarehouse, totalPartialLineMinUomQuantity));

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
        dto.setTitle(this.getMedicine().getName());
        return dto;
    }

    public MedicinePartialOrderLine toPartialOrderLine() {
        MedicinePartialOrderLine partialOrderLine = new MedicinePartialOrderLine();
        partialOrderLine.setMasterOrderLine(this);
        partialOrderLine.setUom(this.getUom());
        partialOrderLine.setQuantity(this.getQuantity());
        return partialOrderLine;
    }

    private void setRequestValidationInfo(MedicineOrderLineRespDto lineDto) {
        Boolean inRequest = this.getOrder().getOrderRequest().getLineList()
                .stream().anyMatch(rl -> rl.getMedicine().getUuid().equals(this.getMedicine().getUuid()));
        lineDto.setInRequest(inRequest);

        if (inRequest) {
            BigDecimal allowedQuantity =
                    this.getOrder().getOrderRequest().getLineList().stream()
                            .filter(rl -> rl.getMedicine().getUuid().equals(this.getMedicine().getUuid()))
                            .map(rl -> rl.getMedicine().calculateUomQuantity(rl.getRequestUom(), rl.getRequestQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

            BigDecimal orderQuantity =
                    this.getOrder().getLineList().stream()
                            .filter(ol -> ol.getMedicine().getUuid().equals(this.getMedicine().getUuid()))
                            .map(ol -> ol.getMedicine().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

            List<MedicineOrderLine> pendingAndApprovedLineList = this.getOrder().getOrderRequest().getOrderList()
                    .stream()
                    .filter(o -> !o.getUuid().equals(this.getOrder().getUuid()) && (o.getOrderStatus() == OrderStatus.approved || o.getOrderStatus() == OrderStatus.submitted))
                    .flatMap(o -> o.getLineList().stream()
                            .filter(ol -> ol.getMedicine().getUuid().equals(this.getMedicine().getUuid())))
                    .collect(Collectors.toList());

            BigDecimal pendingAndApprovedQuantity = pendingAndApprovedLineList.stream()
                    .map(ol -> ol.getMedicine().calculateUomQuantity(ol.getUom(), ol.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

            if (allowedQuantity.subtract(pendingAndApprovedQuantity).subtract(orderQuantity).compareTo(BigDecimal.ZERO) < 0)
                lineDto.setExceedRequestQuantity(true);
            else
                lineDto.setExceedRequestQuantity(false);
        }
    }


    public MedicineTransferLine generateTransferLine() {
        MedicineTransferLine newTransferLine = new MedicineTransferLine();
        newTransferLine.setMedicine(this.getMedicine());
        newTransferLine.setMedicineSnapshot(this.getMedicine().findLatestSnapshot());
        newTransferLine.setQuantity(this.getQuantity());
        newTransferLine.setUom(this.getUom());
        return newTransferLine;
    }


    public BigDecimal getMinUomCostPrice() {
        return this.getTotalCost().divide(this.getMinUomQuantity(), 2, RoundingMode.HALF_UP);
    }

    public OrderLineDetailReq toYBOrderLineDetailDto() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        BigDecimal minUomQuantity = this.getMedicine().calculateUomQuantity(this.getUom(), this.getQuantity());

        OrderLineDetailReq req = new OrderLineDetailReq();
        req.setCMXXH(this.getUuid().toString());
        req.setWZBH(this.getMedicine().getUploadResult().getServerCode());
        req.setWZMC(this.getMedicine().getName());
        req.setPCH(this.getBatchNumber());
        if (this.getOrder().isReturnOrder())
            req.setZCSL(minUomQuantity.toString());
        else
            req.setSRSL(minUomQuantity.toString());
        req.setYXQZ(df.format(this.getExpireDate()));
        req.setCLBZ("0");
        req.setKCDW(this.getMedicine().getMinUom().getName());
        req.setRKDJ(this.getMinUomCostPrice().toString());
        return req;
    }
}
