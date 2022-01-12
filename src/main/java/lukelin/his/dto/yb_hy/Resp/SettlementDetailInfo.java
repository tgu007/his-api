package lukelin.his.dto.yb_hy.Resp;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;

public class SettlementDetailInfo {
    private JSONArray settlementDetailList;

    public JSONArray getSettlementDetailList() {
        return settlementDetailList;
    }

    public void setSettlementDetailList(JSONArray settlementDetailList) {
        this.settlementDetailList = settlementDetailList;
    }

    private BigDecimal selfPayAmount1;

    private String selfPaymentRatio1;

    private BigDecimal fundPaymentAmount1;

    private String fundPaymentRatio1;

    private BigDecimal lineTotal1;

    private BigDecimal selfPayAmount2;

    private String selfPaymentRatio2;

    private BigDecimal fundPaymentAmount2;

    private String fundPaymentRatio2;

    private BigDecimal lineTotal2;

    private BigDecimal selfPayAmount3;

    private String selfPaymentRatio3;

    private BigDecimal fundPaymentAmount3;

    private String fundPaymentRatio3;

    private BigDecimal lineTotal3;

    private BigDecimal selfPayAmount4;

    private String selfPaymentRatio4;

    private BigDecimal fundPaymentAmount4;

    private String fundPaymentRatio4;

    private BigDecimal lineTotal4;

    private BigDecimal selfPayAmount5;

    private String selfPaymentRatio5;

    private BigDecimal fundPaymentAmount5;

    private String fundPaymentRatio5;

    private BigDecimal lineTotal5;

    private BigDecimal selfPayAmount6;

    private String selfPaymentRatio6;

    private BigDecimal fundPaymentAmount6;

    private String fundPaymentRatio6;

    private BigDecimal lineTotal6;

    private BigDecimal selfPayAmount7;

    private String selfPaymentRatio7;

    private BigDecimal fundPaymentAmount7;

    private String fundPaymentRatio7;

    private BigDecimal lineTotal7;

    private BigDecimal selfPayAmount8;

    private String selfPaymentRatio8;

    private BigDecimal fundPaymentAmount8;

    private String fundPaymentRatio8;

    private BigDecimal lineTotal8;

    public BigDecimal getSelfPayAmount8() {
        return selfPayAmount8;
    }

    public void setSelfPayAmount8(BigDecimal selfPayAmount8) {
        this.selfPayAmount8 = selfPayAmount8;
    }

    public String getSelfPaymentRatio8() {
        return selfPaymentRatio8;
    }

    public void setSelfPaymentRatio8(String selfPaymentRatio8) {
        this.selfPaymentRatio8 = selfPaymentRatio8;
    }

    public BigDecimal getFundPaymentAmount8() {
        return fundPaymentAmount8;
    }

    public void setFundPaymentAmount8(BigDecimal fundPaymentAmount8) {
        this.fundPaymentAmount8 = fundPaymentAmount8;
    }

    public String getFundPaymentRatio8() {
        return fundPaymentRatio8;
    }

    public void setFundPaymentRatio8(String fundPaymentRatio8) {
        this.fundPaymentRatio8 = fundPaymentRatio8;
    }

    public BigDecimal getLineTotal8() {
        return lineTotal8;
    }

    public void setLineTotal8(BigDecimal lineTotal8) {
        this.lineTotal8 = lineTotal8;
    }

    private BigDecimal getSelfTotal()
    {
        return selfPayAmount1.add(selfPayAmount2).add(selfPayAmount3).add(selfPayAmount4).add(selfPayAmount5).add(selfPayAmount6).add(selfPayAmount7);
    }

    private BigDecimal getFundTotal()
    {
        return fundPaymentAmount1.add(fundPaymentAmount2).add(fundPaymentAmount3).add(fundPaymentAmount4).add(fundPaymentAmount5).add(fundPaymentAmount6).add(fundPaymentAmount7);
    }

    private BigDecimal getTotal()
    {
        return  this.getSelfTotal().add(this.getFundTotal());
    }

    public BigDecimal getSelfPayAmount1() {
        return selfPayAmount1;
    }

    public void setSelfPayAmount1(BigDecimal selfPayAmount1) {
        this.selfPayAmount1 = selfPayAmount1;
    }

    public String getSelfPaymentRatio1() {
        return selfPaymentRatio1;
    }

    public void setSelfPaymentRatio1(String selfPaymentRatio1) {
        this.selfPaymentRatio1 = selfPaymentRatio1;
    }

    public BigDecimal getFundPaymentAmount1() {
        return fundPaymentAmount1;
    }

    public void setFundPaymentAmount1(BigDecimal fundPaymentAmount1) {
        this.fundPaymentAmount1 = fundPaymentAmount1;
    }

    public String getFundPaymentRatio1() {
        return fundPaymentRatio1;
    }

    public void setFundPaymentRatio1(String fundPaymentRatio1) {
        this.fundPaymentRatio1 = fundPaymentRatio1;
    }

    public BigDecimal getLineTotal1() {
        return lineTotal1;
    }

    public void setLineTotal1(BigDecimal lineTotal1) {
        this.lineTotal1 = lineTotal1;
    }

    public BigDecimal getSelfPayAmount2() {
        return selfPayAmount2;
    }

    public void setSelfPayAmount2(BigDecimal selfPayAmount2) {
        this.selfPayAmount2 = selfPayAmount2;
    }

    public String getSelfPaymentRatio2() {
        return selfPaymentRatio2;
    }

    public void setSelfPaymentRatio2(String selfPaymentRatio2) {
        this.selfPaymentRatio2 = selfPaymentRatio2;
    }

    public BigDecimal getFundPaymentAmount2() {
        return fundPaymentAmount2;
    }

    public void setFundPaymentAmount2(BigDecimal fundPaymentAmount2) {
        this.fundPaymentAmount2 = fundPaymentAmount2;
    }

    public String getFundPaymentRatio2() {
        return fundPaymentRatio2;
    }

    public void setFundPaymentRatio2(String fundPaymentRatio2) {
        this.fundPaymentRatio2 = fundPaymentRatio2;
    }

    public BigDecimal getLineTotal2() {
        return lineTotal2;
    }

    public void setLineTotal2(BigDecimal lineTotal2) {
        this.lineTotal2 = lineTotal2;
    }

    public BigDecimal getSelfPayAmount3() {
        return selfPayAmount3;
    }

    public void setSelfPayAmount3(BigDecimal selfPayAmount3) {
        this.selfPayAmount3 = selfPayAmount3;
    }

    public String getSelfPaymentRatio3() {
        return selfPaymentRatio3;
    }

    public void setSelfPaymentRatio3(String selfPaymentRatio3) {
        this.selfPaymentRatio3 = selfPaymentRatio3;
    }

    public BigDecimal getFundPaymentAmount3() {
        return fundPaymentAmount3;
    }

    public void setFundPaymentAmount3(BigDecimal fundPaymentAmount3) {
        this.fundPaymentAmount3 = fundPaymentAmount3;
    }

    public String getFundPaymentRatio3() {
        return fundPaymentRatio3;
    }

    public void setFundPaymentRatio3(String fundPaymentRatio3) {
        this.fundPaymentRatio3 = fundPaymentRatio3;
    }

    public BigDecimal getLineTotal3() {
        return lineTotal3;
    }

    public void setLineTotal3(BigDecimal lineTotal3) {
        this.lineTotal3 = lineTotal3;
    }

    public BigDecimal getSelfPayAmount4() {
        return selfPayAmount4;
    }

    public void setSelfPayAmount4(BigDecimal selfPayAmount4) {
        this.selfPayAmount4 = selfPayAmount4;
    }

    public String getSelfPaymentRatio4() {
        return selfPaymentRatio4;
    }

    public void setSelfPaymentRatio4(String selfPaymentRatio4) {
        this.selfPaymentRatio4 = selfPaymentRatio4;
    }

    public BigDecimal getFundPaymentAmount4() {
        return fundPaymentAmount4;
    }

    public void setFundPaymentAmount4(BigDecimal fundPaymentAmount4) {
        this.fundPaymentAmount4 = fundPaymentAmount4;
    }

    public String getFundPaymentRatio4() {
        return fundPaymentRatio4;
    }

    public void setFundPaymentRatio4(String fundPaymentRatio4) {
        this.fundPaymentRatio4 = fundPaymentRatio4;
    }

    public BigDecimal getLineTotal4() {
        return lineTotal4;
    }

    public void setLineTotal4(BigDecimal lineTotal4) {
        this.lineTotal4 = lineTotal4;
    }

    public BigDecimal getSelfPayAmount5() {
        return selfPayAmount5;
    }

    public void setSelfPayAmount5(BigDecimal selfPayAmount5) {
        this.selfPayAmount5 = selfPayAmount5;
    }

    public String getSelfPaymentRatio5() {
        return selfPaymentRatio5;
    }

    public void setSelfPaymentRatio5(String selfPaymentRatio5) {
        this.selfPaymentRatio5 = selfPaymentRatio5;
    }

    public BigDecimal getFundPaymentAmount5() {
        return fundPaymentAmount5;
    }

    public void setFundPaymentAmount5(BigDecimal fundPaymentAmount5) {
        this.fundPaymentAmount5 = fundPaymentAmount5;
    }

    public String getFundPaymentRatio5() {
        return fundPaymentRatio5;
    }

    public void setFundPaymentRatio5(String fundPaymentRatio5) {
        this.fundPaymentRatio5 = fundPaymentRatio5;
    }

    public BigDecimal getLineTotal5() {
        return lineTotal5;
    }

    public void setLineTotal5(BigDecimal lineTotal5) {
        this.lineTotal5 = lineTotal5;
    }

    public BigDecimal getSelfPayAmount6() {
        return selfPayAmount6;
    }

    public void setSelfPayAmount6(BigDecimal selfPayAmount6) {
        this.selfPayAmount6 = selfPayAmount6;
    }

    public String getSelfPaymentRatio6() {
        return selfPaymentRatio6;
    }

    public void setSelfPaymentRatio6(String selfPaymentRatio6) {
        this.selfPaymentRatio6 = selfPaymentRatio6;
    }

    public BigDecimal getFundPaymentAmount6() {
        return fundPaymentAmount6;
    }

    public void setFundPaymentAmount6(BigDecimal fundPaymentAmount6) {
        this.fundPaymentAmount6 = fundPaymentAmount6;
    }

    public String getFundPaymentRatio6() {
        return fundPaymentRatio6;
    }

    public void setFundPaymentRatio6(String fundPaymentRatio6) {
        this.fundPaymentRatio6 = fundPaymentRatio6;
    }

    public BigDecimal getLineTotal6() {
        return lineTotal6;
    }

    public void setLineTotal6(BigDecimal lineTotal6) {
        this.lineTotal6 = lineTotal6;
    }

    public BigDecimal getSelfPayAmount7() {
        return selfPayAmount7;
    }

    public void setSelfPayAmount7(BigDecimal selfPayAmount7) {
        this.selfPayAmount7 = selfPayAmount7;
    }

    public String getSelfPaymentRatio7() {
        return selfPaymentRatio7;
    }

    public void setSelfPaymentRatio7(String selfPaymentRatio7) {
        this.selfPaymentRatio7 = selfPaymentRatio7;
    }

    public BigDecimal getFundPaymentAmount7() {
        return fundPaymentAmount7;
    }

    public void setFundPaymentAmount7(BigDecimal fundPaymentAmount7) {
        this.fundPaymentAmount7 = fundPaymentAmount7;
    }

    public String getFundPaymentRatio7() {
        return fundPaymentRatio7;
    }

    public void setFundPaymentRatio7(String fundPaymentRatio7) {
        this.fundPaymentRatio7 = fundPaymentRatio7;
    }

    public BigDecimal getLineTotal7() {
        return lineTotal7;
    }

    public void setLineTotal7(BigDecimal lineTotal7) {
        this.lineTotal7 = lineTotal7;
    }
}
