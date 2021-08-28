package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.dto.yb.resp.SettlementDto;
import org.springframework.beans.BeanUtils;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "yb.settlement")
public class Settlement extends BaseSettlement {

    private String ybjsh;

    public String getYbjsh() {
        return ybjsh;
    }

    public void setYbjsh(String ybjsh) {
        this.ybjsh = ybjsh;
    }


    public SettlementDto toSettlementDto() {
        SettlementDto dto = new SettlementDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
