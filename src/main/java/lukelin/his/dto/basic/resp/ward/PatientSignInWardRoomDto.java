package lukelin.his.dto.basic.resp.ward;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

public class PatientSignInWardRoomDto extends BaseCodeDto {
    private String comment;

    private PatientSignInWardDto ward;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PatientSignInWardDto getWard() {
        return ward;
    }

    public void setWard(PatientSignInWardDto ward) {
        this.ward = ward;
    }
}
