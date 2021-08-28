package lukelin.his.dto.yb.inventory.req;

import java.util.*;

public class InventoryUploadList {
    private List<InventoryUploadReqDto> reqList;

    public List<InventoryUploadReqDto> getReqList() {
        return reqList;
    }

    public void setReqList(List<InventoryUploadReqDto> reqList) {
        this.reqList = reqList;
    }
}
