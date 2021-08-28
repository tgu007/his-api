package lukelin.his.dto.Inventory.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.Inventory.TransferStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentWarehouseDto;

import java.util.Date;
import java.util.UUID;

public abstract class BaseTransferListRespDto {
    private UUID uuid;

    private String transferNumber;

    private Date transferDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmedDate;

    private DepartmentWarehouseDto toWarehouse;

    private DepartmentWarehouseDto fromWarehouse;

    private String reference;

    private String whoCreated;

    private TransferStatus transferStatus;

    public DepartmentWarehouseDto getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(DepartmentWarehouseDto toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public DepartmentWarehouseDto getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(DepartmentWarehouseDto fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(String transferNumber) {
        this.transferNumber = transferNumber;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }
}
