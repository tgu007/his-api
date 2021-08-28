package lukelin.his.dto.Inventory.req.medicine;

import lukelin.his.domain.entity.inventory.medicine.MedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderRequest;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderRequestLine;
import lukelin.his.dto.Inventory.req.BaseOrderRequestSaveDto;
import lukelin.his.dto.Inventory.req.BaseOrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class MedicineOrderRequestSaveDto extends BaseOrderRequestSaveDto {
    private List<MedicineOrderRequestLineSaveDto> requestLineList;

    public List<MedicineOrderRequestLineSaveDto> getRequestLineList() {
        return requestLineList;
    }

    public void setRequestLineList(List<MedicineOrderRequestLineSaveDto> requestLineList) {
        this.requestLineList = requestLineList;
    }

    public MedicineOrderRequest toEntity() {
        MedicineOrderRequest medicineOrderRequest = new MedicineOrderRequest();
        super.setOrderRequestProperty(medicineOrderRequest);
        List<MedicineOrderRequestLine> lineList = new ArrayList<>();
        for (MedicineOrderRequestLineSaveDto requestLineSaveDto : this.getRequestLineList())
            lineList.add(requestLineSaveDto.toEntity());
        medicineOrderRequest.setLineList(lineList);
        return medicineOrderRequest;
    }
}
