package lukelin.his.domain.Interfaces;

import lukelin.his.domain.entity.basic.entity.BasePriceLog;

import java.math.BigDecimal;

public interface ChargeableEntityInterface {
    BigDecimal getListPrice();

    BigDecimal getPendingListPrice();

    void setListPrice(BigDecimal price);

    void setPendingListPrice(BigDecimal price);

    BasePriceLog getNewPriceLog();

    default BasePriceLog confirmNewPrice() {
        BasePriceLog newLog = this.getNewPriceLog();
        newLog.setNewPrice(this.getPendingListPrice());
        newLog.setOldPrice(this.getListPrice());
        this.setListPrice(this.getPendingListPrice());
        this.setPendingListPrice(BigDecimal.ZERO);
        return newLog;
    }
}
