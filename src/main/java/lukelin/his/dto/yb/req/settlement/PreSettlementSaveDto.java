package lukelin.his.dto.yb.req.settlement;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.yb.PreSettlement;
import lukelin.his.domain.entity.yb.PreSettlementFee;

import java.util.ArrayList;
import java.util.List;

public class PreSettlementSaveDto extends BaseSettlementSaveDto {

    public PreSettlement toEntity() {
        PreSettlement settlement = new PreSettlement();
        settlement.setJsbh(this.getJsbh());
        BeanUtils.copyPropertiesIgnoreNull(this.getYbjsxx(), settlement);
        if (this.getFYJSMX() != null) {
            List<PreSettlementFee> feeList = new ArrayList<>();
            for (SettlementFeeSaveDto feeSaveDto : this.getFYJSMX())
                feeList.add(feeSaveDto.toEntity());
            settlement.setFeeList(feeList);
        }
        return settlement;
    }

}
