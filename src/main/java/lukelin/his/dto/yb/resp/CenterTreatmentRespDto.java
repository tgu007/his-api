package lukelin.his.dto.yb.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CenterTreatmentRespDto {
    private UUID uuid;
    //中心项目内码
    private String code1;

    //标准编码
    private String code2;

    //收费项目名称
    private String name;

    //开始日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    //结束日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String model;

    private String manufacture;

    private String chargeLevel;

    private BigDecimal payRatio;

    public BigDecimal getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(BigDecimal payRatio) {
        this.payRatio = payRatio;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getChargeLevel() {
        return chargeLevel;
    }

    public void setChargeLevel(String chargeLevel) {
        this.chargeLevel = chargeLevel;
    }
}
