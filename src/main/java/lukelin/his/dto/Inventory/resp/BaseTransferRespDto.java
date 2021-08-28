package lukelin.his.dto.Inventory.resp;

import lukelin.his.domain.enums.Inventory.TransferStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentWarehouseDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class BaseTransferRespDto {
    private UUID uuid;

    private String transferNumber;

    private Date transferDate;

    private DepartmentWarehouseDto fromWarehouse;

    private String reference;

    private TransferStatus transferStatus;

    private DepartmentWarehouseDto toWarehouse;

    private List<BaseTransferLineRespDto> lineList;

    private Date confirmedDate;

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

    public DepartmentWarehouseDto getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(DepartmentWarehouseDto fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
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

    public DepartmentWarehouseDto getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(DepartmentWarehouseDto toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public List<BaseTransferLineRespDto> getLineList() {
        return lineList;
    }

    public void setLineList(List<BaseTransferLineRespDto> lineList) {
        this.lineList = lineList;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }
}
