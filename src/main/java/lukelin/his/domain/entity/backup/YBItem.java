package lukelin.his.domain.entity.backup;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "backup.ybitem")
public class YBItem {
    private String code;

    private String name;

    private String start;

    private String end;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
