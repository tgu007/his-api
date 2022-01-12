package lukelin.his.dto.account.response;

import java.math.BigDecimal;

public class InvoiceDto {
    private Boolean selfPay;

    private String ybjsh;

    private String ybType;

    private String invoiceYear;

    private String invoiceMonth;

    private String invoiceDay;

    private int signInYear;

    private int signInMonth;

    private int signInDay;

    private int signOutYear;

    private int signOutMonth;

    private int signOutDay;

    private String patientName;

    private String workUnit;

    private String signInNumber;

    private String signInDate;

    private String signOutDate;

    private BigDecimal feeXY;

    private BigDecimal feeXYSelf;

    private BigDecimal feeZC;

    private BigDecimal feeZCSelf;

    private BigDecimal feeZCY;

    private BigDecimal feeZCYSelf;

    private BigDecimal feeCW;

    private BigDecimal feeCWSelf;

    private BigDecimal feeZhenCha;

    private BigDecimal feeZhenChaSelf;

    private BigDecimal feeHY;

    private BigDecimal feeHYSelf;

    private BigDecimal feeZL;

    private BigDecimal feeZLSelf;

    private BigDecimal feeHL;

    private BigDecimal feeHLSelf;

    private BigDecimal feeHC;

    private BigDecimal feeHCSelf;

    private BigDecimal feeZhenliao;

    private BigDecimal feeZhenliaoSelf;

    private BigDecimal feeQT;

    private BigDecimal feeQTSelf;

    private BigDecimal feeTotal;

    private BigDecimal feeTotalSelf;

    private BigDecimal feeShuYang;

    private BigDecimal feeShuYangSelf;

    private String invoiceNumber;

    private String totalAmountChinese;

    private BigDecimal totalPayment = BigDecimal.ZERO;

    private BigDecimal remainingPayment = BigDecimal.ZERO;

    private BigDecimal refund = BigDecimal.ZERO;

    private String wardInfo;


    private BigDecimal cashAmount;

    private BigDecimal fromBalanceThisYear;

    private BigDecimal fromBalancePreviousYear;

    private BigDecimal civilSubsidy;

    private BigDecimal seriousDiseaseSubsidy;

    private BigDecimal overallPayment;

    private BigDecimal ybSelfAmountAll;

    private BigDecimal ybSelfAmountRatio;

    private BigDecimal ybSelfAmount;

    private BigDecimal cardBalance;

    private BigDecimal ybAccessAmount;

    private BigDecimal retirementFund;

    private BigDecimal medicalSubsidy;

    private BigDecimal specialCareSubsidy;

    private BigDecimal supplementInsurance;

    private BigDecimal familyFund;

    private String fundInfo1;

    private String fundInfo2;

    private String fundInfo3;

    private String gender;

    private String departmentName;

    private String totalSignInDays;

    private BigDecimal totalFundAmount;

    public BigDecimal getTotalFundAmount() {
        return totalFundAmount;
    }

    public void setTotalFundAmount(BigDecimal totalFundAmount) {
        this.totalFundAmount = totalFundAmount;
    }

    public int getSignInYear() {
        return signInYear;
    }

    public void setSignInYear(int signInYear) {
        this.signInYear = signInYear;
    }

    public int getSignInMonth() {
        return signInMonth;
    }

    public void setSignInMonth(int signInMonth) {
        this.signInMonth = signInMonth;
    }

    public int getSignInDay() {
        return signInDay;
    }

    public void setSignInDay(int signInDay) {
        this.signInDay = signInDay;
    }

    public int getSignOutYear() {
        return signOutYear;
    }

    public void setSignOutYear(int signOutYear) {
        this.signOutYear = signOutYear;
    }

    public int getSignOutMonth() {
        return signOutMonth;
    }

    public void setSignOutMonth(int signOutMonth) {
        this.signOutMonth = signOutMonth;
    }

    public int getSignOutDay() {
        return signOutDay;
    }

    public void setSignOutDay(int signOutDay) {
        this.signOutDay = signOutDay;
    }

    public String getTotalSignInDays() {
        return totalSignInDays;
    }

    public void setTotalSignInDays(String totalSignInDays) {
        this.totalSignInDays = totalSignInDays;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Boolean getSelfPay() {
        return selfPay;
    }

    public void setSelfPay(Boolean selfPay) {
        this.selfPay = selfPay;
    }

    public BigDecimal getYbSelfAmount() {
        return ybSelfAmount;
    }

    public void setYbSelfAmount(BigDecimal ybSelfAmount) {
        this.ybSelfAmount = ybSelfAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getFromBalanceThisYear() {
        return fromBalanceThisYear;
    }

    public void setFromBalanceThisYear(BigDecimal fromBalanceThisYear) {
        this.fromBalanceThisYear = fromBalanceThisYear;
    }

    public BigDecimal getFromBalancePreviousYear() {
        return fromBalancePreviousYear;
    }

    public void setFromBalancePreviousYear(BigDecimal fromBalancePreviousYear) {
        this.fromBalancePreviousYear = fromBalancePreviousYear;
    }

    public BigDecimal getCivilSubsidy() {
        return civilSubsidy;
    }

    public void setCivilSubsidy(BigDecimal civilSubsidy) {
        this.civilSubsidy = civilSubsidy;
    }

    public BigDecimal getSeriousDiseaseSubsidy() {
        return seriousDiseaseSubsidy;
    }

    public void setSeriousDiseaseSubsidy(BigDecimal seriousDiseaseSubsidy) {
        this.seriousDiseaseSubsidy = seriousDiseaseSubsidy;
    }

    public BigDecimal getOverallPayment() {
        return overallPayment;
    }

    public void setOverallPayment(BigDecimal overallPayment) {
        this.overallPayment = overallPayment;
    }

    public BigDecimal getYbSelfAmountAll() {
        return ybSelfAmountAll;
    }

    public void setYbSelfAmountAll(BigDecimal ybSelfAmountAll) {
        this.ybSelfAmountAll = ybSelfAmountAll;
    }

    public BigDecimal getYbSelfAmountRatio() {
        return ybSelfAmountRatio;
    }

    public void setYbSelfAmountRatio(BigDecimal ybSelfAmountRatio) {
        this.ybSelfAmountRatio = ybSelfAmountRatio;
    }

    public BigDecimal getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(BigDecimal cardBalance) {
        this.cardBalance = cardBalance;
    }

    public BigDecimal getYbAccessAmount() {
        return ybAccessAmount;
    }

    public void setYbAccessAmount(BigDecimal ybAccessAmount) {
        this.ybAccessAmount = ybAccessAmount;
    }

    public BigDecimal getRetirementFund() {
        return retirementFund;
    }

    public void setRetirementFund(BigDecimal retirementFund) {
        this.retirementFund = retirementFund;
    }

    public BigDecimal getMedicalSubsidy() {
        return medicalSubsidy;
    }

    public void setMedicalSubsidy(BigDecimal medicalSubsidy) {
        this.medicalSubsidy = medicalSubsidy;
    }

    public BigDecimal getSpecialCareSubsidy() {
        return specialCareSubsidy;
    }

    public void setSpecialCareSubsidy(BigDecimal specialCareSubsidy) {
        this.specialCareSubsidy = specialCareSubsidy;
    }

    public BigDecimal getSupplementInsurance() {
        return supplementInsurance;
    }

    public void setSupplementInsurance(BigDecimal supplementInsurance) {
        this.supplementInsurance = supplementInsurance;
    }

    public BigDecimal getFamilyFund() {
        return familyFund;
    }

    public void setFamilyFund(BigDecimal familyFund) {
        this.familyFund = familyFund;
    }

    public String getFundInfo1() {
        return fundInfo1;
    }

    public void setFundInfo1(String fundInfo1) {
        this.fundInfo1 = fundInfo1;
    }

    public String getFundInfo2() {
        return fundInfo2;
    }

    public void setFundInfo2(String fundInfo2) {
        this.fundInfo2 = fundInfo2;
    }

    public String getFundInfo3() {
        return fundInfo3;
    }

    public void setFundInfo3(String fundInfo3) {
        this.fundInfo3 = fundInfo3;
    }

    public String getWardInfo() {
        return wardInfo;
    }

    public void setWardInfo(String wardInfo) {
        this.wardInfo = wardInfo;
    }

    public String getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(String signOutDate) {
        this.signOutDate = signOutDate;
    }

    public BigDecimal getFeeShuYang() {
        return feeShuYang;
    }

    public void setFeeShuYang(BigDecimal feeShuYang) {
        this.feeShuYang = feeShuYang;
    }

    public BigDecimal getFeeShuYangSelf() {
        return feeShuYangSelf;
    }

    public void setFeeShuYangSelf(BigDecimal feeShuYangSelf) {
        this.feeShuYangSelf = feeShuYangSelf;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(BigDecimal remainingPayment) {
        this.remainingPayment = remainingPayment;
    }

    public BigDecimal getRefund() {
        return refund;
    }

    public void setRefund(BigDecimal refund) {
        this.refund = refund;
    }

    public String getYbjsh() {
        return ybjsh;
    }

    public void setYbjsh(String ybjsh) {
        this.ybjsh = ybjsh;
    }

    public String getYbType() {
        return ybType;
    }

    public void setYbType(String ybType) {
        this.ybType = ybType;
    }

    public String getInvoiceYear() {
        return invoiceYear;
    }

    public void setInvoiceYear(String invoiceYear) {
        this.invoiceYear = invoiceYear;
    }

    public String getInvoiceMonth() {
        return invoiceMonth;
    }

    public void setInvoiceMonth(String invoiceMonth) {
        this.invoiceMonth = invoiceMonth;
    }

    public String getInvoiceDay() {
        return invoiceDay;
    }

    public void setInvoiceDay(String invoiceDay) {
        this.invoiceDay = invoiceDay;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getSignInNumber() {
        return signInNumber;
    }

    public void setSignInNumber(String signInNumber) {
        this.signInNumber = signInNumber;
    }

    public String getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(String signInDate) {
        this.signInDate = signInDate;
    }

    public BigDecimal getFeeXY() {
        return feeXY;
    }

    public void setFeeXY(BigDecimal feeXY) {
        this.feeXY = feeXY;
    }

    public BigDecimal getFeeXYSelf() {
        return feeXYSelf;
    }

    public void setFeeXYSelf(BigDecimal feeXYSelf) {
        this.feeXYSelf = feeXYSelf;
    }

    public BigDecimal getFeeZC() {
        return feeZC;
    }

    public void setFeeZC(BigDecimal feeZC) {
        this.feeZC = feeZC;
    }

    public BigDecimal getFeeZCSelf() {
        return feeZCSelf;
    }

    public void setFeeZCSelf(BigDecimal feeZCSelf) {
        this.feeZCSelf = feeZCSelf;
    }

    public BigDecimal getFeeZCY() {
        return feeZCY;
    }

    public void setFeeZCY(BigDecimal feeZCY) {
        this.feeZCY = feeZCY;
    }

    public BigDecimal getFeeZCYSelf() {
        return feeZCYSelf;
    }

    public void setFeeZCYSelf(BigDecimal feeZCYSelf) {
        this.feeZCYSelf = feeZCYSelf;
    }

    public BigDecimal getFeeCW() {
        return feeCW;
    }

    public void setFeeCW(BigDecimal feeCW) {
        this.feeCW = feeCW;
    }

    public BigDecimal getFeeCWSelf() {
        return feeCWSelf;
    }

    public void setFeeCWSelf(BigDecimal feeCWSelf) {
        this.feeCWSelf = feeCWSelf;
    }

    public BigDecimal getFeeZhenCha() {
        return feeZhenCha;
    }

    public void setFeeZhenCha(BigDecimal feeZhenCha) {
        this.feeZhenCha = feeZhenCha;
    }

    public BigDecimal getFeeZhenChaSelf() {
        return feeZhenChaSelf;
    }

    public void setFeeZhenChaSelf(BigDecimal feeZhenChaSelf) {
        this.feeZhenChaSelf = feeZhenChaSelf;
    }

    public BigDecimal getFeeHY() {
        return feeHY;
    }

    public void setFeeHY(BigDecimal feeHY) {
        this.feeHY = feeHY;
    }

    public BigDecimal getFeeHYSelf() {
        return feeHYSelf;
    }

    public void setFeeHYSelf(BigDecimal feeHYSelf) {
        this.feeHYSelf = feeHYSelf;
    }

    public BigDecimal getFeeZL() {
        return feeZL;
    }

    public void setFeeZL(BigDecimal feeZL) {
        this.feeZL = feeZL;
    }

    public BigDecimal getFeeZLSelf() {
        return feeZLSelf;
    }

    public void setFeeZLSelf(BigDecimal feeZLSelf) {
        this.feeZLSelf = feeZLSelf;
    }

    public BigDecimal getFeeHL() {
        return feeHL;
    }

    public void setFeeHL(BigDecimal feeHL) {
        this.feeHL = feeHL;
    }

    public BigDecimal getFeeHLSelf() {
        return feeHLSelf;
    }

    public void setFeeHLSelf(BigDecimal feeHLSelf) {
        this.feeHLSelf = feeHLSelf;
    }

    public BigDecimal getFeeHC() {
        return feeHC;
    }

    public void setFeeHC(BigDecimal feeHC) {
        this.feeHC = feeHC;
    }

    public BigDecimal getFeeHCSelf() {
        return feeHCSelf;
    }

    public void setFeeHCSelf(BigDecimal feeHCSelf) {
        this.feeHCSelf = feeHCSelf;
    }

    public BigDecimal getFeeZhenliao() {
        return feeZhenliao;
    }

    public void setFeeZhenliao(BigDecimal feeZhenliao) {
        this.feeZhenliao = feeZhenliao;
    }

    public BigDecimal getFeeZhenliaoSelf() {
        return feeZhenliaoSelf;
    }

    public void setFeeZhenliaoSelf(BigDecimal feeZhenliaoSelf) {
        this.feeZhenliaoSelf = feeZhenliaoSelf;
    }

    public BigDecimal getFeeQT() {
        return feeQT;
    }

    public void setFeeQT(BigDecimal feeQT) {
        this.feeQT = feeQT;
    }

    public BigDecimal getFeeQTSelf() {
        return feeQTSelf;
    }

    public void setFeeQTSelf(BigDecimal feeQTSelf) {
        this.feeQTSelf = feeQTSelf;
    }

    public BigDecimal getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(BigDecimal feeTotal) {
        this.feeTotal = feeTotal;
    }

    public BigDecimal getFeeTotalSelf() {
        return feeTotalSelf;
    }

    public void setFeeTotalSelf(BigDecimal feeTotalSelf) {
        this.feeTotalSelf = feeTotalSelf;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTotalAmountChinese() {
        return totalAmountChinese;
    }

    public void setTotalAmountChinese(String totalAmountChinese) {
        this.totalAmountChinese = totalAmountChinese;
    }
}
