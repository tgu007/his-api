package lukelin.his.dto.signin.response;

import lukelin.his.domain.entity.patient_sign_in.MedicalRecordTag;

import java.util.*;

public class MedicalRecordDto extends MedicalRecordListDto {
    private String content;

    private List<MedicalRecordTagDto> tagList;

    public List<MedicalRecordTagDto> getTagList() {
        return tagList;
    }

    public void setTagList(List<MedicalRecordTagDto> tagList) {
        this.tagList = tagList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
