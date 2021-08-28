package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb.treatment_department")
public class TreatmentDepartment extends BaseEntity {
    @OneToOne()
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentTreatment hisDepartment;

    @Column(name = "code")
    private String code;

    public DepartmentTreatment getHisDepartment() {
        return hisDepartment;
    }

    public void setHisDepartment(DepartmentTreatment hisDepartment) {
        this.hisDepartment = hisDepartment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
