package lukelin.his.domain.entity.basic;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.DictionaryGroupDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "basic.dic_type")
public class DictionaryGroup implements DtoConvertible<DictionaryGroupDto> {
    @Id
    private Integer id;

    @Column(nullable = false, length = 50)
    private String code;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Dictionary> dictionaryList;

    public List<Dictionary> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(List<Dictionary> dictionaryList) {
        this.dictionaryList = dictionaryList;
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

    @Override
    public DictionaryGroupDto toDto() {
        DictionaryGroupDto responseDto = DtoUtils.convertRawDto(this);
        List<DictionaryDto> dictionaryDtoList = new ArrayList<>();
        for (Dictionary dictionary : this.dictionaryList)
            dictionaryDtoList.add(dictionary.toDto());
        responseDto.setDictionaryDtoList(dictionaryDtoList);
        return responseDto;
    }
}
