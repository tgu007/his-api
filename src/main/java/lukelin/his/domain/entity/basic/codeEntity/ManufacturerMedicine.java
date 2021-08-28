package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.dto.basic.resp.setup.ManufacturerMedicineRespDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_manufacturer_medicine")
public class ManufacturerMedicine extends BaseSearchableCodeEntity implements DtoConvertible<ManufacturerMedicineRespDto> {
    public ManufacturerMedicine(UUID uuid) {
        this.setUuid(uuid);
    }

    public ManufacturerMedicine() {
    }

    @Column(name = "yb_server_id")
    private String serverId;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public ManufacturerMedicineRespDto toDto() {
        ManufacturerMedicineRespDto responseDto = DtoUtils.convertRawDto(this);
        return responseDto;
    }
}
