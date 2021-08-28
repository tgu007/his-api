package lukelin.his.dto.basic.resp.ward;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

public class PatientSignInBedDto extends BaseCodeDto {
    private String comment;

    private PatientSignInWardRoomDto wardRoom;

    public PatientSignInWardRoomDto getWardRoom() {
        return wardRoom;
    }

    public void setWardRoom(PatientSignInWardRoomDto wardRoom) {
        this.wardRoom = wardRoom;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
