package lukelin.his.dto.yb.inventory.resp;

import java.util.List;

public class CenterMedicineListSave {
    private List<CenterMedicineSaveDto> medicineList;

    public List<CenterMedicineSaveDto> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<CenterMedicineSaveDto> medicineList) {
        this.medicineList = medicineList;
    }
}
