package lukelin.his.dto.yb;

import java.util.List;

public class EntityMatchReqDto {
    private String sqlx;

    private List<EntityMatchReqLineDto> xmxxy;

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public List<EntityMatchReqLineDto> getXmxxy() {
        return xmxxy;
    }

    public void setXmxxy(List<EntityMatchReqLineDto> xmxxy) {
        this.xmxxy = xmxxy;
    }
}
