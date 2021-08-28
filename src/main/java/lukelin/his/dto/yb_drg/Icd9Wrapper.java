package lukelin.his.dto.yb_drg;

import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.yb.ICD9RespDto;

public class Icd9Wrapper {
    private ICD9RespDto value;

    public ICD9RespDto getValue() {
        return value;
    }

    public void setValue(ICD9RespDto value) {
        this.value = value;
    }
}
