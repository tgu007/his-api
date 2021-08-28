package lukelin.his.dto.yb.req;

import java.util.List;

public class TreatmentUploadReqList {
    private List<TreatmentUploadReqDto> treatmentList;

    public List<TreatmentUploadReqDto> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<TreatmentUploadReqDto> treatmentList) {
        this.treatmentList = treatmentList;
    }
}
