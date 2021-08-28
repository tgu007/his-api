package lukelin.his.dto.account.request;

import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import java.util.List;

public class AutoFeeSaveDto {
    private FeeSaveDto feeSaveDto;

    private Integer quantity;

    private List<DictionaryDto> executeDayDtoList;

    public List<DictionaryDto> getExecuteDayDtoList() {
        return executeDayDtoList;
    }

    public void setExecuteDayDtoList(List<DictionaryDto> executeDayDtoList) {
        this.executeDayDtoList = executeDayDtoList;
    }

    public FeeSaveDto getFeeSaveDto() {
        return feeSaveDto;
    }

    public void setFeeSaveDto(FeeSaveDto feeSaveDto) {
        this.feeSaveDto = feeSaveDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
