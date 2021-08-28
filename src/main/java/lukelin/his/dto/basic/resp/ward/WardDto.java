package lukelin.his.dto.basic.resp.ward;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

import java.util.List;

public class WardDto extends BaseCodeDto {

    private String comment;

    private List<WardRoomDto> wardRoomList;

    public List<WardRoomDto> getWardRoomList() {
        return wardRoomList;
    }

    public void setWardRoomList(List<WardRoomDto> wardRoomList) {
        this.wardRoomList = wardRoomList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Ward toEntity() {
        Ward ward = new Ward();
        BeanUtils.copyPropertiesIgnoreNull(this, ward);
        return ward;
    }
}
