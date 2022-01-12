package lukelin.his.domain.entity.patient_sign_in;


import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.signin.response.PatientContactDto;
import lukelin.his.dto.signin.response.PatientDto;

import javax.persistence.*;
import java.util.*;

@javax.persistence.Entity
@Table(name = "patient_sign_in.patient")
public class Patient extends BaseEntity implements DtoConvertible<PatientDto> {
    public Patient(UUID uuid) {
        this.setUuid(uuid);
    }

    public Patient() {
    }

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "ethnic_id", nullable = false)
    private Dictionary ethnic;

    @ManyToOne
    @JoinColumn(name = "marriage_status_id", nullable = false)
    private Dictionary marriageStatus;

    @ManyToOne
    @JoinColumn(name = "occupation_id")
    private Dictionary occupation;

    @ManyToOne
    @JoinColumn(name = "id_type_id", nullable = false)
    private Dictionary idType;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Dictionary gender;

    @Column(nullable = false)
    private String idNumber;

    @Column(nullable = false, length = 50)
    private String domicile;

    @Column(name = "address_residential", nullable = false)
    private String address;

    @Column(name = "work_unit")
    private String placeOfWork;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientContact> patientContactList;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientSignIn> signInHistoryList;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "native_place")
    private String nativePlace;

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public List<PatientSignIn> getSignInHistoryList() {
        return signInHistoryList;
    }

    public void setSignInHistoryList(List<PatientSignIn> signInHistoryList) {
        this.signInHistoryList = signInHistoryList;
    }

    public Dictionary getGender() {
        return gender;
    }

    public void setGender(Dictionary gender) {
        this.gender = gender;
    }

    public List<PatientContact> getPatientContactList() {
        return patientContactList;
    }

    public void setPatientContactList(List<PatientContact> patientContactList) {
        this.patientContactList = patientContactList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Dictionary getEthnic() {
        return ethnic;
    }

    public void setEthnic(Dictionary ethnic) {
        this.ethnic = ethnic;
    }

    public Dictionary getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Dictionary marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public Dictionary getOccupation() {
        return occupation;
    }

    public void setOccupation(Dictionary occupation) {
        this.occupation = occupation;
    }

    public Dictionary getIdType() {
        return idType;
    }

    public void setIdType(Dictionary idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }


    @Override
    public PatientDto toDto() {
        PatientDto patientDto = DtoUtils.convertRawDto(this);
        if (this.ethnic != null)
            patientDto.setEthnicDto(this.ethnic.toDto());
        if (this.marriageStatus != null)
            patientDto.setMarriageStatusDto(this.marriageStatus.toDto());
        if (this.occupation != null)
            patientDto.setOccupationDto(this.occupation.toDto());
        if (this.gender != null)
            patientDto.setGenderDto(this.gender.toDto());
        if (this.idType != null)
            patientDto.setIdTypeDto(this.idType.toDto());

        Optional<PatientSignIn> optionalCurrentSignIn =
                this.getSignInHistoryList().stream().filter(s -> s.getStatus() != PatientSignInStatus.signedOut && s.getStatus() != PatientSignInStatus.canceled).max(Comparator.comparing(PatientSignIn::getWhenCreated));

        optionalCurrentSignIn.ifPresent(patientSignIn -> patientDto.setCurrentSignInId(patientSignIn.getUuid()));

        List<PatientContactDto> contactDtoList = new ArrayList<>();
        for (PatientContact contact : this.patientContactList)
            contactDtoList.add(contact.toDto());
        patientDto.setPatientContactDtoList(contactDtoList);
        return patientDto;
    }
}
