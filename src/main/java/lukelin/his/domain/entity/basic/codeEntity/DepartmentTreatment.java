package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.yb.DoctorAgreement;
import lukelin.his.domain.entity.yb.TreatmentDepartment;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.ward.PatientSignInWardDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_department_treatment")
public class DepartmentTreatment extends BaseIdEntity implements DtoConvertible<DepartmentTreatmentDto> {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "default_pharmacy_id")
    private DepartmentWarehouse defaultPharmacy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ward_warehouse_id")
    private DepartmentWarehouse wardWarehouse;

    @Column(name = "type")
    private DepartmentTreatmentType type;

    @ManyToMany
    @JoinTable(name = "basic.ward_department",
            joinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "ward_id", referencedColumnName = "uuid")})
    private Set<Ward> wardList;


    @Column(name = "sequence", nullable = false, insertable = false, updatable = false)
    private Integer sequence;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hisDepartment")
    private TreatmentDepartment ybDepartment;

    public TreatmentDepartment getYbDepartment() {
        return ybDepartment;
    }

    public void setYbDepartment(TreatmentDepartment ybDepartment) {
        this.ybDepartment = ybDepartment;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<Ward> getWardList() {
        return wardList;
    }

    public void setWardList(Set<Ward> wardList) {
        this.wardList = wardList;
    }

    public DepartmentWarehouse getWardWarehouse() {
        return wardWarehouse;
    }

    public void setWardWarehouse(DepartmentWarehouse wardWarehouse) {
        this.wardWarehouse = wardWarehouse;
    }

    public DepartmentTreatmentType getType() {
        return type;
    }

    public void setType(DepartmentTreatmentType type) {
        this.type = type;
    }

    public DepartmentTreatment(UUID uuid) {
        this.setUuid(uuid);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DepartmentWarehouse getDefaultPharmacy() {
        return defaultPharmacy;
    }

    public void setDefaultPharmacy(DepartmentWarehouse defaultPharmacy) {
        this.defaultPharmacy = defaultPharmacy;
    }

    @Override
    public DepartmentTreatmentDto toDto() {
        DepartmentTreatmentDto dto = DtoUtils.convertRawDto(this);
        dto.setDepartment(this.department.toDto());
        if (this.getDefaultPharmacy() != null)
            dto.setDefaultPharmacy(this.defaultPharmacy.toDto());
        if (this.getWardWarehouse() != null)
            dto.setWardWarehouse(this.wardWarehouse.toDto());
        List<PatientSignInWardDto> wardList = new ArrayList<>();
        for (Ward ward : this.getWardList())
            wardList.add(ward.toPatientWardDto());
        dto.setWardList(wardList);
        return dto;
    }
}
