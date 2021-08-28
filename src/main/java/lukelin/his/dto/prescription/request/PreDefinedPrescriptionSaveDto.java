package lukelin.his.dto.prescription.request;

import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.prescription.PreDefinedPrescription;
import lukelin.his.domain.enums.Prescription.PreDefinedPrescriptionType;
import lukelin.his.dto.basic.req.QuickAddCodeEntityDto;
import lukelin.his.system.Utils;

import java.util.*;

public class PreDefinedPrescriptionSaveDto extends QuickAddCodeEntityDto {
    private UUID employeeId;

    private UUID uuid;

    private List<PreDefinedPrescriptionEntitySaveDto> lineList;


    private PreDefinedPrescriptionType type;

    private boolean oneOff;


    public PreDefinedPrescriptionType getType() {
        return type;
    }

    public void setType(PreDefinedPrescriptionType type) {
        this.type = type;
    }

    public boolean isOneOff() {
        return oneOff;
    }

    public void setOneOff(boolean oneOff) {
        this.oneOff = oneOff;
    }

    public List<PreDefinedPrescriptionEntitySaveDto> getLineList() {
        return lineList;
    }

    public void setLineList(List<PreDefinedPrescriptionEntitySaveDto> lineList) {
        this.lineList = lineList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public PreDefinedPrescription toEntity() {
        PreDefinedPrescription entity = new PreDefinedPrescription();
        entity.setName(this.getName());
        entity.setCode(this.getName());
        entity.setEnabled(true);
        entity.setCreatedByDoctor(Ebean.find(Employee.class, this.getEmployeeId()));
        entity.setSearchCode(Utils.getFirstSpell(this.getName()));
        entity.setType(this.getType());
        entity.setOneOff(this.isOneOff());
        return entity;
    }
}
