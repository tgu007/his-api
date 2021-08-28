package lukelin.his.dto.basic.req.entity;


import java.util.UUID;

public abstract class EntitySaveDto {
    private UUID uuid;

    private String name;

    private String searchCode;

    private Integer feeTypeId;

    private boolean enabled;

    private UUID minSizeUomId;

    public UUID getMinSizeUomId() {
        return minSizeUomId;
    }

    public void setMinSizeUomId(UUID minSizeUomId) {
        this.minSizeUomId = minSizeUomId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public Integer getFeeTypeId() {
        return feeTypeId;
    }

    public void setFeeTypeId(Integer feeTypeId) {
        this.feeTypeId = feeTypeId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
