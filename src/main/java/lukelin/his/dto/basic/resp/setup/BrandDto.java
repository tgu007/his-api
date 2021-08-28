package lukelin.his.dto.basic.resp.setup;

import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.basic.codeEntity.Brand;
import lukelin.his.domain.enums.Basic.InventoryEntityType;

public class BrandDto extends BaseCodeDto {
    private String searchCode;

    private InventoryEntityType type;

    public InventoryEntityType getType() {
        return type;
    }

    public void setType(InventoryEntityType type) {
        this.type = type;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }


    public Brand toEntity() {
        Brand brand = new Brand();
        BeanUtils.copyPropertiesIgnoreNull(this, brand);
        return brand;
    }
}
