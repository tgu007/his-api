package lukelin.his.dto.Inventory.req;

import lukelin.his.domain.enums.Inventory.TransferStatus;

import java.util.Date;
import java.util.UUID;

public class BaseTransferSaveDto {
    private UUID uuid;

    private String reference;

    private TransferStatus transferStatus;

    private UUID fromWarehouseId;

    private UUID toWarehouseId;

    private Date transferDate;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public UUID getFromWarehouseId() {
        return fromWarehouseId;
    }

    public void setFromWarehouseId(UUID fromWarehouseId) {
        this.fromWarehouseId = fromWarehouseId;
    }

    public UUID getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(UUID toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

}
