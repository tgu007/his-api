package lukelin.his.domain.entity.basic.template;


import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.enums.Basic.TagType;
import lukelin.his.dto.basic.resp.template.TemplateTagDto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "basic.template_tag")
public class TemplateTag extends BaseEntity implements DtoConvertible<TemplateTagDto> {

    private String name;

    @Column(name = "description")
    private String description;

    //There's a funny problem you can't set this column to be order.
    @Column(name = "sequence")
    private Double sequence;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "allow_edit")
    private boolean allowEdit;

    @Column(name = "template_html")
    private String templateHtml;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private TemplateTagMenu parent;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    @Column(name = "tag_type")
    private TagType tagType;

    public String getTemplateHtml() {
        return templateHtml;
    }

    public void setTemplateHtml(String templateHtml) {
        this.templateHtml = templateHtml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSequence() {
        return sequence;
    }

    public void setSequence(Double sequence) {
        this.sequence = sequence;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public TemplateTagMenu getParent() {
        return parent;
    }

    public void setParent(TemplateTagMenu parent) {
        this.parent = parent;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    @Override
    public TemplateTagDto toDto() {
        TemplateTagDto dto = DtoUtils.convertRawDto(this);
        dto.setExpanded(false);
        dto.setLeaf(true);
        dto.setKey(this.getUuid());
        dto.setTitle(this.name);
        dto.setParentId(this.parent.getUuid());
        if (this.getOwner() != null)
            dto.setOwnerId(this.getOwner().getUuid());
        return dto;
    }
}
