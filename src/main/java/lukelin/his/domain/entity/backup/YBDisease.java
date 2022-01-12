package lukelin.his.domain.entity.backup;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "backup.ybdisease")
public class YBDisease {
    private String id;

    private String code;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
