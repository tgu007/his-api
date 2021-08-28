package lukelin.his.dto.account.response;

import java.math.BigDecimal;

public abstract class BaseFeeListDto {
    private String feeType;

    private String name;

    private BigDecimal quantity;

    private String uomName;

    private BigDecimal unitAmount;

    private String displayUnitAmount;

    private String displayQuantityInfo;

    private BigDecimal totalAmount;

    public String getDisplayUnitAmount() {
        return displayUnitAmount;
    }

    public void setDisplayUnitAmount(String displayUnitAmount) {
        this.displayUnitAmount = displayUnitAmount;
    }

    public String getDisplayQuantityInfo() {
        return displayQuantityInfo;
    }

    public void setDisplayQuantityInfo(String displayQuantityInfo) {
        this.displayQuantityInfo = displayQuantityInfo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
