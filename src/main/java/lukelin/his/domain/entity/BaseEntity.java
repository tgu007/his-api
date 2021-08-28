package lukelin.his.domain.entity;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import lukelin.his.domain.entity.prescription.Prescription;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity extends BaseIdEntity {

    @Column(name = "when_created", nullable = false)
    @WhenCreated
    private Date whenCreated;

    @Column(name = "when_modified")
    @WhenModified
    private Date whenModified;

    @Column(name = "who_created", nullable = false)
    @WhoCreated
    private String whoCreated;

    @Column(name = "who_modified")
    @WhoModified
    private String whoModified;

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Date getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Date whenModified) {
        this.whenModified = whenModified;
    }

    public String getWhoCreated() {
        return whoCreated;
    }

    public void setWhoCreated(String whoCreated) {
        this.whoCreated = whoCreated;
    }

    public String getWhoModified() {
        return whoModified;
    }

    public void setWhoModified(String whoModified) {
        this.whoModified = whoModified;
    }

    public String getWhoCreatedName() {
        return this.getNameInfo(this.getWhoCreated());
    }

    public String getWhoModifiedName() {
        return this.getNameInfo(this.getWhoModified());
    }

    public UUID getWhoCreatedId() {
        return this.getId(this.getWhoCreated());
    }

    public UUID getWhoModifiedId() {
        return this.getId(this.getWhoModified());
    }

    private String getNameInfo(String fullInfo) {
        if (!StringUtils.isEmpty(fullInfo) && fullInfo.contains("(") && fullInfo.contains(")")) {
            Integer startIndex = fullInfo.indexOf("(");
            Integer lastIndex = fullInfo.lastIndexOf(")");
            return fullInfo.substring(startIndex + 1, lastIndex);
        }
        return "";
    }

    private UUID getId(String fullInfo) {
        if (!StringUtils.isEmpty(fullInfo) && fullInfo.contains("(") && fullInfo.contains(")")) {
            Integer startIndex = fullInfo.indexOf("(");
            return UUID.fromString(fullInfo.substring(0, startIndex - 1));
        }
        return null;
    }

}
