package lukelin.his.domain.entity.basic.ward;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.basic.resp.ward.PatientSignInWardRoomDto;
import lukelin.his.dto.basic.resp.ward.WardRoomBedDto;
import lukelin.his.dto.basic.resp.ward.WardRoomDto;
import lukelin.his.dto.signin.response.TreePatientNodeDto;
import lukelin.his.dto.signin.response.TreeWardRoomNodeDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.ward_room")
public class WardRoom extends BaseCodeEntity implements DtoConvertible<WardRoomDto> {

    private String comment;

    @Column(nullable = false)
    private Integer order;

    public WardRoom() {
        super();
    }

    public WardRoom(UUID uuid) {
        super();
        this.setUuid(uuid);
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @OneToMany(mappedBy = "wardRoom", cascade = CascadeType.ALL)
    private List<WardRoomBed> bedList;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    public List<WardRoomBed> getBedList() {
        return bedList;
    }

    public void setBedList(List<WardRoomBed> bedList) {
        this.bedList = bedList;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public WardRoomDto toDto() {
        WardRoomDto wardRoomDto = DtoUtils.convertRawDto(this);
        List<WardRoomBedDto> wardRoomBedList = new ArrayList<>();
        for (WardRoomBed wardRoomBed : this.getBedList())
            wardRoomBedList.add(wardRoomBed.toDto());
        wardRoomDto.setWardRoomBedList(wardRoomBedList);
        return wardRoomDto;
    }

    public TreeWardRoomNodeDto toTreeWardRoomNodeDto() {
        TreeWardRoomNodeDto wardRoomNode = new TreeWardRoomNodeDto();
        wardRoomNode.setTitle(this.getName());
        wardRoomNode.setKey(this.getUuid().toString());

        List<TreePatientNodeDto> patientNodeList = new ArrayList<>();
        wardRoomNode.setChildren(patientNodeList);
        for (WardRoomBed bed : this.getBedList()) {
            patientNodeList.add(bed.toTreePatientNodeDto());
        }
        return wardRoomNode;
    }

    public PatientSignInWardRoomDto toPatientWardRoomDto() {
        PatientSignInWardRoomDto patientWardRoom = new PatientSignInWardRoomDto();
        BeanUtils.copyPropertiesIgnoreNull(this, patientWardRoom);
        patientWardRoom.setWard(this.getWard().toPatientWardDto());
        return patientWardRoom;
    }
}
