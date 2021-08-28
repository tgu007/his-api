package lukelin.his.dto.basic.resp.setup;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public abstract class TreeNodeDto {
    private UUID key;

    private String title;

    @JsonProperty("isLeaf")
    private Boolean isLeaf;

    private boolean expanded;

    private List<TreeNodeDto> children;

    public List<TreeNodeDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeDto> children) {
        this.children = children;
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
