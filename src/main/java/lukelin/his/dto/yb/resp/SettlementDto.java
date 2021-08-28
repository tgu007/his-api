package lukelin.his.dto.yb.resp;

import java.math.BigDecimal;
import java.util.UUID;

public class SettlementDto extends BaseSettlementDto{
    private String ybjsh;

    public String getYbjsh() {
        return ybjsh;
    }

    public void setYbjsh(String ybjsh) {
        this.ybjsh = ybjsh;
    }
}
