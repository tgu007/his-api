package lukelin.his.dto.yb;

import java.util.*;

public class EntityUploadResp {
    private List<EntityUploadResultSaveDto> entityList;

    public List<EntityUploadResultSaveDto> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<EntityUploadResultSaveDto> entityList) {
        this.entityList = entityList;
    }
}
