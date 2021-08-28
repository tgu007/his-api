package lukelin.his.domain.entity.inventory.medicine;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderStatus;
import lukelin.his.dto.Inventory.resp.medicine.BasePrescriptionMedicineOrderRespDto;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineOrderRespDto;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@MappedSuperclass
public abstract class BasePrescriptionMedicineOrder extends BaseEntity {
    @Column(name = "status", nullable = false)
    private PrescriptionMedicineOrderStatus status;

    @Column(name = "order_number", nullable = false, insertable = false, updatable = false)
    private Integer orderNumber;

    @Column(name = "order_number_code", length = 50)
    private String orderNumberCode;

    @Column(name = "processed_date")
    private Date processedDate;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public PrescriptionMedicineOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionMedicineOrderStatus status) {
        this.status = status;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumberCode() {
        return orderNumberCode;
    }

    public void setOrderNumberCode(String orderNumberCode) {
        this.orderNumberCode = orderNumberCode;
    }

    protected void setDtoValue(BasePrescriptionMedicineOrderRespDto dto) {
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime startTime = null;
        if (this.getStatus() == PrescriptionMedicineOrderStatus.submitted)
            startTime = this.getWhenCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        else
            startTime = this.getProcessedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(startTime, timeNow);
        if (duration.toDays() < 1)
            if (duration.toHours() < 1)
                dto.setOrderTimeInfo(duration.toMinutes() + "分钟前");
            else
                dto.setOrderTimeInfo(duration.toHours() + "小时前");
        else
            dto.setOrderTimeInfo(duration.toDays() + "天前");
    }
}
