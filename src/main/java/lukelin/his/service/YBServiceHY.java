package lukelin.his.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.account.ViewFeeSummary;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.patient_sign_in.PatientContact;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.entity.yb.hy.PreSettlementHY;
import lukelin.his.domain.entity.yb.hy.SettlementHY;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.yb_hy.Req.*;
import lukelin.his.dto.yb_hy.PramWrapper;
import lukelin.his.dto.yb_hy.Resp.FeeUploadResp;
import lukelin.his.dto.yb_hy.Resp.SettlementResp;
import lukelin.his.system.Utils;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class YBServiceHY extends BaseHisService {

    @Value("${ybInterface.hybaseUrl}")
    protected String interfaceBaseUrl;

    @Autowired
    protected RestTemplate restTemplate;

    public void yBSignIn(PatientSignIn patientSignIn) {
        if (patientSignIn.getYbSignIn() != null)
            throw new ApiValidationException("病人已医保登记入院");


        CardInfo cardInfo = this.getPatientInfo(patientSignIn);
        patientSignIn.setCardInfo(cardInfo);

        SignInReq signInReq = patientSignIn.toHYYBSignIn(cardInfo.getPatientNumber(), false);

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2401");
        wrapper.setInputNodeName("mdtrtinfo");
        wrapper.setAreaCode(cardInfo.getAreaCode());
        wrapper.setInput(signInReq);

        List<Disease> diseaseList = patientSignIn.toHYYBSignInDisease(cardInfo.getPatientNumber(), false);
        wrapper.setInputNodeNameTwo("diseinfo");
        wrapper.setInputTwo(diseaseList);

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        //储存医保信息
        String serverCode = JSONObject.parseObject(response).getJSONObject("result").get("mdtrt_id").toString();
        YBSignIn signIn = new YBSignIn();
        signIn.setCode(serverCode);
        signIn.setId(serverCode);
        patientSignIn.setYbSignIn(signIn);
        signIn.setPatientSignIn(patientSignIn);
        ebeanServer.save(signIn);
    }

    public void insuTypeCheck(PatientSignIn patientSignIn) {
        InsuTypeCheckReq checkReq = new InsuTypeCheckReq();
//        if (patientSignIn.getCardInfo() == null) {
//            CardInfo cardInfo = this.getPatientInfo(patientSignIn);
//            patientSignIn.setCardInfo(cardInfo);
//        }
        checkReq.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        checkReq.setInsutype(patientSignIn.getInsuranceType().getExtraInfo());
        checkReq.setFixmedins_code(hospitalCode);
        checkReq.setMed_type("2101"); //普通住院
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (patientSignIn.getSignInDate() != null)
            checkReq.setBegntime(df.format(patientSignIn.getSignInDate()));
        else
            checkReq.setBegntime(df.format(new Date()));

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2001");
        wrapper.setInputNodeName("data");
        wrapper.setAreaCode(patientSignIn.getCardInfo().getAreaCode());
        wrapper.setInput(checkReq);

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        //储存医保信息
        if (response == null)
            throw new ApiValidationException("病人没有当前所选择的医保保险类型");
    }

    private CardInfo getPatientInfo(PatientSignIn patientSignIn) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("1101");
        wrapper.setInputNodeName("data");

        PatientInfoReq patientInfoReq = new PatientInfoReq();
        patientInfoReq.setCertno(patientSignIn.getPatient().getIdNumber());
        patientInfoReq.setMdtrt_cert_no(patientSignIn.getPatient().getIdNumber());
        patientInfoReq.setPsn_name(patientSignIn.getPatient().getName());
        wrapper.setInput(patientInfoReq);

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        JSONObject jsonObject = JSONObject.parseObject(response);

        Map<String, String> baseinfo = (Map<String, String>) jsonObject.get("baseinfo");
        String psn_no = baseinfo.get("psn_no");

        JSONArray insuinfoList = jsonObject.getJSONArray("insuinfo");
        String areaCode = "";
        Optional<Object> optionalInsuType = insuinfoList.stream().filter(i -> ((JSONObject) i).getString("insutype").equals(patientSignIn.getInsuranceType().getExtraInfo())).findFirst();
        if (optionalInsuType.isPresent()) {
            Map<String, String> insuInfo = (Map<String, String>) optionalInsuType.get();
            areaCode = insuInfo.get("insuplc_admdvs");
        } else
            throw new ApiValidationException("病人没有当前所选择的医保保险类型");


        if (patientSignIn.getCardInfo() != null)
            ebeanServer.delete(patientSignIn.getCardInfo());
        CardInfo cardInfo = new CardInfo();
        cardInfo.setFullInfo(response);
        cardInfo.setPatientNumber(psn_no);
        cardInfo.setPatientSignIn(patientSignIn);
        cardInfo.setAreaCode(areaCode);
        ebeanServer.save(cardInfo);
        return cardInfo;
    }

    @Transactional
    public void cancelYBSignIn(PatientSignIn patientSignIn) {
        //医保取消住院
        YBSignIn ybSignIn = patientSignIn.getYbSignIn();
        CardInfo cardInfo = patientSignIn.getCardInfo();

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2404");
        wrapper.setInputNodeName("data");
        wrapper.setAreaCode(cardInfo.getAreaCode());
        SignInCancelReq cancelReq = new SignInCancelReq();
        cancelReq.setMdtrt_id(ybSignIn.getId());
        cancelReq.setPsn_no(cardInfo.getPatientNumber());
        wrapper.setInput(cancelReq);

        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

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
        patientSignIn.setStatus(PatientSignInStatus.pendingSignIn);
        ebeanServer.save(patientSignIn);

    }

    public void updateSignInInfo(PatientSignIn patientSignIn) {

        CardInfo cardInfo = patientSignIn.getCardInfo();
        SignInReq signInReq = patientSignIn.toHYYBSignIn(cardInfo.getPatientNumber(), true);
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2403");
        wrapper.setInputNodeName("adminfo");
        wrapper.setAreaCode(cardInfo.getAreaCode());
        wrapper.setInput(signInReq);

        List<Disease> diseaseList = patientSignIn.toHYYBSignInDisease(cardInfo.getPatientNumber(), true);
        wrapper.setInputNodeNameTwo("diseinfo");
        wrapper.setInputTwo(diseaseList);

        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);
    }

    @Transactional
    public void matchMedicine(UUID medicineId) {
        Medicine medicine = this.findById(Medicine.class, medicineId);
        String matchType = "101";
        if (medicine.chineseMedicine())
            matchType = "102";
        if (medicine.getMatchedMedicine() != null)
            this.deleteMedicineMatch(medicine, matchType);

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3301");
        wrapper.setInputNodeName("data");

        MatchRequest matchRequest = new MatchRequest();
        matchRequest.setFixmedins_hilist_id(medicine.getCode().toString());
        matchRequest.setFixmedins_hilist_name(medicine.getName());

        matchRequest.setList_type(matchType);
        matchRequest.setMed_list_codg(medicine.getCenterMedicine().getBZBM());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        matchRequest.setBegndate(medicine.getCenterMedicine().getKSRQ().format(pattern));
        matchRequest.setEndDate(medicine.getCenterMedicine().getJSRQ().format(pattern));
        wrapper.setInput(matchRequest);


        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

        MedicineMatchDownload matchedMedicine = new MedicineMatchDownload();
        matchedMedicine.setMedicine(medicine);
        matchedMedicine.setReference(medicine.getCenterMedicine().getBZBM());
        matchedMedicine.setStatus("1");
        ebeanServer.save(matchedMedicine);
    }

    private final String hospitalCode = "H43040500103";

    private void deleteMedicineMatch(Medicine medicine, String matchType) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3302");
        wrapper.setInputNodeName("data");

        DeleteMatchReq deleteReq = new DeleteMatchReq();
        deleteReq.setFixmedins_code(hospitalCode);
        deleteReq.setFixmedins_hilist_id(medicine.getCode().toString());
        deleteReq.setList_type(matchType);
        deleteReq.setMed_list_codg(medicine.getMatchedMedicine().getReference());
        wrapper.setInput(deleteReq);

        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

        ebeanServer.delete(medicine.getMatchedMedicine());
    }

    public void matchItem(UUID itemId) {
        Item item = this.findById(Item.class, itemId);
        String matchType = "301";
        if (item.getMatchedItem() != null)
            this.deleteMatchedItem(item, matchType);

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3301");
        wrapper.setInputNodeName("data");

        MatchRequest matchRequest = new MatchRequest();
        matchRequest.setFixmedins_hilist_id(item.getCode().toString());
        matchRequest.setFixmedins_hilist_name(item.getName());
        matchRequest.setList_type(matchType);
        matchRequest.setMed_list_codg(item.getCenterTreatment().getBZBM());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        matchRequest.setBegndate(item.getCenterTreatment().getSTARTDATE().format(pattern));
        matchRequest.setEndDate(item.getCenterTreatment().getENDDATE().format(pattern));
        wrapper.setInput(matchRequest);


        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

        ItemMatchDownload matchedItem = new ItemMatchDownload();
        matchedItem.setItem(item);
        matchedItem.setReference(item.getCenterTreatment().getBZBM());
        matchedItem.setStatus("1");
        ebeanServer.save(matchedItem);
    }

    private void deleteMatchedItem(Item item, String matchType) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3302");
        wrapper.setInputNodeName("data");

        DeleteMatchReq deleteReq = new DeleteMatchReq();
        deleteReq.setFixmedins_code(hospitalCode);
        deleteReq.setFixmedins_hilist_id(item.getCode().toString());
        deleteReq.setList_type(matchType);
        deleteReq.setMed_list_codg(item.getMatchedItem().getReference());
        wrapper.setInput(deleteReq);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

        ebeanServer.delete(item.getMatchedItem());
    }

    public void matchTreatment(UUID treatmentId) {
        Treatment treatment = this.findById(Treatment.class, treatmentId);
        String matchType = "201";
        if (treatment.getMatchedTreatment() != null)
            this.deleteMatchedTreatment(treatment, matchType);

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3301");
        wrapper.setInputNodeName("data");

        MatchRequest matchRequest = new MatchRequest();
        matchRequest.setFixmedins_hilist_id(treatment.getCode().toString());
        matchRequest.setFixmedins_hilist_name(treatment.getName());
        matchRequest.setList_type(matchType);
        matchRequest.setMed_list_codg(treatment.getCenterTreatment().getBZBM());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        matchRequest.setBegndate(treatment.getCenterTreatment().getSTARTDATE().format(pattern));
        matchRequest.setEndDate(treatment.getCenterTreatment().getENDDATE().format(pattern));
        wrapper.setInput(matchRequest);


        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

        TreatmentMatchDownload matchedTreatment = new TreatmentMatchDownload();
        matchedTreatment.setTreatment(treatment);
        matchedTreatment.setReference(treatment.getCenterTreatment().getBZBM());
        matchedTreatment.setStatus("1");
        ebeanServer.save(matchedTreatment);
    }

    private void deleteMatchedTreatment(Treatment treatment, String matchType) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3302");
        wrapper.setInputNodeName("data");

        DeleteMatchReq deleteReq = new DeleteMatchReq();
        deleteReq.setFixmedins_code(hospitalCode);
        deleteReq.setFixmedins_hilist_id(treatment.getCode().toString());
        deleteReq.setList_type(matchType);
        deleteReq.setMed_list_codg(treatment.getMatchedTreatment().getReference());
        wrapper.setInput(deleteReq);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);

        ebeanServer.delete(treatment.getMatchedTreatment());
    }

    @Transactional
    public void uploadPatientPendingFee(UUID patientSignInId) {
        List<Fee> pendingUploadFeeList = this.getPatientPendingUploadFee(patientSignInId);
        if (pendingUploadFeeList.size() == 0)
            return;
        List<FeeUploadReqHY> uploadReqHYList = new ArrayList<>();
        int i = 1;
        for (Fee fee : pendingUploadFeeList) {
            uploadReqHYList.add(fee.toHYUploadDto());
            i++;
            if (i > 100) {
                this.uploadFee(uploadReqHYList);
                i = 1;
                uploadReqHYList = new ArrayList<>();
            }
        }

        if (uploadReqHYList.size() > 0)
            this.uploadFee(uploadReqHYList);

        //todo presettle
    }

    private void uploadFee(List<FeeUploadReqHY> uploadReqHYList) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2301");
        wrapper.setInputNodeName("feedetail");
        wrapper.setInput(uploadReqHYList);
        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);

        for (Object feeUploaded : JSONObject.parseObject(response).getJSONArray("result")) {
            String jsonString = feeUploaded.toString();
            FeeUploadResp uploadResp = JSONObject.parseObject(jsonString, FeeUploadResp.class);
            FeeUploadResult result = new FeeUploadResult();
            result.setReference(jsonString);
            Fee fee = this.findById(Fee.class, UUID.fromString(uploadResp.getMemo()));
            result.setFee(fee);
            result.setUploadStatus("0"); //成功
            result.setInsuranceAttribute(uploadResp.getChrgitm_lv());
            result.setSelfPayAmount(BigDecimal.ZERO);
            result.setSelfRatioPayAmount(uploadResp.getFulamt_ownpay_amt());
            result.setSelfRatio(uploadResp.getSelfpay_prop());
            result.setServerId(fee.getUuid().toString().substring(0, 30));
            result.setUnitAmountLimit(uploadResp.getPric_uplmt_amt());
            ebeanServer.save(result);
        }
    }

    public List<Fee> getPatientPendingUploadFee(UUID patientSignInId) {
        return ebeanServer.find(Fee.class).where()
                .eq("feeUploadResult", null)
                .eq("patientSignIn.uuid", patientSignInId)
                .eq("feeStatus", FeeStatus.confirmed)
                .findList();
    }

    @Transactional
    public void deleteAllSignInFee(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2302");
        wrapper.setInputNodeName("data");
        CancelFeeReq cancelFeeReq = new CancelFeeReq();
        cancelFeeReq.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        cancelFeeReq.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        wrapper.setInput(cancelFeeReq);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);


        ebeanServer.find(FeeUploadResult.class)
                .where().eq("fee.patientSignIn.uuid", patientSignInId).delete();
        ebeanServer.find(FeeDownload.class)
                .where().eq("patientSignIn.uuid", patientSignInId).delete();
        ebeanServer.find(PreSettlement.class)
                .where().eq("patientSignIn.uuid", patientSignInId).delete();
    }

    @Transactional
    public void cancelFee(Fee feeToCancel) {
        if (feeToCancel.getFeeUploadResult() == null)
            return;
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2302");
        wrapper.setInputNodeName("data");
        CancelFeeReq cancelFeeReq = new CancelFeeReq();
        cancelFeeReq.setPsn_no(feeToCancel.getPatientSignIn().getCardInfo().getPatientNumber());
        cancelFeeReq.setMdtrt_id(feeToCancel.getPatientSignIn().getYbSignIn().getId());
        cancelFeeReq.setFeedetl_sn(feeToCancel.getUuid().toString().substring(0, 30));
        wrapper.setInput(cancelFeeReq);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);


        if (feeToCancel.getPreSettlementFee() != null)
            ebeanServer.delete(feeToCancel.getPreSettlementFee());

        ebeanServer.find(FeeUploadResult.class)
                .where().eq("fee.uuid", feeToCancel.getUuid()).delete();
    }

    @Transactional
    public SettlementResp preSettle(UUID patientSignInId) {

        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        PramWrapper wrapper = this.getSettlePram(patientSignIn, "2303");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        JSONObject settleInfo = JSONObject.parseObject(response).getJSONObject("setlinfo");
        SettlementResp settlementResp = JSONObject.toJavaObject(settleInfo, SettlementResp.class);

        PreSettlementHY settlementHY = settlementResp.toPreSettlement();
        settlementHY.setFullInfo(response);
        settlementHY.setPatientSignIn(patientSignIn);
        if (patientSignIn.getPreSettlementHY() != null)
            ebeanServer.delete(patientSignIn.getPreSettlementHY());
        ebeanServer.save(settlementHY);

        return settlementResp;
    }

    @Transactional
    public SettlementResp finalSettle(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        PramWrapper wrapper = this.getSettlePram(patientSignIn, "2304");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        JSONObject settleInfo = JSONObject.parseObject(response).getJSONObject("setlinfo");
        SettlementResp settlementResp = JSONObject.toJavaObject(settleInfo, SettlementResp.class);

        SettlementHY settlementHY = settlementResp.toSettlement();
        settlementHY.setFullInfo(response);
        settlementHY.setPatientSignIn(patientSignIn);
        if (patientSignIn.getSettlementHY() != null)
            ebeanServer.delete(patientSignIn.getSettlementHY());
        ebeanServer.save(settlementHY);
        return settlementResp;
    }

    @Transactional
    public String uploadSettlement(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("4101");
        wrapper.setInputNodeName("setlinfo");
        SettlementUpload toUpload = this.toSettleUploadDto(patientSignIn);
        wrapper.setInput(toUpload);

        List<Disease> diseaseList = patientSignIn.toHYYBSignInDisease(patientSignIn.getCardInfo().getPatientNumber(), false);
        wrapper.setInputNodeNameTwo("diseinfo");
        wrapper.setInputTwo(diseaseList);

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        SettlementHY settlementHY = patientSignIn.getSettlementHY();
        settlementHY.setUpload_result(response);
        ebeanServer.save(settlementHY);
        return response;
    }

    private SettlementUpload toSettleUploadDto(PatientSignIn patientSignIn) {
        SettlementUpload setlInfo = new SettlementUpload();
        SettlementHY settlementHY = patientSignIn.getSettlementHY();
        setlInfo.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        setlInfo.setSetl_id(patientSignIn.getSettlementHY().getSetl_id());
        setlInfo.setFixmedins_name("衡阳新安康复医院");
        setlInfo.setFixmedins_code(this.hospitalCode);
        setlInfo.setMedcasno(patientSignIn.getSignInNumberCode());
        setlInfo.setPsn_name(patientSignIn.getPatient().getName());
        setlInfo.setGend(patientSignIn.getPatient().getGender().getExtraInfo());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setlInfo.setBrdy(df.format(patientSignIn.getPatient().getBirthday()));
        setlInfo.setAge(Utils.getAge(patientSignIn.getPatient().getBirthday()));
        setlInfo.setNtly("CHN");
        setlInfo.setNaty("1");
        setlInfo.setPatn_cert_type("01");
        setlInfo.setCertno(patientSignIn.getPatient().getIdNumber());
        if (patientSignIn.getPatient().getOccupation() == null)
            setlInfo.setPrfs("未知");
        else
            setlInfo.setPrfs(patientSignIn.getPatient().getOccupation().getName());

        PatientContact patientContact = patientSignIn.getPatient().getPatientContactList().get(0);
        setlInfo.setConer_name(patientContact.getName());
        setlInfo.setPatn_rlts(patientContact.getRelationship());
        setlInfo.setConer_addr(patientContact.getAddress());
        setlInfo.setConer_tel(patientContact.getPhoneNumber());
        setlInfo.setHi_type(settlementHY.getInsutype());
        setlInfo.setInsuplc(patientSignIn.getCardInfo().getAreaCode());
        setlInfo.setIpt_med_type("1");//住院类型1
        setlInfo.setAdm_caty("A03.11"); //其他
        setlInfo.setDscg_caty("A03.11"); //其他
        setlInfo.setMaindiag_flag("1");
        setlInfo.setBill_code("defualCode");
        setlInfo.setBill_no("defaultNumber");
        setlInfo.setBiz_sn("000");
        setlInfo.setSetl_begn_date(settlementHY.getSetl_time());
        setlInfo.setSetl_end_date(settlementHY.getSetl_time());
        setlInfo.setPsn_selfpay(settlementHY.getFulamt_ownpay_amt()); //自付
        setlInfo.setPsn_ownpay(settlementHY.getPsn_part_amt()); //自费
        setlInfo.setAcct_pay(settlementHY.getAcct_pay());
        setlInfo.setPsn_cashpay(settlementHY.getPsn_cash_pay());
        setlInfo.setHi_paymtd("1"); //医保支付方式
        setlInfo.setHsorg("default");
        setlInfo.setHsorg_opter("default");
        setlInfo.setMedins_fill_dept("default");
        setlInfo.setMedins_fill_psn("default");
        return setlInfo;
    }


    private PramWrapper getSettlePram(PatientSignIn patientSignIn, String infoNumber) {
        Optional<ViewFeeSummary> optionalFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", patientSignIn.getUuid()).findOneOrEmpty();
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (optionalFeeInfo.isPresent())
            totalAmount = optionalFeeInfo.get().getTotalAmount();

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber(infoNumber);
        wrapper.setInputNodeName("data");

        FinalSettleReq settleReq = new PreSettleReq();
        settleReq.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        settleReq.setMdtrt_cert_type("02");
        settleReq.setMdtrt_cert_no(patientSignIn.getPatient().getIdNumber());
        settleReq.setPsn_cert_type("2");
        settleReq.setCertno(patientSignIn.getPatient().getIdNumber());
        settleReq.setMedfee_sumamt(totalAmount.toString());
        settleReq.setPsn_setlway("01");
        settleReq.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        settleReq.setMid_setl_flag("0");
        settleReq.setAcct_used_flag("0");

        Map<String, String> insuInfo = this.getinsuInfo(patientSignIn);

        if (insuInfo != null) {
            settleReq.setPsn_type(insuInfo.get("psn_type"));
            settleReq.setInsutype(insuInfo.get("insutype"));
        }
        wrapper.setInput(settleReq);
        return wrapper;
    }


    public void signOut(PatientSignIn patientSignIn) {
        SignOutReq req = new SignOutReq();
        req.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        req.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        req.setInsutype(patientSignIn.getInsuranceType().getExtraInfo());

        req.setDscg_dept_codg(patientSignIn.getDepartmentTreatment().getDepartment().getCode());
        req.setDscg_dept_name(patientSignIn.getDepartmentTreatment().getDepartment().getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String signOutDate = df.format(patientSignIn.getSingOutRequest().getSignOutDate());
        req.setEndtime(signOutDate);
        req.setDscg_way("1"); //医嘱离院

        List<Disease> diseaseList = new ArrayList<>();
        Integer i = 1;
        for (Diagnose diagnose : patientSignIn.getSingOutRequest().getDiagnoseSet()) {
            Disease disease = new Disease();
            disease.setMdtrt_id(patientSignIn.getYbSignIn().getId());
            disease.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
            disease.setDiag_type("2");
            if (i == 1)
                disease.setMaindiag_flag("1");
            else
                disease.setMaindiag_flag("0");
            disease.setDiag_srt_no(i.toString());
            disease.setDiag_code(diagnose.getCode());
            disease.setDiag_name(diagnose.getName());
            disease.setDiag_dept(patientSignIn.getDepartmentTreatment().getDepartment().getName());
            disease.setDise_dor_no(patientSignIn.getDoctor().getDoctorAgreementNumber().getAgreementNumber());
            disease.setDise_dor_name(patientSignIn.getDoctor().getName());
            disease.setDiag_time(signOutDate);
            diseaseList.add(disease);
            i++;
        }


        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2402");
        wrapper.setInputNodeName("dscginfo");
        wrapper.setInput(req);
        wrapper.setInputNodeNameTwo("diseinfo");
        wrapper.setInputTwo(diseaseList);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);
    }

    private Map<String, String> getinsuInfo(PatientSignIn patientSignIn) {
        Map<String, String> insuInfo = null;
        CardInfo cardInfo = patientSignIn.getCardInfo();
        JSONObject jsonObject = JSONObject.parseObject(cardInfo.getFullInfo());
        JSONArray insuinfoList = jsonObject.getJSONArray("insuinfo");

        Map<String, String> matchedInsuInfo =
                (Map<String, String>) insuinfoList.stream().filter(i -> ((Map<String, String>) i).get("insutype").equals(patientSignIn.getInsuranceType().getExtraInfo())).findFirst().get();
        return matchedInsuInfo;
    }


    String areaCode = "430405";

    @Transactional
    public void downloadCenterTreatment() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date lastUpdateDate = null;
        try {
            lastUpdateDate = dateFormat.parse("2020-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<CenterTreatment> latestCenterTreatment = ebeanServer.find(CenterTreatment.class).orderBy("whenCreated desc").setMaxRows(1).findOneOrEmpty();
        if (latestCenterTreatment.isPresent())
            lastUpdateDate = latestCenterTreatment.get().getWhenModified();

        String recordType = "201";//诊疗服务
        String treatmentResponse = this.downloadEntity(recordType, lastUpdateDate, 1); //诊疗服务
        this.saveTreatment(recordType, treatmentResponse, lastUpdateDate);
        recordType = "301";
        String itemResponse = this.downloadEntity(recordType, lastUpdateDate, 1); //医用耗材
        this.saveTreatment(recordType, itemResponse, lastUpdateDate);

    }

    private void saveTreatment(String recordType, String treatmentResponse, Date lastUpdateDate) {
        Integer pageNumber = JSONObject.parseObject(treatmentResponse).getInteger("pages");
        for (int i = 1; i <= pageNumber; i++) {
            String response = this.downloadEntity(recordType, lastUpdateDate, i);
            JSONObject respObject = JSONObject.parseObject(response);
            for (Object centerTreatmentResp : respObject.getJSONArray("data").stream().toArray()) {
                JSONObject jsCenTreatment = (JSONObject) centerTreatmentResp;
                String centerCode = jsCenTreatment.getString("hilist_code");
                String rid = jsCenTreatment.getString("rid");
                Optional<CenterTreatment> optionalCenterTreatment = ebeanServer.find(CenterTreatment.class).where().eq("ZXNM", rid).findOneOrEmpty();
                CenterTreatment centerTreatment = null;
                if (optionalCenterTreatment.isPresent())
                    centerTreatment = optionalCenterTreatment.get();
                else
                    centerTreatment = new CenterTreatment();
                centerTreatment.setBZBM(centerCode);
                centerTreatment.setZXNM(rid);
                centerTreatment.setFullInfo(centerTreatmentResp.toString());
                centerTreatment.setJYFL(jsCenTreatment.getString("chrgitm_lv"));
                centerTreatment.setSFXMMC(jsCenTreatment.getString("hilist_name"));
                centerTreatment.setSTARTDATE(jsCenTreatment.getDate("begndate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                centerTreatment.setENDDATE(jsCenTreatment.getDate("enddate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                centerTreatment.setPYM(jsCenTreatment.getString("pinyin"));
                ebeanServer.save(centerTreatment);
            }
        }
    }

    private String downloadEntity(String recordType, Date lastUpdateDate, Integer pageNumber) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("1312");
        wrapper.setInputNodeName("data");

        CenterRecordDownload downloadReq = new CenterRecordDownload();
        downloadReq.setList_type(recordType);
        downloadReq.setPage_num(pageNumber.toString());
        downloadReq.setPage_size("1000");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        downloadReq.setUpdt_time(df.format(lastUpdateDate));
        downloadReq.setInsu_admdvs(this.areaCode);
        wrapper.setInput(downloadReq);

        return this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
    }

    @Transactional
    public void downloadCenterMedicine() {
        String recordType = "101";//西药

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date lastUpdateDate = null;
        try {
            lastUpdateDate = dateFormat.parse("2020-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<CenterMedicine> latestCenterMedicine = ebeanServer.find(CenterMedicine.class).orderBy("whenCreated desc").setMaxRows(1).findOneOrEmpty();
        if (latestCenterMedicine.isPresent())
            lastUpdateDate = latestCenterMedicine.get().getWhenModified();
        String medicineWestResponse = this.downloadEntity(recordType, lastUpdateDate, 1); //西药
        this.saveMedicine(recordType, medicineWestResponse, lastUpdateDate);
        recordType = "102";
        String medicineChineseResponse = this.downloadEntity(recordType, lastUpdateDate, 1); //西药
        this.saveMedicine(recordType, medicineChineseResponse, lastUpdateDate);
    }

    private void saveMedicine(String recordType, String medicineResponse, Date lastUpdateDate) {
        Integer pageNumber = JSONObject.parseObject(medicineResponse).getInteger("pages");
        for (int i = 1; i <= pageNumber; i++) {
            String response = this.downloadEntity(recordType, lastUpdateDate, i);
            JSONObject respObject = JSONObject.parseObject(response);
            for (Object centerMedicineResp : respObject.getJSONArray("data").stream().toArray()) {
                JSONObject jsCenMedicine = (JSONObject) centerMedicineResp;
                String centerCode = jsCenMedicine.getString("hilist_code");
                String rid = jsCenMedicine.getString("rid");
                Optional<CenterMedicine> optionalCenterMedicine = ebeanServer.find(CenterMedicine.class).where().eq("ZXNM", rid).findOneOrEmpty();
                CenterMedicine centerMedicine = null;
                if (optionalCenterMedicine.isPresent())
                    centerMedicine = optionalCenterMedicine.get();
                else
                    centerMedicine = new CenterMedicine();
                centerMedicine.setBZBM(centerCode);
                centerMedicine.setZXNM(rid);
                centerMedicine.setFullInfo(centerMedicineResp.toString());
                centerMedicine.setJYFL(jsCenMedicine.getString("chrgitm_lv"));
                centerMedicine.setSFXMMC(jsCenMedicine.getString("hilist_name"));
                centerMedicine.setKSRQ(jsCenMedicine.getDate("begndate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                centerMedicine.setJSRQ(jsCenMedicine.getDate("enddate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                centerMedicine.setPYM(jsCenMedicine.getString("pinyin"));
                ebeanServer.save(centerMedicine);
            }
        }
    }

    @Transactional
    public void validateSettlementSummary(SettlementValidationOverall settlementValidationOverall) {
        List<SettlementHY> settlementHYList =
                ebeanServer.find(SettlementHY.class).where()
                        .between("setl_time", settlementValidationOverall.getStmt_begndate(), settlementValidationOverall.getStmt_enddate())
                        .findList();


        BigDecimal totalSettlementAmount = settlementHYList.stream()
                .map(SettlementHY::getMedfee_sumamt)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFundPayAmount = settlementHYList.stream()
                .map(SettlementHY::getFund_pay_sumamt)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAccountPayAmount = settlementHYList.stream()
                .map(SettlementHY::getAcct_pay)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        settlementValidationOverall.setClr_type("21");
        settlementValidationOverall.setMedfee_sumamt(totalSettlementAmount);
        settlementValidationOverall.setFund_pay_sumamt(totalFundPayAmount);
        settlementValidationOverall.setAcct_pay(totalAccountPayAmount);
        settlementValidationOverall.setFixmedins_setl_cnt(settlementHYList.size());

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("3201");
        wrapper.setInputNodeName("data");
        wrapper.setInput(settlementValidationOverall);

        String responseString = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        JSONObject jsResult = JSONObject.parseObject(responseString).getJSONObject("stmtinfo");
        if (!jsResult.getString("stmt_rslt").equals("0"))
            throw new ApiValidationException("对账失败：" + jsResult.getString("stmt_rslt_dscr"));

    }

    public void validateSettlementSummaryDetail(SettlementValidationDetail settlementValidationDetail) {
        String fileQueryNumber = this.uploadFile("C:/settlement_list1.txt", "settlement_list1");

        List<SettlementHY> settlementHYList =
                ebeanServer.find(SettlementHY.class).where()
                        .between("setl_time", settlementValidationDetail.getStmt_begndate(), settlementValidationDetail.getStmt_enddate())
                        .findList();

        BigDecimal totalSettlementAmount = settlementHYList.stream()
                .map(SettlementHY::getMedfee_sumamt)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFundPayAmount = settlementHYList.stream()
                .map(SettlementHY::getFund_pay_sumamt)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAccountPayAmount = settlementHYList.stream()
                .map(SettlementHY::getAcct_pay)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalCashPayAmount = settlementHYList.stream()
                .map(SettlementHY::getPsn_cash_pay)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInputNodeName("data");
        wrapper.setInfoNumber("3202");
        settlementValidationDetail.setClr_type("21");
        settlementValidationDetail.setMedfee_sumamt(totalSettlementAmount);
        settlementValidationDetail.setFund_pay_sumamt(totalFundPayAmount);
        settlementValidationDetail.setAcct_pay(totalAccountPayAmount);
        settlementValidationDetail.setCash_payamt(totalCashPayAmount);
        settlementValidationDetail.setFixmedins_setl_cnt(settlementHYList.size());
        settlementValidationDetail.setFile_qury_no(fileQueryNumber);
        wrapper.setInput(settlementValidationDetail);
        String responseString = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);

    }

    private String uploadFile(String fileLocation, String fileName) {
        Path path = Paths.get(fileLocation);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new ApiValidationException("非法文件路径" + fileLocation);
        }

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("9101");
        wrapper.setInputNodeName("fsUploadIn");
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFilename(fileName);
        fileUpload.setIn(data);
        fileUpload.setFixmedins_code(this.hospitalCode);
        wrapper.setInput(fileUpload);
        String responseString = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(responseString).getString("file_qury_no");
    }

}
