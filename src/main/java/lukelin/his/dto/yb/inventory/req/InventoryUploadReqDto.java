package lukelin.his.dto.yb.inventory.req;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class InventoryUploadReqDto {
    private UUID uuid;
    //用户端物资编 号
    private String CWZBH;

    //物资编号
    private String WZBH;

    //商品名称
    private String WZMC;

    //药品通用名
    private String TYMC;

    //规格名称
    private String GGMC;

    //生产商名称
    private String CDMC;

    //条形码
    private String TXM;

    //剂量单位
    private String JLDW;

    //常用剂量
    private String CYJL;

    //常用频次
    private String CYPC;

    //常用用法
    private String CYYF;

    //账簿单位
    private String ZBDW;

    //零售单价
    private BigDecimal LSDJ;

    //OTC
    private String OTC;

    //剂型
    private String JX;

    //复方标志
    private String FFBZ;

    //开票项目 01：西药费 02：中成药 03：中草药 17 材料 99 未知
    private String KPXM;

    //生产商编号
    private String GYSBH;

    //产地
    private String CD;

    //是否支持库存 拆零
    private String SFKCCL;

    //拆零单位
    private String CLDW;

    //拆零比例
    private BigDecimal CLBL;

    //拆零单价
    private BigDecimal CLDJ;

    //物资包装
    private String WZBZ;

    //注册商标
    private String ZCSB;

    //批准文号
    private String PZWH;

    //用药天数
    private String YYTS;

    //注销标志
    private String ZXBZ;

    //物资类别
    //0 药品，1 材料 2 器械，3 保健品
    private String WZLB;

    //转换比
    private BigDecimal ZHB;

    private List<InventoryUploadCenterInfo> DZXXY;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCWZBH() {
        return CWZBH;
    }

    public void setCWZBH(String CWZBH) {
        this.CWZBH = CWZBH;
    }

    public String getWZBH() {
        return WZBH;
    }

    public void setWZBH(String WZBH) {
        this.WZBH = WZBH;
    }

    public String getWZMC() {
        return WZMC;
    }

    public void setWZMC(String WZMC) {
        this.WZMC = WZMC;
    }

    public String getTYMC() {
        return TYMC;
    }

    public void setTYMC(String TYMC) {
        this.TYMC = TYMC;
    }

    public String getGGMC() {
        return GGMC;
    }

    public void setGGMC(String GGMC) {
        this.GGMC = GGMC;
    }

    public String getCDMC() {
        return CDMC;
    }

    public void setCDMC(String CDMC) {
        this.CDMC = CDMC;
    }

    public String getTXM() {
        return TXM;
    }

    public void setTXM(String TXM) {
        this.TXM = TXM;
    }

    public String getJLDW() {
        return JLDW;
    }

    public void setJLDW(String JLDW) {
        this.JLDW = JLDW;
    }

    public String getCYJL() {
        return CYJL;
    }

    public void setCYJL(String CYJL) {
        this.CYJL = CYJL;
    }

    public String getCYPC() {
        return CYPC;
    }

    public void setCYPC(String CYPC) {
        this.CYPC = CYPC;
    }

    public String getCYYF() {
        return CYYF;
    }

    public void setCYYF(String CYYF) {
        this.CYYF = CYYF;
    }

    public String getZBDW() {
        return ZBDW;
    }

    public void setZBDW(String ZBDW) {
        this.ZBDW = ZBDW;
    }

    public BigDecimal getLSDJ() {
        return LSDJ;
    }

    public void setLSDJ(BigDecimal LSDJ) {
        this.LSDJ = LSDJ;
    }

    public String getOTC() {
        return OTC;
    }

    public void setOTC(String OTC) {
        this.OTC = OTC;
    }

    public String getJX() {
        return JX;
    }

    public void setJX(String JX) {
        this.JX = JX;
    }

    public String getFFBZ() {
        return FFBZ;
    }

    public void setFFBZ(String FFBZ) {
        this.FFBZ = FFBZ;
    }

    public String getKPXM() {
        return KPXM;
    }

    public void setKPXM(String KPXM) {
        this.KPXM = KPXM;
    }

    public String getGYSBH() {
        return GYSBH;
    }

    public void setGYSBH(String GYSBH) {
        this.GYSBH = GYSBH;
    }

    public String getCD() {
        return CD;
    }

    public void setCD(String CD) {
        this.CD = CD;
    }

    public String getSFKCCL() {
        return SFKCCL;
    }

    public void setSFKCCL(String SFKCCL) {
        this.SFKCCL = SFKCCL;
    }

    public String getCLDW() {
        return CLDW;
    }

    public void setCLDW(String CLDW) {
        this.CLDW = CLDW;
    }

    public BigDecimal getCLBL() {
        return CLBL;
    }

    public void setCLBL(BigDecimal CLBL) {
        this.CLBL = CLBL;
    }

    public BigDecimal getCLDJ() {
        return CLDJ;
    }

    public void setCLDJ(BigDecimal CLDJ) {
        this.CLDJ = CLDJ;
    }

    public String getWZBZ() {
        return WZBZ;
    }

    public void setWZBZ(String WZBZ) {
        this.WZBZ = WZBZ;
    }

    public String getZCSB() {
        return ZCSB;
    }

    public void setZCSB(String ZCSB) {
        this.ZCSB = ZCSB;
    }

    public String getPZWH() {
        return PZWH;
    }

    public void setPZWH(String PZWH) {
        this.PZWH = PZWH;
    }

    public String getYYTS() {
        return YYTS;
    }

    public void setYYTS(String YYTS) {
        this.YYTS = YYTS;
    }

    public String getZXBZ() {
        return ZXBZ;
    }

    public void setZXBZ(String ZXBZ) {
        this.ZXBZ = ZXBZ;
    }

    public String getWZLB() {
        return WZLB;
    }

    public void setWZLB(String WZLB) {
        this.WZLB = WZLB;
    }

    public BigDecimal getZHB() {
        return ZHB;
    }

    public void setZHB(BigDecimal ZHB) {
        this.ZHB = ZHB;
    }

    public List<InventoryUploadCenterInfo> getDZXXY() {
        return DZXXY;
    }

    public void setDZXXY(List<InventoryUploadCenterInfo> DZXXY) {
        this.DZXXY = DZXXY;
    }
}
