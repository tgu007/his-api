package lukelin.his.domain.entity.inventory;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.enums.Inventory.TransferStatus;
import lukelin.his.dto.Inventory.resp.BaseTransferRespDto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class BaseTransfer extends BaseEntity implements DtoConvertible<BaseTransferRespDto> {
    private String reference;

    @Column(name = "transfer_date", nullable = false)
    private Date transferDate;

    @Column(nullable = false, name = "status")
    private TransferStatus transferStatus;

    @Column(name = "transfer_number", insertable = false, updatable = false)
    private Integer transferNumber;

    @Column(name = "transfer_number_code")
    private String transferNumberCode;

    @ManyToOne
    @JoinColumn(name = "from_warehouse_id", nullable = false)
    private DepartmentWarehouse fromWarehouse;

    @ManyToOne
    @JoinColumn(name = "to_warehouse_id", nullable = false)
    private DepartmentWarehouse toWarehouse;

    @Column(name = "confirmed_date")
    private Date confirmedDate;

    public String getTransferNumberCode() {
        return transferNumberCode;
    }

    public void setTransferNumberCode(String transferNumberCode) {
        this.transferNumberCode = transferNumberCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Integer getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(Integer transferNumber) {
        this.transferNumber = transferNumber;
    }

    public DepartmentWarehouse getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(DepartmentWarehouse fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public DepartmentWarehouse getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(DepartmentWarehouse toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }
}
