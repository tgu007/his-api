package lukelin.his.dto.yb.req;

import java.util.List;

public class UploadedMedicineListSaveDto {
    private List<UploadedMedicineSaveDto> medicineList;

    public List<UploadedMedicineSaveDto> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<UploadedMedicineSaveDto> medicineList) {
        this.medicineList = medicineList;
    }
}
