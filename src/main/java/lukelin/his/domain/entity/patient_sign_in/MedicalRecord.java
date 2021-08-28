package lukelin.his.domain.entity.patient_sign_in;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.dto.signin.response.MedicalRecordDto;
import lukelin.his.dto.signin.response.MedicalRecordListDto;
import lukelin.his.dto.signin.response.MedicalRecordTagDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "patient_sign_in.medical_record")
public class MedicalRecord extends BaseEntity implements DtoConvertible<MedicalRecordDto> {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private MedicalRecordType type;

    @ManyToOne
    @JoinColumn(name = "patient_sign_in_id", nullable = false)
    private PatientSignIn patientSignIn;

    @Column(name = "in_edit", nullable = false)
    private Boolean inEdit;

    @Column(name = "in_edit_by")
    private String inEditBy;

    @Column(name = "in_edit_when")
    private Date inEditWhen;


    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    private List<MedicalRecordTag> tagList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private MedicalRecordTemplate template;

    public List<MedicalRecordTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<MedicalRecordTag> tagList) {
        this.tagList = tagList;
    }

    public String getInEditBy() {
        return inEditBy;
    }

    public void setInEditBy(String inEditBy) {
        this.inEditBy = inEditBy;
    }

    public Date getInEditWhen() {
        return inEditWhen;
    }

    public void setInEditWhen(Date inEditWhen) {
        this.inEditWhen = inEditWhen;
    }


    public Boolean getInEdit() {
        return inEdit;
    }

    public void setInEdit(Boolean inEdit) {
        this.inEdit = inEdit;
    }

    public MedicalRecordTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MedicalRecordTemplate template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MedicalRecordType getType() {
        return type;
    }

    public void setType(MedicalRecordType type) {
        this.type = type;
    }

    public PatientSignIn getPatientSignIn() {
        return patientSignIn;
    }

    public void setPatientSignIn(PatientSignIn patientSignIn) {
        this.patientSignIn = patientSignIn;
    }

    @Override
    public MedicalRecordDto toDto() {
        MedicalRecordDto dto = DtoUtils.convertRawDto(this);
        this.setDtoValue(dto);
        List<MedicalRecordTagDto> tagList = new ArrayList<>();
        for (MedicalRecordTag tag : this.getTagList().stream().sorted(Comparator.comparing(MedicalRecordTag::getTagTime).reversed()).collect(Collectors.toList()))
            tagList.add(tag.toDto());
        dto.setTagList(tagList);
        return dto;
    }

    public MedicalRecordListDto toListDto() {
        MedicalRecordListDto dto = new MedicalRecordListDto();
        this.setDtoValue(dto);
        BeanUtils.copyPropertiesIgnoreNull(this, dto);
        return dto;
    }

    private void setDtoValue(MedicalRecordListDto dto) {
        if (this.template != null)
            dto.setTemplateId(this.template.getUuid());
        dto.setTypeId(this.type.getUuid());
        dto.setTypeName(this.type.getName());
        dto.setLastModified(this.getWhenModified());
        dto.setPatientSignInId(this.getPatientSignIn().getUuid());

    }

    public MedicalRecord toNewMedicalRecord(PatientSignIn patientSignIn) {
        MedicalRecord medicalRecord = new MedicalRecord();
        BeanUtils.copyPropertiesIgnoreNull(this, medicalRecord);
        medicalRecord.setUuid(null);
        medicalRecord.setInEditBy(null);
        medicalRecord.setInEdit(false);
        medicalRecord.setPatientSignIn(patientSignIn);
        medicalRecord.setType(this.getType());
        medicalRecord.setTemplate(this.getTemplate());
        medicalRecord.setName(medicalRecord.getName() +"(复制)");
        return medicalRecord;
    }
}
