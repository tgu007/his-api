package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.dto.basic.resp.setup.BrandDto;
import lukelin.his.dto.basic.resp.setup.FromHospitalRespDto;

import javax.persistence.Table;


@javax.persistence.Entity
@Table(name = "basic.code_from_hospital")
public class FromHospital extends BaseSearchableCodeEntity implements DtoConvertible<FromHospitalRespDto> {

    @Override
    public FromHospitalRespDto toDto() {
        FromHospitalRespDto dto = DtoUtils.convertRawDto(this);
        return dto;
    }
}
