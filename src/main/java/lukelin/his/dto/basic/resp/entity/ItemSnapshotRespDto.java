package lukelin.his.dto.basic.resp.entity;

import java.util.UUID;

public class ItemSnapshotRespDto extends ItemRespDto {
    private UUID snapshotId;

    public UUID getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(UUID snapshotId) {
        this.snapshotId = snapshotId;
    }
}
