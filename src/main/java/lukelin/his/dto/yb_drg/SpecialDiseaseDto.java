package lukelin.his.dto.yb_drg;

import lukelin.his.dto.basic.resp.setup.BaseCodeDto;

public class SpecialDiseaseDto extends BaseCodeDto {
    private boolean defaultSelection;

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }
}
