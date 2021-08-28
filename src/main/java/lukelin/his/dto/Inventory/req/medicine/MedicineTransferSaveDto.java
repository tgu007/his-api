package lukelin.his.dto.Inventory.req.medicine;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.inventory.medicine.MedicineTransfer;
import lukelin.his.domain.entity.inventory.medicine.MedicineTransferLine;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.dto.Inventory.req.BaseTransferSaveDto;

import java.util.ArrayList;
import java.util.List;

public class MedicineTransferSaveDto extends BaseTransferSaveDto {

    private List<MedicineTransferLineSaveDto> transferLineList;

    public List<MedicineTransferLineSaveDto> getTransferLineList() {
        return transferLineList;
    }

    public void setTransferLineList(List<MedicineTransferLineSaveDto> transferLineList) {
        this.transferLineList = transferLineList;
    }

    public MedicineTransfer toEntity() {
        MedicineTransfer medicineTransfer = new MedicineTransfer();
        BeanUtils.copyPropertiesIgnoreNull(this, medicineTransfer);
        medicineTransfer.setToWarehouse(new DepartmentWarehouse(this.getToWarehouseId()));
        medicineTransfer.setFromWarehouse(new DepartmentWarehouse(this.getFromWarehouseId()));
        List<MedicineTransferLine> lineList = new ArrayList<>();
        for (MedicineTransferLineSaveDto medicineTransferLineSaveDto : this.transferLineList)
            lineList.add(medicineTransferLineSaveDto.toEntity());
        medicineTransfer.setLineList(lineList);
        return medicineTransfer;
    }
}
