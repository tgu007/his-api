package lukelin.his.dto.signin.response;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.patient_sign_in.PatientContact;

import java.util.UUID;

public class PatientContactDto {
    private UUID uuid;

    private String name;

    public String phoneNumber;

    private String address;

    public String relationship;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public PatientContact toEntity() {
        PatientContact patientContact = new PatientContact();
        BeanUtils.copyPropertiesIgnoreNull(this, patientContact);
        return patientContact;
    }
}
