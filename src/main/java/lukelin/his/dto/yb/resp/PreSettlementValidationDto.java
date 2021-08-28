package lukelin.his.dto.yb.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lukelin.his.domain.enums.YB.FeeValidationErrorType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PreSettlementValidationDto {
   private List<FeeValidationDto> feeList;

   private BigDecimal hisTotalFee;

   private BigDecimal hisTotalCoveredFee;

   private BigDecimal ybTotalFee;

   private BigDecimal ybTotalCoveredFee;

   public List<FeeValidationDto> getFeeList() {
      return feeList;
   }

   public void setFeeList(List<FeeValidationDto> feeList) {
      this.feeList = feeList;
   }

   public BigDecimal getHisTotalFee() {
      return hisTotalFee;
   }

   public void setHisTotalFee(BigDecimal hisTotalFee) {
      this.hisTotalFee = hisTotalFee;
   }

   public BigDecimal getHisTotalCoveredFee() {
      return hisTotalCoveredFee;
   }

   public void setHisTotalCoveredFee(BigDecimal hisTotalCoveredFee) {
      this.hisTotalCoveredFee = hisTotalCoveredFee;
   }

   public BigDecimal getYbTotalFee() {
      return ybTotalFee;
   }

   public void setYbTotalFee(BigDecimal ybTotalFee) {
      this.ybTotalFee = ybTotalFee;
   }

   public BigDecimal getYbTotalCoveredFee() {
      return ybTotalCoveredFee;
   }

   public void setYbTotalCoveredFee(BigDecimal ybTotalCoveredFee) {
      this.ybTotalCoveredFee = ybTotalCoveredFee;
   }
}
