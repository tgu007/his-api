package lukelin.his.dto.basic.req.template;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.template.TemplateTag;
import lukelin.his.domain.entity.basic.template.TemplateTagMenu;
import lukelin.his.domain.enums.Basic.TagType;

import java.util.UUID;

public class TemplateTagSaveDto {
    private UUID uuid;

    private String name;

    private String templateHtml;

    private UUID menuId;

    private UUID ownerId;

    private TagType tagType;

    private String description;

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTemplateHtml() {
        return templateHtml;
    }

    public void setTemplateHtml(String templateHtml) {
        this.templateHtml = templateHtml;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public TemplateTag toEntity() {
        TemplateTag tag = new TemplateTag();
        BeanUtils.copyPropertiesIgnoreNull(this, tag);
        tag.setParent(new TemplateTagMenu(this.getMenuId()));
        if (this.getOwnerId() != null)
            tag.setOwner(new Employee(this.getOwnerId()));
        tag.setAllowEdit(true);
        double latestOrder = Ebean.find(TemplateTag.class).
                where().eq("parent.uuid", this.getMenuId())
                .findCount();
        tag.setSequence(latestOrder + 1);
        return tag;
    }

}
