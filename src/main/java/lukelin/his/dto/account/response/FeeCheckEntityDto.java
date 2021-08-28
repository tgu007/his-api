package lukelin.his.dto.account.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.system.Utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FeeCheckEntityDto {
    private EntityType entityType;

    private String feeTypeName;

    private String departmentModel;

    private String manufacturer;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date firstRecordDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date lastRecordDate;

    private Integer recordDayCount;

    private Integer recordCount;

    private BigDecimal totalAmount;

    private Integer duration;

    List<FeeListDto> feeList;

    private UUID feeEntityId;

    private String id;

    public String getDepartmentModel() {
        return departmentModel;
    }

    public void setDepartmentModel(String departmentModel) {
        this.departmentModel = departmentModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getFeeEntityId() {
        return feeEntityId;
    }

    public void setFeeEntityId(UUID feeEntityId) {
        this.feeEntityId = feeEntityId;
    }

    public List<FeeListDto> getFeeList() {
        return feeList;
    }

    public void setFeeList(List<FeeListDto> feeList) {
        this.feeList = feeList;
    }

    public String getFeeTypeName() {
        return feeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return Utils.findDaysBetween(this.firstRecordDate, this.lastRecordDate) + 1;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFirstRecordDate() {
        return firstRecordDate;
    }

    public void setFirstRecordDate(Date firstRecordDate) {
        this.firstRecordDate = firstRecordDate;
    }

    public Date getLastRecordDate() {
        return lastRecordDate;
    }

    public void setLastRecordDate(Date lastRecordDate) {
        this.lastRecordDate = lastRecordDate;
    }

    public Integer getRecordDayCount() {
        return recordDayCount;
    }

    public void setRecordDayCount(Integer recordDayCount) {
        this.recordDayCount = recordDayCount;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
