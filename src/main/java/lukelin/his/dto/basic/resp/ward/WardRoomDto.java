package lukelin.his.dto.basic.resp.ward;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.ward.WardRoom;
import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

import java.util.List;

public class WardRoomDto extends BaseCodeDto {

    private String comment;

    private List<WardRoomBedDto> wardRoomBedList;

    public List<WardRoomBedDto> getWardRoomBedList() {
        return wardRoomBedList;
    }

    public void setWardRoomBedList(List<WardRoomBedDto> wardRoomBedList) {
        this.wardRoomBedList = wardRoomBedList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public WardRoom toEntity() {
        WardRoom wardRoom = new WardRoom();
        BeanUtils.copyPropertiesIgnoreNull(this, wardRoom);
        return wardRoom;
    }
}
