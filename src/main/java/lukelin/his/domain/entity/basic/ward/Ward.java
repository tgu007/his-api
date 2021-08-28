package lukelin.his.domain.entity.basic.ward;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.dto.basic.resp.ward.PatientSignInWardDto;
import lukelin.his.dto.basic.resp.ward.WardDto;
import lukelin.his.dto.basic.resp.ward.WardRoomDto;
import lukelin.his.dto.mini_porgram.MiniPatientDto;
import lukelin.his.dto.mini_porgram.MiniWardDto;
import lukelin.his.dto.signin.response.TreeWardNodeDto;
import lukelin.his.dto.signin.response.TreeWardRoomNodeDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@javax.persistence.Entity
@Table(name = "basic.ward")
public class Ward extends BaseCodeEntity implements DtoConvertible<WardDto> {

    public Ward() {
        super();
    }

    public Ward(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    private String comment;

    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private List<WardRoom> roomList;

    @ManyToMany
    @JoinTable(name = "basic.ward_department",
            joinColumns = {@JoinColumn(name = "ward_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "department_id", referencedColumnName = "uuid")})
    private Set<DepartmentTreatment> departmentList;

    @Column(name = "sequence", nullable = false, insertable = false, updatable = false)
    private Integer sequence;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<DepartmentTreatment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(Set<DepartmentTreatment> departmentList) {
        this.departmentList = departmentList;
    }

    public List<WardRoom> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<WardRoom> roomList) {
        this.roomList = roomList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public WardDto toDto() {
        WardDto wardDto = DtoUtils.convertRawDto(this);
        List<WardRoomDto> wardRoomList = new ArrayList<>();
        for (WardRoom wardRoom : this.getRoomList())
            wardRoomList.add(wardRoom.toDto());
        wardDto.setWardRoomList(wardRoomList);
        return wardDto;
    }

    public TreeWardNodeDto toTreeWardNodeDto() {
        TreeWardNodeDto wardNode = new TreeWardNodeDto();
        wardNode.setTitle(this.getName());
        wardNode.setKey(this.getUuid().toString());

        List<TreeWardRoomNodeDto> roomNodeList = new ArrayList<>();
        wardNode.setChildren(roomNodeList);
        for (WardRoom wardRoom : this.getRoomList()) {
            roomNodeList.add(wardRoom.toTreeWardRoomNodeDto());
        }
        return wardNode;
    }

    public PatientSignInWardDto toPatientWardDto() {
        PatientSignInWardDto patientWard = new PatientSignInWardDto();
        BeanUtils.copyPropertiesIgnoreNull(this, patientWard);
        return patientWard;
    }

    public MiniWardDto toMiniWardDto(List<Prescription> wardPrescriptionList) {
        MiniWardDto wardDto = new MiniWardDto();
        BeanUtils.copyPropertiesIgnoreNull(this, wardDto);
        List<MiniPatientDto> patientList = new ArrayList<>();
        for (PatientSignIn patientSignIn : wardPrescriptionList.stream().map(Prescription::getPatientSignIn).distinct().sorted(comparing(p -> p.getPatient().getName())).collect(Collectors.toList())) {
            List<Prescription> patientPrescriptionList = wardPrescriptionList.stream().filter(p -> p.getPatientSignIn() == patientSignIn).distinct().collect(Collectors.toList());
            MiniPatientDto patientDto = patientSignIn.toMinPatientDto(patientPrescriptionList);
            patientList.add(patientDto);
        }
        wardDto.setPatientList(patientList);
        return wardDto;
    }
}
