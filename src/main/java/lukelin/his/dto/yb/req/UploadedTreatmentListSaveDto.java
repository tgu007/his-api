package lukelin.his.dto.yb.req;

import java.util.List;

public class UploadedTreatmentListSaveDto {
    private List<UploadedTreatmentSaveDto> treatmentList;

    public List<UploadedTreatmentSaveDto> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<UploadedTreatmentSaveDto> treatmentList) {
        this.treatmentList = treatmentList;
    }
}
