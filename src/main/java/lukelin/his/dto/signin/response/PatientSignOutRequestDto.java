package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.enums.PatientSignIn.PatientSignOutStatus;
import lukelin.his.dto.basic.resp.setup.DepartmentTreatmentDto;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.EmployeeDto;

import javax.persistence.*;
import java.util.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class PatientSignOutRequestDto {
    private UUID uuid;

    private PatientSignOutStatus status;

    private DictionaryDto signOutReason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signOutDate;

    private String reference;

    private String advise;

    private DepartmentTreatmentDto departmentTreatment;

    private EmployeeDto doctor;

    private List<DiagnoseDto> diagnoseList;

    private UUID drgSignOutReasonId;

    public UUID getDrgSignOutReasonId() {
        return drgSignOutReasonId;
    }

    public void setDrgSignOutReasonId(UUID drgSignOutReasonId) {
        this.drgSignOutReasonId = drgSignOutReasonId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PatientSignOutStatus getStatus() {
        return status;
    }

    public void setStatus(PatientSignOutStatus status) {
        this.status = status;
    }

    public DictionaryDto getSignOutReason() {
        return signOutReason;
    }

    public void setSignOutReason(DictionaryDto signOutReason) {
        this.signOutReason = signOutReason;
    }

    public Date getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(Date signOutDate) {
        this.signOutDate = signOutDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public DepartmentTreatmentDto getDepartmentTreatment() {
        return departmentTreatment;
    }

    public void setDepartmentTreatment(DepartmentTreatmentDto departmentTreatment) {
        this.departmentTreatment = departmentTreatment;
    }

    public EmployeeDto getDoctor() {
        return doctor;
    }

    public void setDoctor(EmployeeDto doctor) {
        this.doctor = doctor;
    }

    public List<DiagnoseDto> getDiagnoseList() {
        return diagnoseList;
    }

    public void setDiagnoseList(List<DiagnoseDto> diagnoseList) {
        this.diagnoseList = diagnoseList;
    }
}
