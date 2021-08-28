package lukelin.his.domain.entity.basic.codeEntity;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.dto.basic.resp.setup.ManufacturerItemRespDto;
import lukelin.his.dto.basic.resp.setup.ManufacturerMedicineRespDto;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "basic.code_manufacturer_item")
public class ManufacturerItem extends BaseSearchableCodeEntity implements DtoConvertible<ManufacturerItemRespDto> {
    public ManufacturerItem(UUID uuid) {
        this.setUuid(uuid);
    }

    public ManufacturerItem() {
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
    public ManufacturerItemRespDto toDto() {
        ManufacturerItemRespDto responseDto = DtoUtils.convertRawDto(this);
        return responseDto;
    }
}
