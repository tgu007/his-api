package lukelin.his.dto.basic.req.filter;

import lukelin.his.domain.enums.Basic.InventoryEntityType;
import lukelin.his.dto.basic.SearchCodeDto;

public class BrandFilterDto extends SearchCodeDto {
    private InventoryEntityType brandType;

    public InventoryEntityType getBrandType() {
        return brandType;
    }

    public void setBrandType(InventoryEntityType brandType) {
        this.brandType = brandType;
    }
}
