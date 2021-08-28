package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.entity.yb.WarehouseUpload;
import lukelin.his.domain.entity.yb.YBSignIn;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.dto.basic.resp.setup.DepartmentWarehouseDto;

import javax.persistence.*;
import java.util.UUID;


@javax.persistence.Entity
@Table(name = "basic.code_department_warehouse")
public class DepartmentWarehouse extends BaseIdEntity implements DtoConvertible<DepartmentWarehouseDto> {
    public DepartmentWarehouse(UUID uuid) {
        this.setUuid(uuid);
    }

    public DepartmentWarehouse() {
    }

    @Column(name = "type")
    private WarehouseType warehouseType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(mappedBy = "wardWarehouse")
    private DepartmentTreatment wardDepartment;


    @Column(name = "sequence", nullable = false, insertable = false, updatable = false)
    private Integer sequence;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "departmentWarehouse")
    private WarehouseUpload warehouseUploaded;

    public WarehouseUpload getWarehouseUploaded() {
        return warehouseUploaded;
    }

    public void setWarehouseUploaded(WarehouseUpload warehouseUploaded) {
        this.warehouseUploaded = warehouseUploaded;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public DepartmentTreatment getWardDepartment() {
        return wardDepartment;
    }

    public void setWardDepartment(DepartmentTreatment wardDepartment) {
        this.wardDepartment = wardDepartment;
    }

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseType warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    @Override
    public DepartmentWarehouseDto toDto() {
        DepartmentWarehouseDto warehouseDto = new DepartmentWarehouseDto();
        warehouseDto.setUuid(this.getUuid());
        warehouseDto.setWarehouseType(this.warehouseType);
        warehouseDto.setDepartment(this.department.toDto());
        return warehouseDto;
    }

}
