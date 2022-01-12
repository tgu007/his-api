package lukelin.his.dto.yb_hy.Resp;

import java.math.BigDecimal;

public class SettlementAccumulatedInfo {
    private BigDecimal currentYearCount = BigDecimal.ZERO;

    private BigDecimal totalFee = BigDecimal.ZERO;

    private BigDecimal paidStartLine = BigDecimal.ZERO;

    private BigDecimal fundTongChou = BigDecimal.ZERO;

    private BigDecimal fundDEJJ = BigDecimal.ZERO;

    private BigDecimal fundDBBXHG = BigDecimal.ZERO;

    private BigDecimal fundYLJZ = BigDecimal.ZERO;

    private BigDecimal fdlj = BigDecimal.ZERO;

    private BigDecimal zcZifei = BigDecimal.ZERO;

    private BigDecimal zcZifu = BigDecimal.ZERO;

    private BigDecimal fundDBBXZF = BigDecimal.ZERO;

    public BigDecimal getZcZifei() {
        return zcZifei;
    }

    public void setZcZifei(BigDecimal zcZifei) {
        this.zcZifei = zcZifei;
    }

    public BigDecimal getZcZifu() {
        return zcZifu;
    }

    public void setZcZifu(BigDecimal zcZifu) {
        this.zcZifu = zcZifu;
    }

    public BigDecimal getFundDBBXZF() {
        return fundDBBXZF;
    }

    public void setFundDBBXZF(BigDecimal fundDBBXZF) {
        this.fundDBBXZF = fundDBBXZF;
    }

    public BigDecimal getFdlj() {
        return fdlj;
    }

    public void setFdlj(BigDecimal fdlj) {
        this.fdlj = fdlj;
    }

    public BigDecimal getCurrentYearCount() {
        return currentYearCount;
    }

    public void setCurrentYearCount(BigDecimal currentYearCount) {
        this.currentYearCount = currentYearCount;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getPaidStartLine() {
        return paidStartLine;
    }

    public void setPaidStartLine(BigDecimal paidStartLine) {
        this.paidStartLine = paidStartLine;
    }

    public BigDecimal getFundTongChou() {
        return fundTongChou;
    }

    public void setFundTongChou(BigDecimal fundTongChou) {
        this.fundTongChou = fundTongChou;
    }

    public BigDecimal getFundDEJJ() {
        return fundDEJJ;
    }

    public void setFundDEJJ(BigDecimal fundDEJJ) {
        this.fundDEJJ = fundDEJJ;
    }

    public BigDecimal getFundDBBXHG() {
        return fundDBBXHG;
    }

    public void setFundDBBXHG(BigDecimal fundDBBXHG) {
        this.fundDBBXHG = fundDBBXHG;
    }

    public BigDecimal getFundYLJZ() {
        return fundYLJZ;
    }

    public void setFundYLJZ(BigDecimal fundYLJZ) {
        this.fundYLJZ = fundYLJZ;
    }
}
