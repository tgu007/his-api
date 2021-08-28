package lukelin.his.dto.basic.resp.setup;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.Dictionary;

import java.util.UUID;

public class DictionaryDto {
    private Integer id;

    private String code;

    private String name;

    private boolean defaultSelection;

    private Integer order;

    private DictionaryDto parent;

    private String searchCode;

    private Integer frequency;

    private String extraInfo;

    private UUID drgSignOutReasonId;

    public UUID getDrgSignOutReasonId() {
        return drgSignOutReasonId;
    }

    public void setDrgSignOutReasonId(UUID drgSignOutReasonId) {
        this.drgSignOutReasonId = drgSignOutReasonId;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public DictionaryDto getParent() {
        return parent;
    }

    public void setParent(DictionaryDto parent) {
        this.parent = parent;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Dictionary toEntity() {
        Dictionary dictionary = new Dictionary();
        BeanUtils.copyPropertiesIgnoreNull(this, dictionary);
        return dictionary;
    }
}
