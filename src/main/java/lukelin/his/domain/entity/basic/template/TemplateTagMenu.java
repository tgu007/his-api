package lukelin.his.domain.entity.basic.template;


import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.dto.basic.req.filter.TagFilterDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;
import lukelin.his.dto.basic.resp.template.TemplateTagMenuDto;
import lukelin.his.dto.basic.resp.setup.TreeNodeDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.template_tag_menu")
public class TemplateTagMenu extends BaseEntity {

    private String name;

    @Column(name = "children_allow_edit")
    private boolean allowEditChildren;

    private Integer level;

    @Column(name = "is_leaf")
    private boolean isLeaf;

    @Column(name = "allow_edit")
    private boolean allowEdit;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private TemplateTagMenu parent;

    private String description;

    private Double order;

    private boolean enabled;

    @Column(name = "expand_on_load")
    private boolean expandOnLoad;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<TemplateTag> templateTagList;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<TemplateTagMenu> childMenuList;


    public TemplateTagMenu(UUID uuid) {
        super();
        this.setUuid(uuid);
    }


    public List<TemplateTag> getTemplateTagList() {
        return templateTagList;
    }

    public void setTemplateTagList(List<TemplateTag> templateTagList) {
        this.templateTagList = templateTagList;
    }

    public List<TemplateTagMenu> getChildMenuList() {
        return childMenuList;
    }

    public void setChildMenuList(List<TemplateTagMenu> childMenuList) {
        this.childMenuList = childMenuList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
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

    public boolean isExpandOnLoad() {
        return expandOnLoad;
    }

    public void setExpandOnLoad(boolean expandOnLoad) {
        this.expandOnLoad = expandOnLoad;
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


    public TemplateTagMenuDto toDto(TagFilterDto tagFilter) {
        TemplateTagMenuDto dto = new TemplateTagMenuDto();
        dto.setKey(this.getUuid());
        dto.setTitle(this.getName());
        dto.setLeaf(false);
        dto.setExpanded(this.isExpandOnLoad());
        dto.setAllowEditChildren(this.allowEditChildren);
        if (this.getParent() != null)
            dto.setParentId(this.getParent().getUuid());

        List<TreeNodeDto> treeNodeDtoList = new ArrayList<>();
        for (TemplateTagMenu childMenu : this.getChildMenuList())
            treeNodeDtoList.add(childMenu.toDto(tagFilter));

        for (TemplateTag tag : this.getTemplateTagList()) {
            if (tagFilter.getEnabled() != null && tagFilter.getEnabled() != tag.isEnabled())
                continue;
            if (tag.getOwner() == null || tag.getOwner().getUuid() == tagFilter.getEmployeeId())
                treeNodeDtoList.add(tag.toDto());
        }
        dto.setChildren(treeNodeDtoList);
        return dto;
    }

}