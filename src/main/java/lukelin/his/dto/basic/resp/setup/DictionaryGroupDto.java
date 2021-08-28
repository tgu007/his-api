package lukelin.his.dto.basic.resp.setup;

import java.util.List;

public class DictionaryGroupDto {
    private String code;

    private List<DictionaryDto> dictionaryDtoList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DictionaryDto> getDictionaryDtoList() {
        return dictionaryDtoList;
    }

    public void setDictionaryDtoList(List<DictionaryDto> dictionaryDtoList) {
        this.dictionaryDtoList = dictionaryDtoList;
    }
}
