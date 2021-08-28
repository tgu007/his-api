package lukelin.his.dto.yb.req.settlement;

import java.util.UUID;

public class PreSettlementReq {
    private UUID patientSignInId;

    private Boolean returnFeeDetail = false;

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public Boolean getReturnFeeDetail() {
        return returnFeeDetail;
    }

    public void setReturnFeeDetail(Boolean returnFeeDetail) {
        this.returnFeeDetail = returnFeeDetail;
    }
}