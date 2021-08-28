package lukelin.his.dto.signin.response;

import java.math.BigDecimal;

public class Patient3024CheckRespDto extends PatientSignInListRespDto {


    private BigDecimal totalFeeAmount = BigDecimal.ZERO;

    private BigDecimal total3024Amount = BigDecimal.ZERO;

    private BigDecimal amount3024Percentage  = BigDecimal.ZERO;;

    private Boolean amount3024Valid = true;

    private Integer day3024Count = 0;

    private Integer signedInDays;

    private BigDecimal day3024Percentage  = BigDecimal.ZERO;;

    private Boolean day3024Valid = true;

    public BigDecimal getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(BigDecimal totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public BigDecimal getTotal3024Amount() {
        return total3024Amount;
    }

    public void setTotal3024Amount(BigDecimal total3024Amount) {
        this.total3024Amount = total3024Amount;
    }

    public BigDecimal getAmount3024Percentage() {
        return amount3024Percentage;
    }

    public void setAmount3024Percentage(BigDecimal amount3024Percentage) {
        this.amount3024Percentage = amount3024Percentage;
    }

    public Boolean getAmount3024Valid() {
        return amount3024Valid;
    }

    public void setAmount3024Valid(Boolean amount3024Valid) {
        this.amount3024Valid = amount3024Valid;
    }

    public Integer getDay3024Count() {
        return day3024Count;
    }

    public void setDay3024Count(Integer day3024Count) {
        this.day3024Count = day3024Count;
    }

    public Integer getSignedInDays() {
        return signedInDays;
    }

    public void setSignedInDays(Integer signedInDays) {
        this.signedInDays = signedInDays;
    }

    public BigDecimal getDay3024Percentage() {
        return day3024Percentage;
    }

    public void setDay3024Percentage(BigDecimal day3024Percentage) {
        this.day3024Percentage = day3024Percentage;
    }

    public Boolean getDay3024Valid() {
        return day3024Valid;
    }

    public void setDay3024Valid(Boolean day3024Valid) {
        this.day3024Valid = day3024Valid;
    }
}
