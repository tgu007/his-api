package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseIdEntity;
import lukelin.his.dto.signin.response.PatientContactDto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "patient_sign_in.patient_contact")
public class PatientContact extends BaseIdEntity implements DtoConvertible<PatientContactDto> {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    public String phoneNumber;

    private String address;

    @Column(nullable = false, length = 50)
    public String relationship;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    @Override
    public PatientContactDto toDto() {
        PatientContactDto contactDto = DtoUtils.convertRawDto(this);
        return contactDto;
    }
}
