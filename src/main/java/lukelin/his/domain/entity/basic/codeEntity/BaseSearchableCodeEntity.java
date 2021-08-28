package lukelin.his.domain.entity.basic.codeEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseSearchableCodeEntity extends BaseCodeEntity {
    @Column(name = "search_code", nullable = false, length = 50)
    private String searchCode;

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }
}
