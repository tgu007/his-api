package lukelin.his.domain.entity.patient_sign_in;

import io.ebean.annotation.Sql;

import javax.persistence.Entity;

@Sql
@javax.persistence.Entity
public class PatientTemplateTagValue {
    private String tagName;

    private String tagValue;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
