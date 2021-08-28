package lukelin.his.domain.entity.basic.ward;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignInBed;
import lukelin.his.dto.basic.resp.ward.PatientSignInBedDto;
import lukelin.his.dto.basic.resp.ward.WardRoomBedDto;
import lukelin.his.dto.signin.response.TreePatientNodeDto;
import lukelin.his.system.Utils;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.ward_room_bed")
public class WardRoomBed extends BaseCodeEntity implements DtoConvertible<WardRoomBedDto> {
    private String comment;

    @OneToMany(mappedBy = "wardRoomBed", cascade = CascadeType.ALL)
    private List<PatientSignInBed> patientSignInBedList;


    @ManyToOne
    @JoinColumn(name = "ward_room_id", nullable = false)
    private WardRoom wardRoom;

    @ManyToOne
    @JoinColumn(name = "current_patient_sign_in_id")
    private PatientSignIn currentSignIn;

    @Column(nullable = false, name = "order_by")
    private Integer order;

    @Column(name = "sequence", nullable = false, insertable = false, updatable = false)
    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "bed_fee_id")
    private Treatment treatment;

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public WardRoomBed() {
        super();
    }

    public WardRoomBed(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    public PatientSignIn getCurrentSignIn() {
        return currentSignIn;
    }

    public void setCurrentSignIn(PatientSignIn currentSignIn) {
        this.currentSignIn = currentSignIn;
    }

    public List<PatientSignInBed> getPatientSignInBedList() {
        return patientSignInBedList;
    }

    public void setPatientSignInBedList(List<PatientSignInBed> patientSignInBedList) {
        this.patientSignInBedList = patientSignInBedList;
    }

    public WardRoom getWardRoom() {
        return wardRoom;
    }

    public void setWardRoom(WardRoom wardRoom) {
        this.wardRoom = wardRoom;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public WardRoomBedDto toDto() {
        WardRoomBedDto wardRoomBedDto = DtoUtils.convertRawDto(this);
        if(this.getTreatment() != null)
            wardRoomBedDto.setTreatmentId(this.getTreatment().getUuid());
        if (this.getCurrentSignIn() != null)
            wardRoomBedDto.setCurrentSignIn(this.getCurrentSignIn().toDto());
        return wardRoomBedDto;
    }

    public String getFullWardInfo() {
        return this.getWardRoom().getWard().getName() + this.getName();
    }

    public TreePatientNodeDto toTreePatientNodeDto() {
        TreePatientNodeDto patientNode = new TreePatientNodeDto();
        patientNode.setLeaf(true);
        patientNode.setTitle(this.getName() + "：" + this.getCurrentSignIn().getPatient().getName());
        patientNode.setKey(this.getCurrentSignIn().getUuid().toString());
        return patientNode;
    }

    public PatientSignInBedDto toPatientBedDto() {
        PatientSignInBedDto patientBed = new PatientSignInBedDto();
        BeanUtils.copyPropertiesIgnoreNull(this, patientBed);
        patientBed.setWardRoom(this.wardRoom.toPatientWardRoomDto());
        return patientBed;
    }
}
