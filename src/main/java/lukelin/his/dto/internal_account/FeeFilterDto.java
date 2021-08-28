package lukelin.his.dto.internal_account;

import lukelin.his.domain.enums.Fee.FeeStatus;

import java.util.Date;
import java.util.List;

public class FeeFilterDto extends PatientFilterDto{
    private Date startDate;

    private Date endDate;

    private String itemName;

    private List<FeeStatus> feeStatusList;

    public List<FeeStatus> getFeeStatusList() {
        return feeStatusList;
    }

    public void setFeeStatusList(List<FeeStatus> feeStatusList) {
        this.feeStatusList = feeStatusList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
