package lukelin.his.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.ebean.*;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.account.ViewFeeSummary;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.Employee;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerItem;
import lukelin.his.domain.entity.basic.codeEntity.ManufacturerMedicine;
import lukelin.his.domain.entity.basic.codeEntity.UserRole;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.patient_sign_in.MedicalRecord;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignInView;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.entity.yb.drg.*;
import lukelin.his.domain.entity.yb.hy.SettlementHY;
import lukelin.his.domain.enums.Basic.UserRoleType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.basic.SearchCodeDto;
import lukelin.his.dto.basic.req.filter.EmployeeFilter;
import lukelin.his.dto.basic.resp.setup.DiagnoseDto;
import lukelin.his.dto.yb.inventory.req.PharmacyOrder;
import lukelin.his.dto.yb_drg.MainMedicalRecord;
import lukelin.his.dto.yb.*;
import lukelin.his.dto.yb.SettlementPaymentRequest.PatientTypePayment;
import lukelin.his.dto.yb.SettlementPaymentRequest.SettlementAccount;
import lukelin.his.dto.yb.SettlementPaymentRequest.SettlementPaymentRequest;
import lukelin.his.dto.yb.SettlementPaymentRequest.YbCardInfo;
import lukelin.his.dto.yb.inventory.resp.ManufacturerUploadResp;
import lukelin.his.dto.yb.req.*;
import lukelin.his.dto.yb.req.settlement.*;
import lukelin.his.dto.yb.req.signIn.SelfSignInReqDto;
import lukelin.his.dto.yb.SignInReqDto;
import lukelin.his.dto.yb.req.signIn.SignInChangeReq;
import lukelin.his.dto.yb.resp.*;
import lukelin.his.dto.yb_drg.*;
import lukelin.his.system.ConfigBeanProp;
import lukelin.his.system.Utils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class YBService extends BaseHisService {
    @Autowired
    protected EbeanServer ebeanServer;

    @Autowired
    protected RestTemplate restTemplate;

    @Value("${ybInterface.baseUrl}")
    protected String interfaceBaseUrl;

    protected Boolean allFeeUploading = false;


    @Autowired
    private ConfigBeanProp configBeanProp;

    @Autowired
    private YBInventoryService ybInventoryService;

    @Value("${uploadYBPatient}")
    private Boolean enableYBService;

    @Value("${selfPayNeedUpload}")
    private Boolean selfPayNeedUpload;

    @Autowired
    private UserService userService;

    @Autowired
    private BasicService basicService;

    @Value("${ybDrg.hospitalCode}")
    protected String ybHospitalCode;


    @Value("${ybDrg.mock}")
    protected Boolean mockDrgUpload;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }

    @Transactional
    public void downloadCenterTreatment() {
        String url = this.interfaceBaseUrl + "yb/treatment/center/download";
        CenterTreatmentListSave downloadResp = this.restTemplate.postForObject(url, null, CenterTreatmentListSave.class);

        List<CenterTreatment> listToSave = new ArrayList<>();
        CenterTreatment centerTreatment = null;
        for (CenterTreatmentSaveDto saveDto : downloadResp.getTreatmentList()) {
            Optional<CenterTreatment> existingTreatment = ebeanServer.find(CenterTreatment.class).where()
                    .eq("ZXNM", saveDto.getZXNM())
                    .findOneOrEmpty();
            centerTreatment = existingTreatment.orElseGet(CenterTreatment::new);
            saveDto.copyProperty(centerTreatment);
            listToSave.add(centerTreatment);
        }
        ebeanServer.saveAll(listToSave);
    }


    public PagedList<CenterMedicine> getPagedCenterMedicineList(SearchCodeDto searchCode, int pageNum, int pageSize) {
        ExpressionList<CenterMedicine> el = ebeanServer.find(CenterMedicine.class).where();
        el = el
                .or()
                .gt("JSRQ", LocalDate.now())
                .eq("JSRQ", null)
                .endOr();

        if (!StringUtils.isBlank(searchCode.getSearchCode()))
            el = el.or()
                    .like("SFXMMC", "%" + searchCode.getSearchCode() + "%")
                    .like("PYM", searchCode.getSearchCode().toUpperCase() + "%")
                    .like("BZBM", "%" + searchCode.getSearchCode() + "%")
                    .endOr();

        Query<CenterMedicine> query = el.query().orderBy("SFXMMC, KSRQ");
        return findPagedList(query, pageNum, pageSize);
    }

    public PagedList<CenterTreatment> getPagedCenterTreatmentList(SearchCodeDto searchCode, int pageNum, int pageSize) {
        ExpressionList<CenterTreatment> el = ebeanServer.find(CenterTreatment.class).where();
        el = el.or()
                .gt("ENDDATE", LocalDate.now())
                .eq("ENDDATE", null)
                .endOr();

        if (!StringUtils.isBlank(searchCode.getSearchCode()))
            el = el.or()
                    .like("SFXMMC", "%" + searchCode.getSearchCode() + "%")
                    .like("PYM", searchCode.getSearchCode().toUpperCase() + "%")
                    .like("BZBM", "%" + searchCode.getSearchCode() + "%")
                    .endOr();

        Query<CenterTreatment> query = el.query().orderBy("SFXMMC, STARTDATE");
        return findPagedList(query, pageNum, pageSize);
    }


    @Transactional
    public void rebuildUploadedTreatmentCache() {
        String url = this.interfaceBaseUrl + "yb/treatment/uploaded/download";
        UploadedTreatmentListSaveDto resp = this.restTemplate.postForObject(url, null, UploadedTreatmentListSaveDto.class);

        String delStatement = "delete from yb.treatment_uploaded";
        SqlUpdate update = ebeanServer.createSqlUpdate(delStatement);
        ebeanServer.execute(update);

        List<UploadedTreatment> listToSave = new ArrayList<>();
        for (UploadedTreatmentSaveDto treatmentSaveDto : resp.getTreatmentList())
            listToSave.add(treatmentSaveDto.toEntity());
        ebeanServer.saveAll(listToSave);
    }


    public EntityMatchReqWrapper getPendingMatchTreatmentList() {
        List<Treatment> treatmentList = ebeanServer.find(Treatment.class).where()
                .eq("enabled", true)
                .ne("uploadResult", null)
                .ne("uploadResult.serverCode", null)
                .or()
                .eq("matchedTreatment", null)
                .and()
                .ne("matchedTreatment.status", "0") //未审核
                .ne("matchedTreatment.status", "1") //审核通过
                .endAnd()
                .endOr()
                .findList();

        List<EntityMatchReqDto> reqDtoList = new ArrayList<>();
        EntityMatchReqDto reqDto = null;
        int counter = 0;
        for (Treatment treatment : treatmentList) {
            reqDto = this.ybInventoryService.crateOrReturnMatchReq(counter, reqDtoList);
            counter++;
            reqDto.getXmxxy().add(treatment.toMatchReqLineDto());
        }

        EntityMatchReqWrapper matchReqWrapper = new EntityMatchReqWrapper();
        matchReqWrapper.setReqList(reqDtoList);
        return matchReqWrapper;
    }

    public void matchTreatment(UUID treatmentId) {
        if (!this.enableYBService)
            return;

        EntityMatchReqWrapper matchReqWrapper = null;
        if (treatmentId == null) {
            matchReqWrapper = this.getPendingMatchTreatmentList();
        } else {
            Treatment treatment = this.findById(Treatment.class, treatmentId);
            matchReqWrapper = this.getTreatmentMatchWrapper(treatment);
        }
        this.callMatchTreatmentInterface(matchReqWrapper);
        this.downloadMatchedTreatment(matchReqWrapper);
    }

    @Transactional
    public EntityMatchReqWrapper getTreatmentMatchWrapper(Treatment treatment) {
        EntityMatchReqLineDto reqLineDto = treatment.toMatchReqLineDto();
        String sqlx = "1";
        if (treatment.getMatchedTreatment() != null) {
            sqlx = "2";
            ebeanServer.delete(treatment.getMatchedTreatment());
        }
        return this.ybInventoryService.createSingleEntityMatchWrapper(reqLineDto, sqlx);

    }

    @Transactional
    public void callMatchTreatmentInterface(EntityMatchReqWrapper matchReqWrapper) {
        String url = this.interfaceBaseUrl + "yb/match/request";
        MatchResp matchResp = this.restTemplate.postForObject(url, matchReqWrapper, MatchResp.class);

        TreatmentMatchUploadResult result;
        List<TreatmentMatchUploadResult> listToSave = new ArrayList<>();
        for (EntityMatchUploadResultSaveDto saveDto : matchResp.getMatchRespList()) {
            result = new TreatmentMatchUploadResult();
            result.setTreatment(new Treatment(saveDto.getUuid()));
            result.setStatus(saveDto.getYbscbz());
            if (result.getStatus() == null)
                result.setStatus("0");
            //系统错误
            result.setError(saveDto.getError());
            //审批失败原因
            if (!result.getStatus().equals("1") && saveDto.getSbyy() != null)
                result.setError(saveDto.getSbyy());
            listToSave.add(result);
        }
        ebeanServer.saveAll(listToSave);
    }

    @Transactional
    public void downloadMatchedTreatment(EntityMatchReqWrapper matchReqWrapper) {
        String url = this.interfaceBaseUrl + "yb/match/request/result/download";
        MatchDownloadResp downloadResp = this.restTemplate.postForObject(url, matchReqWrapper, MatchDownloadResp.class);

        TreatmentMatchDownload matchedTreatment;
        List<TreatmentMatchDownload> listToSave = new ArrayList<>();
        for (EntityMatchDownloadSaveDto saveDto : downloadResp.getXmxxy()) {
            Optional<TreatmentMatchDownload> optionalDownloadedTreatment = this.ebeanServer.find(TreatmentMatchDownload.class).where()
                    .eq("treatment.uuid", saveDto.getUuid())
                    .findOneOrEmpty();
            if (optionalDownloadedTreatment.isPresent())
                matchedTreatment = optionalDownloadedTreatment.get();
            else {
                matchedTreatment = new TreatmentMatchDownload();
                matchedTreatment.setTreatment(new Treatment(saveDto.getUuid()));
            }

            matchedTreatment.setError(saveDto.getError());
            matchedTreatment.setReference(saveDto.getSpbz());
            matchedTreatment.setStatus(saveDto.getSpjg());
            //医保无返回结果，设为2无记录
            if (matchedTreatment.getStatus() == null)
                matchedTreatment.setStatus("2");
            listToSave.add(matchedTreatment);
        }
        ebeanServer.saveAll(listToSave);
    }

    public void yBSignIn(PatientSignIn patientSignIn, ClientIpInfo clientIpInfo) {
        if (!this.enableYBService)
            return;
        //已注册入院
        if (patientSignIn.getYbSignIn() != null)
            throw new ApiValidationException("病人已医保登记入院");

        //读卡并储存卡信息
        ICCardResponse cardResponse = this.readIcCard(clientIpInfo);
        this.saveCardResponse(cardResponse, patientSignIn, clientIpInfo.getElectronicCard());

        //医保登记入院
        SignInReqDto signInReq = patientSignIn.toYBSignIn();
        signInReq.setClientIpInfo(clientIpInfo);
        String url = clientIpInfo.getClientUrl() + "yb/patient/sign_in";
        SignInRespDto resp = this.restTemplate.postForObject(url, signInReq, SignInRespDto.class);

        //储存医保信息
        YBSignIn signIn = new YBSignIn();
        signIn.setCode(resp.getZyh());
        signIn.setId(resp.getJzxh());
        patientSignIn.setYbSignIn(signIn);
        signIn.setPatientSignIn(patientSignIn);
        ebeanServer.save(signIn);
    }

    public void selfSignIn(PatientSignIn patientSignIn) {
        if (!this.enableYBService)
            return;
        //已注册入院
        if (patientSignIn.getYbSignIn() != null)
            throw new ApiValidationException("病人已医保登记入院");
        //医保登记入院
        SelfSignInReqDto signInReq = patientSignIn.toSelfYBSignIn();
        String url = this.interfaceBaseUrl + "yb/patient/sign_in/self";
        SignInRespDto resp = this.restTemplate.postForObject(url, signInReq, SignInRespDto.class);
        //储存医保信息
        YBSignIn signIn = new YBSignIn();
        signIn.setCode(resp.getZyh());
        signIn.setId(resp.getJzxh());
        patientSignIn.setYbSignIn(signIn);
        signIn.setPatientSignIn(patientSignIn);
        ebeanServer.save(signIn);
    }


    public void updateSignInInfo(PatientSignIn patientSignIn) {
        if (!this.enableYBService)
            return;
        SignInChangeReq signInChangeReq = patientSignIn.toYBSignInChange();
        String url = this.interfaceBaseUrl + "yb/patient/sign_in/change";
        this.restTemplate.postForObject(url, signInChangeReq, Void.class);
    }

    @Transactional
    public void cancelYBSignIn(PatientSignIn patientSignIn, ClientIpInfo clientIpInfo) {
        //医保取消住院
        YBSignIn ybSignIn = patientSignIn.getYbSignIn();
        clientIpInfo.setElectronicCard(!ybSignIn.getPatientSignIn().selfPay() && ybSignIn.getPatientSignIn().getCardInfo().getElectronicCard());

        String url = clientIpInfo.getClientUrl() + "yb/patient/sign_in/cancel/" + ybSignIn.getId();
        this.restTemplate.postForObject(url, clientIpInfo, Void.class);

        ebeanServer.delete(ybSignIn);


        //更新数据库
        List<FeeUploadResult> feeUploadResultList = ebeanServer.find(FeeUploadResult.class).where()
                .eq("fee.patientSignIn.uuid", patientSignIn.getUuid())
                .findList();
        ebeanServer.deleteAll(feeUploadResultList);
        List<FeeUploadError> feeUploadErrorList = ebeanServer.find(FeeUploadError.class).where()
                .eq("fee.patientSignIn.uuid", patientSignIn.getUuid())
                .findList();
        ebeanServer.deleteAll(feeUploadErrorList);
    }

    @Transactional
    public PatientSignIn insuranceToSelf(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        if (patientSignIn.selfPay())
            throw new ApiValidationException("非医保病人，非法的住院类型");

        if (patientSignIn.getStatus() != PatientSignInStatus.signedIn)
            throw new ApiValidationException("非法的住院状态");

        //检查是否有未上传的药品单据
        List<PharmacyOrderUpload> pendingUploadList = this.ybInventoryService.findPendingPharmacyOrderList(patientSignIn);
        if (pendingUploadList.size() > 0) {
            //未上传的发药记录，尝试上传
            for (PharmacyOrderUpload orderUpload : pendingUploadList)
                this.ybInventoryService.uploadPharmacyOrder(orderUpload.getOrderLine());

            //再次检查是否有未上传的记录,如果仍有未处理，报错
            List<PharmacyOrderUpload> reCheckList = this.ybInventoryService.findPendingPharmacyOrderList(patientSignIn);
            if (reCheckList.size() > 0)
                throw new ApiValidationException("有未上传医保的发药库存记录");
        }

        //医保取消住院
        //this.cancelYBSignIn(patientSignIn, null);
        //String url = this.interfaceBaseUrl + "yb/patient/sign_in/cancel/" + patientSignIn.getYbSignIn().getId();
        //this.restTemplate.postForObject(url, null, Void.class);
        if (patientSignIn.getPreSettlement() != null)
            ebeanServer.delete(patientSignIn.getPreSettlement());

        Dictionary selfPayType = ebeanServer.find(Dictionary.class).where()
                .eq("group.code", configBeanProp.getInsuranceType())
                .eq("name", "自费")
                .findOne();
        patientSignIn.setYbSignIn(null);
        patientSignIn.setInsuranceType(selfPayType);
        ebeanServer.save(patientSignIn);

        this.selfSignIn(patientSignIn);
        return patientSignIn;
    }

    @Transactional
    public PatientSignIn selfToInsurance(UUID patientSignInId, ClientIpInfo clientIpInfo) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        if (!patientSignIn.selfPay())
            throw new ApiValidationException("非自费病人，非法的住院类型");

        if (patientSignIn.getStatus() != PatientSignInStatus.signedIn)
            throw new ApiValidationException("非法的住院状态");

        //取消医保自费登记
        //this.cancelYBSignIn(patientSignIn, null);

        //医保入院登记
        this.yBSignIn(patientSignIn, clientIpInfo);

        Dictionary selfPayType = ebeanServer.find(Dictionary.class).where()
                .eq("group.code", configBeanProp.getInsuranceType())
                .eq("name", "医保")
                .findOne();
        patientSignIn.setInsuranceType(selfPayType);
        ebeanServer.save(patientSignIn);
        return patientSignIn;
    }

    public ICCardResponse readIcCard(ClientIpInfo clientIpInfo) {
        if (clientIpInfo == null)
            throw new ApiValidationException("参数错误");
        String url;
        if (clientIpInfo.getClientUrl() != null)
            url = clientIpInfo.getClientUrl() + "yb/patient/read_card";
        else
            url = this.interfaceBaseUrl + "yb/patient/read_card";

        ICCardResponse cardResp;
        cardResp = this.restTemplate.postForObject(url, clientIpInfo, ICCardResponse.class);
        return cardResp;
    }

    private void saveCardResponse(ICCardResponse cardResponse, PatientSignIn patientSignIn, boolean electronicCard) {
        if (patientSignIn.getCardInfo() != null)
            ebeanServer.delete(patientSignIn.getCardInfo());

        CardInfo cardInfo = cardResponse.toEntity(patientSignIn);
        cardInfo.setElectronicCard(electronicCard);
        if (!cardInfo.getName().equals(patientSignIn.getPatient().getName())) {
            if (!cardInfo.getName().equals("测试病人"))
                throw new ApiValidationException("卡信息姓名与登记姓名不一致");
        }
        patientSignIn.setCardInfo(cardInfo);
        ebeanServer.save(cardInfo);
    }

    public List<Fee> getPatientPendingUploadFee(UUID patientSignInId) {
        return ebeanServer.find(Fee.class).where()
                .eq("feeUploadResult", null)
                .eq("patientSignIn.uuid", patientSignInId)
                .eq("feeStatus", FeeStatus.confirmed)
                .findList();
    }

    public boolean anyPendingUploadFee(UUID patientSignInId) {
        return ebeanServer.find(Fee.class).where()
                .eq("feeUploadResult", null)
                .eq("patientSignIn.uuid", patientSignInId)
                .eq("feeStatus", FeeStatus.confirmed)
                .setMaxRows(1)
                .findOneOrEmpty().isPresent();
    }


    public void uploadAllFee() {
        if (this.allFeeUploading)
            throw new ApiValidationException("fee already uploading");

        try {
            this.allFeeUploading = true;
            List<PatientSignInView> patientSignInList = ebeanServer.find(PatientSignInView.class).where()
                    .in("status", PatientSignInStatus.signedIn)
                    .gt("pendingFeeCount", 0)
                    .findList();

            for (PatientSignInView patientSignIn : patientSignInList) {
                if (patientSignIn.getYbSignIn() == null)
                    continue;
                try {
                    this.uploadPatientPendingFee(patientSignIn.getUuid());
                } catch (Exception ex) {
                    //Todo log exception;
                    String test = ex.getMessage();
                }
            }
        } finally {
            this.allFeeUploading = false;
        }


    }


    @Transactional
    public void initializeMedicineManufacturer() {
        List<ManufacturerMedicine> manufacturerMedicineList =
                ebeanServer.find(ManufacturerMedicine.class)
                        .where()
                        .or()
                        .eq("serverId", null)
                        .eq("serverId", "0")
                        .endOr()
                        .eq("enabled", true)
                        .findList();

        String url = this.interfaceBaseUrl + "inventory/manufacture/upload";
        for (ManufacturerMedicine manufacturerMedicine : manufacturerMedicineList) {
            ManufacturerUploadReqDto uploadReqDto = new ManufacturerUploadReqDto();
            uploadReqDto.setUuid(manufacturerMedicine.getUuid().toString());
            uploadReqDto.setMc(manufacturerMedicine.getName());
            ManufacturerUploadResp resp = this.restTemplate.postForObject(url, uploadReqDto, ManufacturerUploadResp.class);
            manufacturerMedicine.setServerId(resp.getGysbh());
        }
        ebeanServer.saveAll(manufacturerMedicineList);
    }

    @Transactional
    public void initializeItemManufacturer() {
        List<ManufacturerItem> manufacturerItemList =
                ebeanServer.find(ManufacturerItem.class)
                        .where()
                        .or()
                        .eq("serverId", null)
                        .eq("serverId", "0")
                        .endOr()
                        .eq("enabled", true)
                        .findList();

        String url = this.interfaceBaseUrl + "inventory/manufacture/upload";
        for (ManufacturerItem manufacturerItem : manufacturerItemList) {
            ManufacturerUploadReqDto uploadReqDto = new ManufacturerUploadReqDto();
            uploadReqDto.setUuid(manufacturerItem.getUuid().toString());
            uploadReqDto.setMc(manufacturerItem.getName());
            ManufacturerUploadResp resp = this.restTemplate.postForObject(url, uploadReqDto, ManufacturerUploadResp.class);
            manufacturerItem.setServerId(resp.getGysbh());
        }
        ebeanServer.saveAll(manufacturerItemList);
    }

    @Transactional
    public void saveFeeUploadResult(FeeUploadResultDto feeUploadResultDto) {
        if (feeUploadResultDto.getFyfymx() != null) {
            for (FeeUploadLineResultDto resultDto : feeUploadResultDto.getFyfymx()) {
                FeeUploadResult result = resultDto.toEntity();
                this.clearExistingError(result.getFee().getUuid());
                this.clearExistingFeeResult(result.getFee().getUuid());
                ebeanServer.save(result);

            }
        }

        if (feeUploadResultDto.getCcxx() != null) {
            for (FeeUploadLineErrorDto errorDto : feeUploadResultDto.getCcxx()) {
                FeeUploadError error = errorDto.toEntity();
                this.clearExistingError(error.getFee().getUuid());
                ebeanServer.save(error);
            }
        }
    }

    private void clearExistingError(UUID feeId) {
        Optional<FeeUploadError> optionalExistingError = ebeanServer.find(FeeUploadError.class).where().eq("fee.uuid", feeId).findOneOrEmpty();
        optionalExistingError.ifPresent(feeUploadError -> ebeanServer.delete(feeUploadError));
    }

    private void clearExistingFeeResult(UUID feeId) {
        Optional<FeeUploadResult> optionalExistingResult = ebeanServer.find(FeeUploadResult.class).where().eq("fee.uuid", feeId).findOneOrEmpty();
        optionalExistingResult.ifPresent(feeUploadResult -> ebeanServer.delete(feeUploadResult));
    }

    @Transactional
    public void deleteAllSignInFee(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        String url = this.interfaceBaseUrl + "yb/patient/fee/cancel";
        FeeCancelReq cancelReq = new FeeCancelReq();
        cancelReq.setFybh("");
        cancelReq.setJzxh(patientSignIn.getYbSignIn().getId());
        cancelReq.setSfqc("1");
        this.restTemplate.postForObject(url, cancelReq, FeeCancelReq.class);


        ebeanServer.find(FeeUploadResult.class)
                .where().eq("fee.patientSignIn.uuid", patientSignInId).delete();
        ebeanServer.find(FeeUploadError.class)
                .where().eq("fee.patientSignIn.uuid", patientSignInId).delete();
        ebeanServer.find(FeeInventoryUploadResult.class)
                .where().eq("fee.patientSignIn.uuid", patientSignInId).delete();
        Optional<FeeDownload> optionalFeeDownload = ebeanServer.find(FeeDownload.class).where().eq("patientSignIn.uuid", patientSignInId).findOneOrEmpty();
        optionalFeeDownload.ifPresent(download -> ebeanServer.delete(download));
        Optional<PreSettlement> optionalPreSettlement = ebeanServer.find(PreSettlement.class).where().eq("patientSignIn.uuid", patientSignInId).findOneOrEmpty();
        optionalPreSettlement.ifPresent(preSettlement -> ebeanServer.delete(preSettlement));
    }


    //从上传的费用返回信息删除
    public void cancelFee(Fee fee) {
        if (!this.enableYBService) {
            return;
        }

        if (fee.getFeeUploadResult() != null) {
            String url = this.interfaceBaseUrl + "yb/patient/fee/cancel";
            FeeCancelReq cancelReq = new FeeCancelReq();
            cancelReq.setFybh(fee.getFeeUploadResult().getServerId());
            cancelReq.setJzxh(fee.getPatientSignIn().getYbSignIn().getId());
            this.restTemplate.postForObject(url, cancelReq, FeeCancelReq.class);
        }

        if (fee.getPreSettlementFee() != null)
            ebeanServer.delete(fee.getPreSettlementFee());

        this.clearExistingError(fee.getUuid());
        this.clearExistingFeeResult(fee.getUuid());
    }

    //从下载的费用明细删除
    @Transactional
    public void deleteUploadedFee(UUID feeDownloadLineId) {
        FeeDownloadLine feeDownloadLine = this.findById(FeeDownloadLine.class, feeDownloadLineId);
        FeeCancelReq cancelReq = new FeeCancelReq();
        cancelReq.setFybh(feeDownloadLine.getFydm());
        cancelReq.setJzxh(feeDownloadLine.getFeeDownload().getPatientSignIn().getYbSignIn().getId());

        String url = this.interfaceBaseUrl + "yb/patient/fee/cancel";
        this.restTemplate.postForObject(url, cancelReq, FeeCancelReq.class);

        Fee hisFee = feeDownloadLine.getFee();
        if (hisFee != null) {
            this.clearExistingError(feeDownloadLine.getFee().getUuid());
            this.clearExistingFeeResult(feeDownloadLine.getFee().getUuid());
            if (hisFee.getPatientSignIn().getPreSettlement() != null)
                ebeanServer.delete(hisFee.getPatientSignIn().getPreSettlement());
        }
        ebeanServer.delete(feeDownloadLine);
    }


    public Settlement getPatientSignInSettlement(UUID patientSignInId) {

        Optional<Settlement> optionalSettlement = ebeanServer.find(Settlement.class).where()
                .eq("patientSignIn.uuid", patientSignInId).findOneOrEmpty();
        return optionalSettlement.orElse(null);
    }

    @Transactional
    public PreSettlement preSettle(PreSettlementReq req) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, req.getPatientSignInId());
        PreSettleUploadReq uploadReq = new PreSettleUploadReq();
        uploadReq.setJzxh(patientSignIn.getYbSignIn().getId());
        if (req.getReturnFeeDetail())
            uploadReq.setSffhjsmx("0");
        else
            uploadReq.setSfsygzzf("1");
        uploadReq.setSfsygzzf("1"); //个账支付 1使用，0不使用
        uploadReq.setZyjslx("1"); //住院结算类型 1普通

        String url = this.interfaceBaseUrl + "yb/patient/sign_in/pre_settle";
        PreSettlementSaveDto preSettlementSaveDto = this.restTemplate.postForObject(url, uploadReq, PreSettlementSaveDto.class);

        if (preSettlementSaveDto != null) {
            Optional<PreSettlement> optionalPreSettlement = ebeanServer.find(PreSettlement.class).where()
                    .eq("patientSignIn.uuid", req.getPatientSignInId()).findOneOrEmpty();
            optionalPreSettlement.ifPresent(preSettlement -> ebeanServer.delete(preSettlement));

            PreSettlement newPreSettlement = preSettlementSaveDto.toEntity();
            newPreSettlement.setPatientSignIn(patientSignIn);
            newPreSettlement.setReturnDetail(req.getReturnFeeDetail());
            ebeanServer.save(newPreSettlement);
            return newPreSettlement;
        } else
            throw new ApiValidationException("获取医保预结失败");
    }

    @Transactional
    public void downloadFee(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        FeeDownloadReq req = new FeeDownloadReq();
        req.setJzxh(patientSignIn.getYbSignIn().getId());
        req.setPage(1);
        req.setPatientSignInId(patientSignInId);

        String url = this.interfaceBaseUrl + "yb/patient/fee/download";
        FeeDownloadInterfaceResp feeDownloadDto = this.restTemplate.postForObject(url, req, FeeDownloadInterfaceResp.class);
        //删除现有记录
        FeeDownload existingFeeDownload = patientSignIn.getYbFeeDownload();
        if (existingFeeDownload != null)
            ebeanServer.delete(existingFeeDownload);

        FeeDownload feeDownload = feeDownloadDto.toEntity();
        if (feeDownload.getLineList() != null) {
            for (FeeDownloadLine line : feeDownload.getLineList()) {
                this.validateDownloadedFee(line);
            }
        }
        ebeanServer.save(feeDownload);
    }


    private void validateDownloadedFee(FeeDownloadLine line) {
        if (!line.getYbscbz().equals("1"))
            line.setValidationMessage("医保费用未上传至中心");
        else if (StringUtils.isBlank(line.getCfybh())) {
            line.setValidationMessage("空HIS客户端代码");
        } else {
            UUID feeId = UUID.fromString(line.getCfybh());
            Optional<Fee> optionalFee = ebeanServer.find(Fee.class).where().eq("uuid", feeId).findOneOrEmpty();
            if (optionalFee.isPresent()) {
                Fee hisFee = optionalFee.get();
                line.setFee(hisFee);
                if (hisFee.getFeeStatus() != FeeStatus.confirmed)
                    line.setValidationMessage("HIS费用状态不正确");
                else if (hisFee.getFeeUploadResult() == null) {
                    //修复His端费用
                    if (hisFee.getFeeUploadError() != null)
                        ebeanServer.delete(hisFee.getFeeUploadError());
                    FeeUploadResult uploadResult = new FeeUploadResult();
                    uploadResult.setFee(hisFee);
                    uploadResult.setServerId(line.getFybh());
                    uploadResult.setInsuranceAttribute(line.getYbsx());
                    uploadResult.setSelfRatio(line.getZlbl());
                    uploadResult.setSelfRatioPayAmount(line.getZlje());
                    uploadResult.setSelfPayAmount(line.getZfje());
                    uploadResult.setUnitAmountLimit(line.getDjxe());
                    uploadResult.setUploadStatus(line.getYbscbz());
                    uploadResult.setReference("从下载的明细修复");
                    ebeanServer.save(uploadResult);
                }
                //line.setValidationMessage("His端费用显示未上传");
                else {
                    if (hisFee.getTotalAmount().compareTo(line.getJe()) != 0)
                        line.setValidationMessage("HIS与医保总费用金额不相等");
                    else if (hisFee.getFeeUploadResult().getSelfRatioPayAmount().compareTo(line.getZlje()) != 0)
                        line.setValidationMessage("HIS与医保自理金额不相等");
                    else if (hisFee.getFeeUploadResult().getSelfPayAmount().compareTo(line.getZfje()) != 0)
                        line.setValidationMessage("HIS与医保自费金额不相等");
                }

            } else
                line.setValidationMessage("找不到对应的HIS费用");
        }


        line.setValid(line.getValidationMessage() == null);
    }

    @Transactional
    public Settlement finalSettle(UUID patientSignInId, ClientIpInfo clientIpInfo) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        if (patientSignIn.getPreSettlement() == null && !patientSignIn.selfPay())
            throw new ApiValidationException("没有预结信息");

        UUID newId = UUID.randomUUID();
        SettleUploadReq uploadReq = patientSignIn.toSettleUploadReq(newId.toString());
        clientIpInfo.setElectronicCard(!patientSignIn.selfPay() && patientSignIn.getCardInfo().getElectronicCard());
        uploadReq.setClientIpInfo(clientIpInfo);

        String url = clientIpInfo.getClientUrl() + "yb/patient/sign_in/settle";
        SettlementSaveDto settlementSaveDto = restTemplate.postForObject(url, uploadReq, SettlementSaveDto.class);

        settlementSaveDto.setPatientSignInId(patientSignInId);
        //Should get nothing, just in case
        List<Settlement> settlementList = ebeanServer.find(Settlement.class).where().eq("patientSignIn.uuid", patientSignInId).findList();
        ebeanServer.deleteAll(settlementList);

        Settlement newSettlement = settlementSaveDto.toEntity();
        newSettlement.setUuid(newId);
        ebeanServer.save(newSettlement);


        return newSettlement;
    }

    public void confirmFinalSettlement(PatientSignIn patientSignIn) {
        //确认结算
        //PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        String url = this.interfaceBaseUrl + "yb/patient/sign_in/settle/confirm";
        ConfirmSettlementDto dto = new ConfirmSettlementDto();
        dto.setJzxh(patientSignIn.getYbSignIn().getId());
        dto.setYbjsh(patientSignIn.getSettlement().getYbjsh());
        dto.setQrzt("1"); //确认结果1成功
        restTemplate.postForObject(url, dto, Void.class);
    }

    @Transactional
    public SettlementHY finalSelfSettle(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        UUID newId = UUID.randomUUID();

        SettlementHY newSettlement = null;
        // if (this.selfPayNeedUpload) {
//            SelfSettleUploadReq uploadReq = patientSignIn.toSelfSettleUploadReq(newId.toString());
//            String url = this.interfaceBaseUrl + "yb/patient/sign_in/settle/self";
//            SettlementSaveDto settlementSaveDto = this.restTemplate.postForObject(url, uploadReq, SettlementSaveDto.class);
//            settlementSaveDto.setPatientSignInId(patientSignInId);
//            newSettlement = settlementSaveDto.toEntity();
//            newSettlement.setZfje(uploadReq.getFyze());
//            newSettlement.setZje(uploadReq.getFyze());
        //   } else {
        newSettlement = new SettlementHY();
        newSettlement.setPatientSignIn(patientSignIn);
        Optional<ViewFeeSummary> optionalViewFeeSummary = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", patientSignInId).findOneOrEmpty();
        if (optionalViewFeeSummary.isPresent()) {
            newSettlement.setMedfee_sumamt(optionalViewFeeSummary.get().getTotalAmount());
            newSettlement.setFulamt_ownpay_amt(optionalViewFeeSummary.get().getTotalAmount());
        } else {
            newSettlement.setMedfee_sumamt(BigDecimal.ZERO);
            newSettlement.setFulamt_ownpay_amt(BigDecimal.ZERO);
        }
        // }
        //Should get nothing, just in case
        List<SettlementHY> settlementList = ebeanServer.find(SettlementHY.class).where().eq("patientSignIn.uuid", patientSignInId).findList();
        ebeanServer.deleteAll(settlementList);
        ebeanServer.save(newSettlement);
        return newSettlement;
    }


    @Transactional
    public PagedList<FeeDownloadLine> getDownloadedFeeList(FeeDownloadFilter filter, Integer pageNum) {
        ExpressionList<FeeDownloadLine> el = ebeanServer.find(FeeDownloadLine.class)
                .where()
                .eq("feeDownload.patientSignIn.uuid", filter.getPatientSignInId());

        if (filter.getLoadAll() == null || !filter.getLoadAll())
            el = el.eq("isValid", false);

        if (filter.getSearchCode() != null)
            el = el.like("kpxm", "%" + filter.getSearchCode() + "%");

        return this.findPagedList(el, pageNum);
    }


    @Transactional
    public void uploadPatientPendingFee(UUID patientSignInId) {
        if (!this.enableYBService)
            return;
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
//        if (patientSignIn.selfPay())
//            throw new ApiValidationException("invalid patient type");
        List<Fee> pendingUploadFeeList = this.getPatientPendingUploadFee(patientSignInId);
        if (pendingUploadFeeList.size() == 0)
            return;
        FeeUploadReq feeUploadReq = patientSignIn.toUploadDto(pendingUploadFeeList);

        String url = this.interfaceBaseUrl + "yb/patient/fee/upload";
        FeeUploadResultDto uploadResultDto = this.restTemplate.postForObject(url, feeUploadReq, FeeUploadResultDto.class);
        this.saveFeeUploadResult(uploadResultDto);

        if (!patientSignIn.selfPay()) {
            PreSettlementReq preSettlementReq = new PreSettlementReq();
            preSettlementReq.setPatientSignInId(patientSignIn.getUuid());
            this.preSettle(preSettlementReq);
        }
    }


    @Transactional
    public TreatmentUploadResult uploadSingleTreatment(Treatment treatment) {
        TreatmentUploadReqDto reqDto = treatment.toTreatmentUpload();
        TreatmentUploadReqList reqList = new TreatmentUploadReqList();
        List<TreatmentUploadReqDto> list = new ArrayList<>();
        list.add(reqDto);
        reqList.setTreatmentList(list);
        List<Treatment> treatmentList = new ArrayList<>();
        treatmentList.add(treatment);
        //this.callTreatmentUploadInterface(reqList);
        List<TreatmentUploadResult> listToSave = this.callTreatmentUploadInterface(reqList, treatmentList);
        return listToSave.get(0);
    }

    @Transactional
    public void uploadAllTreatment() {
        List<Treatment> treatmentList = ebeanServer.find(Treatment.class).where()
                .or()
                .eq("uploadResult", null)
                .eq("uploadResult.serverCode", null)
                .endOr().findList();
        List<TreatmentUploadReqDto> reqDtoList = new ArrayList<>();
        for (Treatment treatment : treatmentList) {
            reqDtoList.add(treatment.toTreatmentUpload());
        }

        TreatmentUploadReqList req = new TreatmentUploadReqList();
        req.setTreatmentList(reqDtoList);
        this.callTreatmentUploadInterface(req, treatmentList);
    }

    private List<TreatmentUploadResult> callTreatmentUploadInterface(TreatmentUploadReqList reqList, List<Treatment> treatmentList) {
        String url = this.interfaceBaseUrl + "yb/treatment/upload";
        EntityUploadResp uploadResp = this.restTemplate.postForObject(url, reqList, EntityUploadResp.class);

        TreatmentUploadResult result;
        List<TreatmentUploadResult> listToSave = new ArrayList<>();
        for (EntityUploadResultSaveDto saveDto : uploadResp.getEntityList()) {
            Optional<TreatmentUploadResult> optionalUploadResult = this.ebeanServer.find(TreatmentUploadResult.class).where()
                    .eq("treatment.uuid", saveDto.getUuid())
                    .findOneOrEmpty();
            if (optionalUploadResult.isPresent())
                result = optionalUploadResult.get();
            else {
                result = new TreatmentUploadResult();
                //result.setTreatment(new Treatment(saveDto.getUuid()));
            }


            Treatment treatment = treatmentList.stream().filter(m -> m.getUuid().equals(saveDto.getUuid())).findFirst().get();
            result.setTreatment(treatment);
            treatment.setUploadResult(result);

            result.setServerCode(saveDto.getServerCode());
            result.setUploadError(saveDto.getError());
            listToSave.add(result);
        }
        ebeanServer.saveAll(listToSave);
        return listToSave;
    }

    public void signOut(PatientSignIn patientSignIn) {
        SignOutReqDto signOutReq = patientSignIn.getSingOutRequest().toYBSignOutReqDto();
        String url = this.interfaceBaseUrl + "yb/patient/sign_in/signOut";
        this.restTemplate.postForObject(url, signOutReq, Void.class);
    }

    public void saveDoctorAgreement(DoctorAgreementSaveDto saveDto) {
        DoctorAgreement doctorAgreement = saveDto.toEntity();
        ebeanServer.save(doctorAgreement);
    }

    public YBSignInRecord saveSignInRecord(SignInRecordRespDto saveDto) {
        YBSignInRecord signInRecord = saveDto.toEntity();
        ebeanServer.save(signInRecord);
        return signInRecord;
    }

    public PagedList<ICD9> getPagedIcd9List(SearchCodeDto searchCodeDto, Integer pageNum, Integer pageSize) {
        ExpressionList<ICD9> el = ebeanServer.find(ICD9.class).where();
        if (searchCodeDto.getEnabled() != null)
            el = el.eq("enabled", searchCodeDto.getEnabled());
        if (searchCodeDto.getSearchCode() != null)
            el = Utils.addSearchExpression(el, searchCodeDto.getSearchCode());
        return this.findPagedList(el, pageNum, pageSize);
    }

    @Transactional
    public DrgInitPramDto getDrgRecordInitPram() {
        DrgInitPramDto dto = new DrgInitPramDto();
        List<AllergyMedicine> allergyMedicineList =
                ebeanServer.find(AllergyMedicine.class).where().eq("enabled", true).findList();
        dto.setAllergyMedicineList(DtoUtils.toDtoList(allergyMedicineList));

        List<BloodType> bloodTypeList =
                ebeanServer.find(BloodType.class).where().eq("enabled", true).findList();
        dto.setBloodTypeList(DtoUtils.toDtoList(bloodTypeList));

        List<BloodTypeRH> bloodTypeRHList =
                ebeanServer.find(BloodTypeRH.class).where().eq("enabled", true).findList();
        dto.setBloodTypeRhList(DtoUtils.toDtoList(bloodTypeRHList));

        List<DiagnoseDirection> diagnoseDirectionList =
                ebeanServer.find(DiagnoseDirection.class).where().eq("enabled", true).findList();
        dto.setDiagnoseDirectionList(DtoUtils.toDtoList(diagnoseDirectionList));

        List<SignOutMethod> signOutMethodList =
                ebeanServer.find(SignOutMethod.class).where().eq("enabled", true).findList();
        dto.setSignOutMethodList(DtoUtils.toDtoList(signOutMethodList));

        List<SpecialDisease> specialDiseaseList =
                ebeanServer.find(SpecialDisease.class).where().eq("enabled", true).findList();
        dto.setSpecialDiseaseList(DtoUtils.toDtoList(specialDiseaseList));

        EmployeeFilter employeeFilter = new EmployeeFilter();
        employeeFilter.setEnabled(true);
        UserRole userRole = ebeanServer.find(UserRole.class).where().eq("name", "医生").findOne();
        employeeFilter.setUserRoleId(userRole.getUuid());
        dto.setDoctorList(DtoUtils.toDtoList(this.userService.getEmployeeList(employeeFilter)));

        dto.setMarriageStatusList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMarriageStatus())));

        List<SignOutReason> signOutReasonList =
                ebeanServer.find(SignOutReason.class).where().eq("enabled", true).findList();
        dto.setSignOutReasonList(DtoUtils.toDtoList(signOutReasonList));

        List<OperationLevel> operationLevelList =
                ebeanServer.find(OperationLevel.class).where().eq("enabled", true).findList();
        dto.setOperationLevelList(DtoUtils.toDtoList(operationLevelList));


        ExpressionList<Employee> el =
                ebeanServer.find(Employee.class)
                        .where()
                        .eq("enabled", true)
                        .ne("certificationNumber", null);
        el = el.query().fetch("userRoleList").where()
                .eq("userRoleList.userRoleType", UserRoleType.therapist);
        List<Employee> operatorList = el.findList();
        dto.setOperatorList(DtoUtils.toDtoList(operatorList));
        return dto;
    }

    public DrgMedicalRecordInfo getDrgMedicalRecordInfo(UUID patientSignInId) {
        DrgMedicalRecordInfo drgMedicalRecordInfo = new DrgMedicalRecordInfo();

        List<MedicalRecord> signInMedicalRecordList = ebeanServer.find(MedicalRecord.class).where()
                .eq("type.name", "入院记录")
                .eq("patientSignIn.uuid", patientSignInId)
                .findList();
        if (signInMedicalRecordList.size() > 0) {
            MedicalRecord signInMedicalRecord = signInMedicalRecordList.get(0);
            drgMedicalRecordInfo.setMainInfo(this.getDrgMedicalRecordContentInfo(signInMedicalRecord, "zs"));
            drgMedicalRecordInfo.setCurrentCondition(this.getDrgMedicalRecordContentInfo(signInMedicalRecord, "xbs"));
        }

        Optional<MedicalRecord> optionalMedicalRecord = ebeanServer.find(MedicalRecord.class).where()
                .eq("name", "病案首页")
                .eq("patientSignIn.uuid", patientSignInId)
                .findOneOrEmpty();

        List<DiagnoseDto> diagnoseDtoList = new ArrayList<>();
        List<DrgRecordOperationRespDto> operationList = new ArrayList<>();
        OperationLevelDto operationLevel = ebeanServer.find(OperationLevel.class)
                .where().eq("defaultSelection", true).findOne().toDto();
        if (optionalMedicalRecord.isPresent()) {
            MedicalRecord medicalRecordMain = optionalMedicalRecord.get();
            MainMedicalRecord mainMedicalRecord = JSON.parseObject(medicalRecordMain.getContent(), new TypeReference<MainMedicalRecord>() {
            });
            if (mainMedicalRecord.getInput48().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput48().getValue());
            if (mainMedicalRecord.getInput54().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput54().getValue());
            if (mainMedicalRecord.getInput60().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput60().getValue());
            if (mainMedicalRecord.getInput66().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput66().getValue());
            if (mainMedicalRecord.getInput72().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput72().getValue());
            if (mainMedicalRecord.getInput78().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput78().getValue());
            if (mainMedicalRecord.getInput51().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput51().getValue());
            if (mainMedicalRecord.getInput57().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput57().getValue());
            if (mainMedicalRecord.getInput63().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput63().getValue());
            if (mainMedicalRecord.getInput69().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput69().getValue());
            if (mainMedicalRecord.getInput75().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput75().getValue());
            if (mainMedicalRecord.getInput81().getValue() != null)
                diagnoseDtoList.add(mainMedicalRecord.getInput81().getValue());

            if (mainMedicalRecord.getInput111().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput111().getValue(), operationLevel));
            if (mainMedicalRecord.getInput121().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput121().getValue(), operationLevel));
            if (mainMedicalRecord.getInput131().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput131().getValue(), operationLevel));
            if (mainMedicalRecord.getInput141().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput141().getValue(), operationLevel));
            if (mainMedicalRecord.getInput151().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput151().getValue(), operationLevel));
            if (mainMedicalRecord.getInput161().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput161().getValue(), operationLevel));
            if (mainMedicalRecord.getInput171().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput171().getValue(), operationLevel));
            if (mainMedicalRecord.getInput181().getValue() != null)
                operationList.add(this.cloneToOperation(mainMedicalRecord.getInput181().getValue(), operationLevel));

        }
        drgMedicalRecordInfo.setDiagnoseList(diagnoseDtoList);
        drgMedicalRecordInfo.setOperationList(operationList);


//
//        List<MedicalRecord> signOutMedicalRecordList = ebeanServer.find(MedicalRecord.class).where()
//                .eq("type.name", "出 (转) 院记录")
//                .eq("patientSignIn.uuid", patientSignInId)
//                .findList();
//        if (signOutMedicalRecordList.size() > 0) {
//            MedicalRecord signOutMedicalRecord = signOutMedicalRecordList.get(0);
//            drgMedicalRecordInfo.setSignInCondition(this.getDrgMedicalRecordContentInfo(signOutMedicalRecord, "ryqk"));
//            drgMedicalRecordInfo.setTreatmentProcess(this.getDrgMedicalRecordContentInfo(signOutMedicalRecord, "zlgc"));
//            drgMedicalRecordInfo.setSingOutCondition(this.getDrgMedicalRecordContentInfo(signOutMedicalRecord, "cyqk"));
//            drgMedicalRecordInfo.setSignOutPrescription(this.getDrgMedicalRecordContentInfo(signOutMedicalRecord, "cyyz"));
//        }
        return drgMedicalRecordInfo;
    }

    private DrgRecordOperationRespDto cloneToOperation(ICD9RespDto icd9, OperationLevelDto defaultOperationLevel) {
        DrgRecordOperationRespDto dto = new DrgRecordOperationRespDto();
        dto.setIcd9(icd9);
        dto.setOperationLevel(defaultOperationLevel);
        dto.setMainOperation(false);
        dto.setYyxOperation(false);
        return dto;
    }

    private String getDrgMedicalRecordContentInfo(MedicalRecord medicalRecord, String infoHeaderName) {
        Document doc = Jsoup.parse(medicalRecord.getContent());
        Element node = doc.getElementById(infoHeaderName);
        if (node != null) {
            String text = node.text();
            return text.substring(text.indexOf("：") + 1);
        } else
            return null;

    }

    private final String drgApiSuccessCode = "CIS_R_00";

    @Transactional
    public UUID saveDrgRecord(DrgRecordSaveDto saveDto) {
        DrgRecord record = saveDto.toEntity();
        if (record.getUuid() != null)
            ebeanServer.update(record);
        else {
            Optional<DrgRecord> existingDrgRecord = ebeanServer.find(DrgRecord.class).where().eq("patientSignIn.uuid", saveDto.getPatientSignInId()).findOneOrEmpty();
            if (existingDrgRecord.isPresent())
                throw new ApiValidationException("已经有记录存在，请刷新当前页面");
            ebeanServer.save(record);
        }
        return record.getUuid();
    }

    public DrgRecord getDrgRecord(UUID drgRecordId) {
        return ebeanServer.find(DrgRecord.class, drgRecordId);
    }

    @Transactional
    public DrgRecord uploadDrgRecord(UUID drgRecordId) {
        DrgRecord record = ebeanServer.find(DrgRecord.class, drgRecordId);
        String data = JSONObject.toJSONString(record.toUploadDto(this.ybHospitalCode, this.mockDrgUpload), SerializerFeature.UseSingleQuotes);

        DrgApiRespDto respDto = this.callDrgWebApi(data, "5001");
        this.updateDrgRecordUploadStatus(record, respDto, true);
        return record;

        //this.saveFeeUploadResult(uploadResultDto);
    }

    private void updateDrgRecordUploadStatus(DrgRecord record, DrgApiRespDto respDto, boolean uploaded) {

        if (respDto.getCode().equals(this.drgApiSuccessCode)) {
            record.setUploaded(uploaded);
            record.setUploadResponse(respDto.getResult());
            ebeanServer.update(record);
        } else
            throw new ApiValidationException(respDto.getMsg());

    }

    @Transactional
    public DrgRecord cancelUploadedDrgRecord(UUID drgRecordId) {
        DrgRecord record = ebeanServer.find(DrgRecord.class, drgRecordId);
        if (!record.getUploaded())
            throw new ApiValidationException("病案信息尚未上传");
        String data = JSONObject.toJSONString(record.toCancelDto(this.ybHospitalCode, this.mockDrgUpload));
        DrgApiRespDto respDto = this.callDrgWebApi(data, "5003");
        this.updateDrgRecordUploadStatus(record, respDto, false);
        return record;
    }

    private DrgApiRespDto callDrgWebApi(String data, String serviceId) {
        DrgUploadDto uploadDto = new DrgUploadDto();
        uploadDto.setServiceId(serviceId);
        uploadDto.setData(data);

        String url = this.interfaceBaseUrl + "drg/service/post";
        return this.restTemplate.postForObject(url, uploadDto, DrgApiRespDto.class);
    }

    public Boolean allFeeDownloadedFromYB(UUID patientSignInId) {
        Optional<Fee> notDownloadedFee =
                ebeanServer.find(Fee.class)
                        .where()
                        .eq("centerDownloadedFee", null)
                        .eq("patientSignIn.uuid", patientSignInId)
                        .eq("feeStatus", FeeStatus.confirmed)
                        .setMaxRows(1)
                        .findOneOrEmpty();
        return !notDownloadedFee.isPresent();
    }

    public Boolean allFeeValidFromYB(UUID patientSignInId) {
        Optional<FeeDownloadLine> notValidFee =
                ebeanServer.find(FeeDownloadLine.class)
                        .where()
                        .eq("feeDownload.patientSignIn.uuid", patientSignInId)
                        .eq("isValid", false)
                        .setMaxRows(1)
                        .findOneOrEmpty();
        return !notValidFee.isPresent();
    }


    public SettlementPaymentRequest calculateSettlementPayment(SettlementPaymentFilter dateFilter) {
        ExpressionList<Settlement> el = ebeanServer.find(Settlement.class).where();
        Date endDate = this.addDays(dateFilter.getEndDate(), 1);
        el = el.between("whenCreated", dateFilter.getStartDate(), endDate);
        //el = el.eq("patientSignIn.status", PatientSignInStatus.signedOut);
        el = el.eq("patientSignIn.insuranceType.code", "医保");
        List<Settlement> settlementList = el.findList();

        SettlementPaymentRequest request = new SettlementPaymentRequest();
        for (Settlement settlement : settlementList) {
            String cardInfoString = settlement.getPatientSignIn().getCardInfo().getFullInfo();
            YbCardInfo cardInfo = JSON.parseObject(cardInfoString, new TypeReference<YbCardInfo>() {
            });
            this.setPaymentRequestAccountValue(request.getSelfAccount(), settlement.getDNZHZF().add(settlement.getLNZHZF()), cardInfo);
            this.setPaymentRequestAccountValue(request.getTcAccount(), settlement.getTCJE(), cardInfo);
            this.setPaymentRequestAccountValue(request.getCivilAccount(), settlement.getGWYBZZF(), cardInfo);
            this.setPaymentRequestAccountValue(request.getSeriousDiseaseAccount(), settlement.getDBJE(), cardInfo);
            this.setPaymentRequestAccountValue(request.getBirthAccount(), settlement.getSYJJZF(), cardInfo);
            this.setPaymentRequestAccountValue(request.getMedicalSubsidyAccount(), settlement.getYLJZ(), cardInfo);
            this.setPaymentRequestAccountValue(request.getSupplementInsuranceAccount(), settlement.getBCYLBX(), cardInfo);
            this.setPaymentRequestAccountValue(request.getSpecialCareSubsidy(), settlement.getYFBZ(), cardInfo);
        }
        return request;
    }

    private void setPaymentRequestAccountValue(SettlementAccount settlementAccount, BigDecimal value, YbCardInfo cardInfo) {
        PatientTypePayment patientTypePayment;
        if (cardInfo.getYlrylbmc().equals("在职"))
            patientTypePayment = settlementAccount.getWorkingPayment();
        else
            patientTypePayment = settlementAccount.getRetiredPayment();

        if (cardInfo.getDwlx().equals("企业"))
            patientTypePayment.setCompanyTypePayment(patientTypePayment.getCompanyTypePayment().add(value));
        else if (cardInfo.getDwlx().equals("机关事业团体"))
            patientTypePayment.setCivilTypePayment(patientTypePayment.getCivilTypePayment().add(value));
        else
            patientTypePayment.setOtherTypePayment(patientTypePayment.getOtherTypePayment().add(value));
    }


//    @Transactional
//    public PreSettlementValidationDto getPreSettlementValidation(UUID patientSignInId) {
//        PreSettlementValidationDto dto = new PreSettlementValidationDto();
//        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
//
//        Optional<ViewFeeSummary> optionalFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", patientSignInId).findOneOrEmpty();
//        if (optionalFeeInfo.isPresent()) {
//            ViewFeeSummary feeSummary = optionalFeeInfo.get();
//            dto.setHisTotalFee(feeSummary.getTotalAmount());
//            dto.setHisTotalCoveredFee(feeSummary.getCoveredFeeAmount());
//        }
//
//        if (patientSignIn.getPreSettlement() != null) {
//            dto.setYbTotalCoveredFee(patientSignIn.getPreSettlement().getBXJE());
//            dto.setYbTotalFee(patientSignIn.getPreSettlement().getZje());
//
//
//            if (patientSignIn.getPreSettlement().getReturnDetail()) {
//                List<FeeValidationDto> feeList = new ArrayList<>();
//                this.addNoSettlementFeeList(feeList, patientSignInId);
//                this.addNotUploadedButInSettleFeeList(feeList, patientSignInId);
//                this.addValueMisMatchFeeList(feeList, patientSignInId);
//                dto.setFeeList(feeList);
//            }
//        }
//
//        return dto;
//
//    }
//
//    private void addNoSettlementFeeList(List<FeeValidationDto> feeDtoList, UUID patientSignInId) {
//        List<Fee> feeList = ebeanServer.find(Fee.class).where()
//                .eq("patientSignIn.uuid", patientSignInId)
//                .eq("feeStatus", FeeStatus.confirmed)
//                .or()
//                .eq("selfPay", false)
//                .eq("selfPay", null)
//                .endOr()
//                .ne("feeUploadResult", null)
//                .eq("preSettlementFee", null)
//                .findList();
//        this.addToReturnDto(feeList, feeDtoList, FeeValidationErrorType.uploadedNotInSettlement);
//    }
//
//
//    private void addNotUploadedButInSettleFeeList(List<FeeValidationDto> feeDtoList, UUID patientSignInId) {
//        List<Fee> feeList = ebeanServer.find(Fee.class).where()
//                .eq("patientSignIn.uuid", patientSignInId)
//                .eq("feeStatus", FeeStatus.confirmed)
//                .or()
//                .eq("selfPay", false)
//                .eq("selfPay", null)
//                .endOr()
//                .eq("feeUploadResult", null)
//                .ne("preSettlementFee", null)
//                .findList();
//        this.addToReturnDto(feeList, feeDtoList, FeeValidationErrorType.notUploadedButInSettle);
//    }
//
//    private void addValueMisMatchFeeList(List<FeeValidationDto> feeDtoList, UUID patientSignInId) {
//        List<Fee> feeList = ebeanServer.find(Fee.class).where()
//                .eq("patientSignIn.uuid", patientSignInId)
//                .eq("feeStatus", FeeStatus.confirmed)
//                .or()
//                .eq("selfPay", false)
//                .eq("selfPay", null)
//                .endOr()
//                .ne("feeUploadResult", null)
//                .ne("preSettlementFee", null)
//                .findList();
//
//        List<Fee> invalidFeeList = feeList.stream().filter(f ->f.getPreSettlementFee().getYbje().compareTo(f.getFeeUploadResult().getInsuranceAmount())!=0).collect(Collectors.toList());
//        this.addToReturnDto(invalidFeeList, feeDtoList, FeeValidationErrorType.feeAmountNotMatch);
//    }
//
//    private void addToReturnDto(List<Fee> feeList, List<FeeValidationDto> feeDtoList, FeeValidationErrorType errorType) {
//        for (Fee fee : feeList) {
//            FeeValidationDto dto = new FeeValidationDto();
//            dto.setErrorType(errorType);
//            dto.setHisFee(fee.toListDto());
//
//            PreSettlementFee preSettlementFee = fee.getPreSettlementFee();
//            if (preSettlementFee != null) {
//
//                dto.setYbFeeCoveredValue(preSettlementFee.getYbje());
//                dto.setYbFeeId(preSettlementFee.getUuid());
//                dto.setYbFeeName(preSettlementFee.getKpxmmc());
//                dto.setYbFeeNumber(preSettlementFee.getFybh());
//                dto.setYbFeeValue(preSettlementFee.getXmje());
//            }
//            feeDtoList.add(dto);
//        }
//    }
}
