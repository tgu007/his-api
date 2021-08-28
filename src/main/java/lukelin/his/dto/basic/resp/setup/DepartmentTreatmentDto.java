package lukelin.his.dto.basic.resp.setup;

import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.dto.basic.resp.ward.PatientSignInWardDto;

import java.util.List;
import java.util.UUID;

public class DepartmentTreatmentDto {
    private UUID uuid;

    private DepartmentDto department;

    private DepartmentWarehouseDto defaultPharmacy;

    private DepartmentTreatmentType type;

    private DepartmentWarehouseDto wardWarehouse;

    private List<PatientSignInWardDto> wardList;

    public List<PatientSignInWardDto> getWardList() {
        return wardList;
    }

    public void setWardList(List<PatientSignInWardDto> wardList) {
        this.wardList = wardList;
    }

    public DepartmentWarehouseDto getWardWarehouse() {
        return wardWarehouse;
    }

    public void setWardWarehouse(DepartmentWarehouseDto wardWarehouse) {
        this.wardWarehouse = wardWarehouse;
    }

    public DepartmentTreatmentType getType() {
        return type;
    }

    public void setType(DepartmentTreatmentType type) {
        this.type = type;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public DepartmentWarehouseDto getDefaultPharmacy() {
        return defaultPharmacy;
    }

    public void setDefaultPharmacy(DepartmentWarehouseDto defaultPharmacy) {
        this.defaultPharmacy = defaultPharmacy;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
