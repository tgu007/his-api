package lukelin.his.dto.yb.req.settlement;

import lukelin.his.dto.yb.req.settlement.SettleSummarySaveDto;
import lukelin.his.dto.yb.req.settlement.SettlementFeeSaveDto;

import java.util.List;
import java.util.UUID;

public class BaseSettlementSaveDto {
    private UUID patientSignInId;

    private String jsbh;

    private SettleSummarySaveDto ybjsxx;

    private List<SettlementFeeSaveDto> FYJSMX;

    public List<SettlementFeeSaveDto> getFYJSMX() {
        return FYJSMX;
    }

    public void setFYJSMX(List<SettlementFeeSaveDto> FYJSMX) {
        this.FYJSMX = FYJSMX;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public String getJsbh() {
        return jsbh;
    }

    public void setJsbh(String jsbh) {
        this.jsbh = jsbh;
    }

    public SettleSummarySaveDto getYbjsxx() {
        return ybjsxx;
    }

    public void setYbjsxx(SettleSummarySaveDto ybjsxx) {
        this.ybjsxx = ybjsxx;
    }
}
