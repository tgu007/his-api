package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb.warehouse_mapping")
public class WarehouseUpload extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "warehouse_id", nullable = false)
    private DepartmentWarehouse departmentWarehouse;

    @Column(name = "warehouse_number")
    private String serverCode;

    public DepartmentWarehouse getDepartmentWarehouse() {
        return departmentWarehouse;
    }

    public void setDepartmentWarehouse(DepartmentWarehouse departmentWarehouse) {
        this.departmentWarehouse = departmentWarehouse;
    }

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }
}
