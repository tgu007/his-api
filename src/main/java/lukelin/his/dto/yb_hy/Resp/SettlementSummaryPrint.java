package lukelin.his.dto.yb_hy.Resp;

public class SettlementSummaryPrint {
    private String identityType;

    private SettlementAccumulatedInfo accumulatedInfo;

    private SettlementFeeSummaryInfo feeSummaryInfo;

    private SettlementDetailInfo detailInfo;

    private SettlementBasicInfo basicInfo;

    private String ybAreaCode;

    public String getYbAreaCode() {
        return ybAreaCode;
    }

    public void setYbAreaCode(String ybAreaCode) {
        this.ybAreaCode = ybAreaCode;
    }

    public SettlementBasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(SettlementBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public SettlementAccumulatedInfo getAccumulatedInfo() {
        return accumulatedInfo;
    }

    public void setAccumulatedInfo(SettlementAccumulatedInfo accumulatedInfo) {
        this.accumulatedInfo = accumulatedInfo;
    }

    public SettlementFeeSummaryInfo getFeeSummaryInfo() {
        return feeSummaryInfo;
    }

    public void setFeeSummaryInfo(SettlementFeeSummaryInfo feeSummaryInfo) {
        this.feeSummaryInfo = feeSummaryInfo;
    }

    public SettlementDetailInfo getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(SettlementDetailInfo detailInfo) {
        this.detailInfo = detailInfo;
    }
}
