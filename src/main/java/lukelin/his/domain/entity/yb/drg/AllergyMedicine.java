package lukelin.his.domain.entity.yb.drg;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.codeEntity.BaseCodeEntity;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.yb_drg.AllergyMedicineDto;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb_drg.allergy_medicine")
public class AllergyMedicine extends BaseCodeEntity implements DtoConvertible<AllergyMedicineDto> {

    @Override
    public AllergyMedicineDto toDto() {
        AllergyMedicineDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
