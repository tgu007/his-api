package lukelin.his.dto.Inventory.req;

import java.util.List;
import java.util.UUID;

public class NZTransferSourceLineDto {
    private List<UUID> sourceLineIdList;

    public List<UUID> getSourceLineIdList() {
        return sourceLineIdList;
    }

    public void setSourceLineIdList(List<UUID> sourceLineIdList) {
        this.sourceLineIdList = sourceLineIdList;
    }
}
