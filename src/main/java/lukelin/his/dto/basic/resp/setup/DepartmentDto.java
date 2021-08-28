package lukelin.his.dto.basic.resp.setup;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.Department;
import lukelin.his.domain.enums.Basic.DepartmentType;

public class DepartmentDto extends BaseCodeDto {

    private DepartmentType type;

    private String comment;


    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Department toEntity() {
        Department department = new Department();
        BeanUtils.copyPropertiesIgnoreNull(this, department);
        return department;
    }

}
