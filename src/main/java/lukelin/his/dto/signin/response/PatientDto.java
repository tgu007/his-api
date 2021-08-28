package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.patient_sign_in.Patient;
import lukelin.his.domain.entity.patient_sign_in.PatientContact;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PatientDto {
    private UUID uuid;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private DictionaryDto ethnicDto;

    private DictionaryDto marriageStatusDto;

    private DictionaryDto occupationDto;

    private DictionaryDto idTypeDto;

    private DictionaryDto genderDto;

    private String idNumber;

    private String domicile;

    private String address;

    private String placeOfWork;

    private String birthPlace;

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

    private List<PatientContactDto> patientContactDtoList;

    public DictionaryDto getGenderDto() {
        return genderDto;
    }

    private UUID currentSignInId;

    public UUID getCurrentSignInId() {
        return currentSignInId;
    }

    public void setCurrentSignInId(UUID currentSignInId) {
        this.currentSignInId = currentSignInId;
    }

    public void setGenderDto(DictionaryDto genderDto) {
        this.genderDto = genderDto;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public DictionaryDto getEthnicDto() {
        return ethnicDto;
    }

    public void setEthnicDto(DictionaryDto ethnicDto) {
        this.ethnicDto = ethnicDto;
    }

    public DictionaryDto getMarriageStatusDto() {
        return marriageStatusDto;
    }

    public void setMarriageStatusDto(DictionaryDto marriageStatusDto) {
        this.marriageStatusDto = marriageStatusDto;
    }

    public DictionaryDto getOccupationDto() {
        return occupationDto;
    }

    public void setOccupationDto(DictionaryDto occupationDto) {
        this.occupationDto = occupationDto;
    }

    public DictionaryDto getIdTypeDto() {
        return idTypeDto;
    }

    public void setIdTypeDto(DictionaryDto idTypeDto) {
        this.idTypeDto = idTypeDto;
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

    public List<PatientContactDto> getPatientContactDtoList() {
        return patientContactDtoList;
    }

    public void setPatientContactDtoList(List<PatientContactDto> patientContactDtoList) {
        this.patientContactDtoList = patientContactDtoList;
    }

    public Patient toEntity() {
        Patient patient = new Patient();
        BeanUtils.copyPropertiesIgnoreNull(this, patient);
        List<PatientContact> patientContactList = new ArrayList<>();
        for (PatientContactDto patientContactDto : this.patientContactDtoList) {
            PatientContact patientContact = patientContactDto.toEntity();
            patientContact.setUuid(patientContact.getUuid());
            patientContactList.add(patientContact);
        }
        patient.setPatientContactList(patientContactList);
        patient.setEthnic(this.ethnicDto.toEntity());
        patient.setIdType(this.idTypeDto.toEntity());
        patient.setMarriageStatus(this.marriageStatusDto.toEntity());
        patient.setOccupation(this.occupationDto.toEntity());
        patient.setGender(this.genderDto.toEntity());
        return patient;
    }
}
