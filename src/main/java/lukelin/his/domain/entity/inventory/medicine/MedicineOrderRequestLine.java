package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.inventory.BaseOrderRequestLine;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.medicine.MedicineOrderRequestLineRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "inventory.medicine_order_request_line")
public class MedicineOrderRequestLine extends BaseOrderRequestLine {
    @ManyToOne
    @JoinColumn(name = "request_id")
    private MedicineOrderRequest orderRequest;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(name = "last_period_usage")
    private String lastPeriodUsage;

    public String getLastPeriodUsage() {
        return lastPeriodUsage;
    }

    public void setLastPeriodUsage(String lastPeriodUsage) {
        this.lastPeriodUsage = lastPeriodUsage;
    }

    public MedicineOrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(MedicineOrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @Override
    public MedicineOrderRequestLineRespDto toDto() {
        MedicineOrderRequestLineRespDto lineDto = new MedicineOrderRequestLineRespDto();
        BeanUtils.copyPropertiesIgnoreNull(this, lineDto);
        lineDto.setEntityStockSummary(this.getMedicine().toMedicineStockRespDto());
        super.setOrderRequestLineDtoValue(lineDto);
        return lineDto;
    }

    public NZTransferRespDto toNZTransferDto() {
        NZTransferRespDto dto = new NZTransferRespDto();
        dto.setKey(this.getUuid().toString());
        dto.setTitle(this.getMedicine().getName());
        return dto;
    }

    public MedicineOrderLine toOrderLine() {
        MedicineOrderLine orderLine = new MedicineOrderLine();
        orderLine.setMedicine(this.getMedicine());
        orderLine.setMedicineSnapshot(this.getMedicine().findLatestSnapshot());
        orderLine.setUom(this.getRequestUom());
        orderLine.setQuantity(this.getRequestQuantity());
        orderLine.setCost(new BigDecimal(0));
        return orderLine;
    }

}
