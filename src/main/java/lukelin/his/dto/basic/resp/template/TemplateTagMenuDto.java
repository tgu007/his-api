package lukelin.his.dto.basic.resp.template;

import lukelin.his.dto.basic.resp.setup.TreeNodeDto;

import java.util.List;
import java.util.UUID;

public class TemplateTagMenuDto extends TreeNodeDto {
    private boolean allowEditChildren;

    private Integer level;

    private boolean allowEdit;

    private UUID parentId;

    private String description;

    private Double order;

    private boolean enabled;


    public boolean isAllowEditChildren() {
        return allowEditChildren;
    }

    public void setAllowEditChildren(boolean allowEditChildren) {
        this.allowEditChildren = allowEditChildren;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getOrder() {
        return order;
    }

    public void setOrder(Double order) {
        this.order = order;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
