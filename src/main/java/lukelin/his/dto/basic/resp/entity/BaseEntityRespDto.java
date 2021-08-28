package lukelin.his.dto.basic.resp.entity;

import lukelin.his.dto.basic.resp.setup.DictionaryDto;
import lukelin.his.dto.basic.resp.setup.UnitOfMeasureDto;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BaseEntityRespDto {
    private UUID uuid;

    private Integer code;

    private String name;

    private String searchCode;

    private boolean enabled;

    private BigDecimal listPrice;

    private BigDecimal listPriceMinUom;

    private DictionaryDto feeType;

    private UnitOfMeasureDto minSizeUom;

    private String centerCode;

    private String centerName;

    private BigDecimal centerPrice;

    private BigDecimal pendingListPrice;

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public BigDecimal getPendingListPrice() {
        return pendingListPrice;
    }

    public void setPendingListPrice(BigDecimal pendingListPrice) {
        this.pendingListPrice = pendingListPrice;
    }

    public BigDecimal getCenterPrice() {
        return centerPrice;
    }

    public void setCenterPrice(BigDecimal centerPrice) {
        this.centerPrice = centerPrice;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public BigDecimal getListPriceMinUom() {
        return listPriceMinUom;
    }

    public void setListPriceMinUom(BigDecimal listPriceMinUom) {
        this.listPriceMinUom = listPriceMinUom;
    }

    public UnitOfMeasureDto getMinSizeUom() {
        return minSizeUom;
    }

    public void setMinSizeUom(UnitOfMeasureDto minSizeUom) {
        this.minSizeUom = minSizeUom;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public DictionaryDto getFeeType() {
        return feeType;
    }

    public void setFeeType(DictionaryDto feeType) {
        this.feeType = feeType;
    }
}
