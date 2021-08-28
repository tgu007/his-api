package lukelin.his.dto.yb.req.settlement;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.Settlement;

public class SettlementSaveDto extends BaseSettlementSaveDto {
    private String ybjsh;

    public String getYbjsh() {
        return ybjsh;
    }

    public void setYbjsh(String ybjsh) {
        this.ybjsh = ybjsh;
    }

    public Settlement toEntity() {
        Settlement settlement = new Settlement();
        PatientSignIn patientSignIn = new PatientSignIn(this.getPatientSignInId());
        settlement.setPatientSignIn(patientSignIn);
        settlement.setJsbh(this.getJsbh());
        settlement.setYbjsh(this.getYbjsh());
        if (this.getYbjsxx() != null)
            BeanUtils.copyPropertiesIgnoreNull(this.getYbjsxx(), settlement);
        return settlement;
    }
}
