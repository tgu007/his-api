package lukelin.his.dto.yb.resp;

import java.util.List;

public class CenterTreatmentListSave {
    private List<CenterTreatmentSaveDto> treatmentList;

    public List<CenterTreatmentSaveDto> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<CenterTreatmentSaveDto> treatmentList) {
        this.treatmentList = treatmentList;
    }
}
