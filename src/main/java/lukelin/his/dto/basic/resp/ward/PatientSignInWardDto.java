package lukelin.his.dto.basic.resp.ward;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

public class PatientSignInWardDto extends BaseCodeDto {
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
