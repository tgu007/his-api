package lukelin.his.dto.Inventory.req.filter;

import java.util.List;
import java.util.UUID;

public class MedicineOrderFilterDto {
    private List<UUID> wardIdList;


    public List<UUID> getWardIdList() {
        return wardIdList;
    }

    public void setWardIdList(List<UUID> wardIdList) {
        this.wardIdList = wardIdList;
    }
}
