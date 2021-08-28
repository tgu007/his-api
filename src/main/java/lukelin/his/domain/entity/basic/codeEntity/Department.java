package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.enums.Basic.DepartmentType;
import lukelin.his.dto.basic.resp.setup.DepartmentDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_department")
public class Department extends BaseCodeEntity implements DtoConvertible<DepartmentDto> {

    @Column(name = "type", nullable = false)
    private DepartmentType type;

    private String comment;

    @Column(name = "sequence", nullable = false)
    private BigDecimal sequence;

    public Department() {
        super();
    }

    public Department(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    public BigDecimal getSequence() {
        return sequence;
    }

    public void setSequence(BigDecimal sequence) {
        this.sequence = sequence;
    }

    @OneToOne(mappedBy = "department")
    private DepartmentWarehouse warehouse;

    public DepartmentWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(DepartmentWarehouse warehouse) {
        this.warehouse = warehouse;
    }

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

    @Override
    public DepartmentDto toDto() {
        DepartmentDto departmentDto = new DepartmentDto();
        BeanUtils.copyPropertiesIgnoreNull(this, departmentDto);
        departmentDto.setUuid(this.getUuid());
        return departmentDto;
    }
}
