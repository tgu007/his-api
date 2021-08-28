package lukelin.his.dto.yb.req;

import io.ebean.Ebean;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.yb.DoctorAgreement;

import java.util.UUID;

public class DoctorAgreementSaveDto {
    private UUID uuid;

    private UUID doctorId;

    private String agreementNumber;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public DoctorAgreement toEntity() {
        DoctorAgreement agreementNumber = null;
        if (this.getUuid() != null)
            agreementNumber = Ebean.find(DoctorAgreement.class, this.getUuid());
        else
            agreementNumber = new DoctorAgreement();
        Employee doctor = Ebean.find(Employee.class, this.getDoctorId());
        agreementNumber.setDoctor(doctor);
        agreementNumber.setAgreementNumber(this.getAgreementNumber());
        return agreementNumber;
    }
}
