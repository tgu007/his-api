package lukelin.his.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.backup.ChineseMedicine;
import lukelin.his.domain.entity.account.Fee;
import lukelin.his.domain.entity.account.ViewFeeSummary;
import lukelin.his.domain.entity.backup.WestMedicine;
import lukelin.his.domain.entity.backup.YBDisease;
import lukelin.his.domain.entity.backup.YBItem;
import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.patient_sign_in.PatientContact;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignInView;
import lukelin.his.domain.entity.yb.*;
import lukelin.his.domain.entity.yb.hy.PreSettlementHY;
import lukelin.his.domain.entity.yb.hy.SettlementHY;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.dto.yb_hy.Req.*;
import lukelin.his.dto.yb_hy.PramWrapper;
import lukelin.his.dto.yb_hy.Resp.*;
import lukelin.his.system.ConfigBeanProp;
import lukelin.his.system.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class YBServiceHY extends BaseHisService {

    @Value("${ybInterface.hybaseUrl}")
    protected String interfaceBaseUrl;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    private ConfigBeanProp configBeanProp;
    private boolean allFeeUploading = false;


    @Transactional
    public void yBSignIn(PatientSignIn patientSignIn) {
        if (patientSignIn.getYbSignIn() != null)
            throw new ApiValidationException("病人已医保登记入院");


        CardInfo cardInfo = this.checkPatientInsurance(patientSignIn);
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
        checkReq.setMed_type(patientSignIn.getMedType().getExtraInfo()); //普通住院
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

    public JSONObject getPatientInfo(PatientInfoReqPram infoReqPram) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("1101");
        wrapper.setInputNodeName("data");

        PatientInfoReq patientInfoReq = new PatientInfoReq();
        patientInfoReq.setCertno(infoReqPram.getIdNumber());
        patientInfoReq.setMdtrt_cert_no(infoReqPram.getIdNumber());
        patientInfoReq.setPsn_name(infoReqPram.getName());
        wrapper.setInput(patientInfoReq);

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        JSONObject jsonObject = JSONObject.parseObject(response);

        for (Object object : jsonObject.getJSONArray("insuinfo")) {
            JSONObject insuInfo = (JSONObject)object;
            String areaCode = insuInfo.getString("insuplc_admdvs");
            Optional<Dictionary> areaOptional = ebeanServer.find(Dictionary.class).where()
                    .eq("group.code", "医保地区")
                    .eq("code", areaCode).findOneOrEmpty();
            String areaName = "未知";
            if(areaOptional.isPresent())
                areaName = areaOptional.get().getExtraInfo();
            insuInfo.put("areaName",areaName);
        }
        return jsonObject;
    }

    private CardInfo checkPatientInsurance(PatientSignIn patientSignIn) {
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
        //SettlementHY settlementHY = patientSignIn.getSettlementHY();
        PreSettlementHY preSettlementHY = patientSignIn.getPreSettlementHY();

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2404");
        wrapper.setInputNodeName("data");
        wrapper.setAreaCode(cardInfo.getAreaCode());
        SignInCancelReq cancelReq = new SignInCancelReq();
        cancelReq.setMdtrt_id(ybSignIn.getId());
        cancelReq.setPsn_no(cardInfo.getPatientNumber());
        wrapper.setInput(cancelReq);


        if (preSettlementHY != null)
            ebeanServer.delete(preSettlementHY);

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
        Dictionary selfPayType = ebeanServer.find(Dictionary.class)
                .where()
                .eq("group.code", configBeanProp.getInsuranceType())
                .eq("extraInfo", "selfPay")
                .findOne();
        patientSignIn.setInsuranceType(selfPayType);
        //patientSignIn.setStatus(PatientSignInStatus.pendingSignIn);
        ebeanServer.save(patientSignIn);

        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);
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


    public void matchAllMedicine() {
        List<Medicine> medicineList =
                ebeanServer.find(Medicine.class).where()
                        .eq("selfPay", false)
                        .eq("enabled", true)
                        .eq("matchedMedicine", null)
                        .ne("centerMedicine", null)
                        .findList();
        for (Medicine medicine : medicineList)
            this.matchMedicine(medicine.getUuid());
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

    public void matchAllItems() {
        List<Item> itemList =
                ebeanServer.find(Item.class).where()
                        .eq("selfPay", false)
                        .eq("enabled", true)
                        .eq("matchedItem", null)
                        .ne("centerTreatment", null)
                        .findList();
        for (Item item : itemList)
            this.matchItem(item.getUuid());
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

    public void matchAllTreatment() {
        List<Treatment> treatmentList =
                ebeanServer.find(Treatment.class).where()
                        .eq("enabled", true)
                        .eq("matchedTreatment", null)
                        .ne("centerTreatment", null)
                        .findList();
        for (Treatment treatment : treatmentList) {
            if (treatment.getCenterTreatment().getSFXMMC().equals("自费项目"))
                continue;
            this.matchTreatment(treatment.getUuid());
        }

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
        LocalDate startDate = treatment.getCenterTreatment().getSTARTDATE();
        if (startDate == null)
            startDate = LocalDate.of(2000, 1, 1);

        LocalDate endDate = treatment.getCenterTreatment().getENDDATE();
        if (endDate == null)
            endDate = LocalDate.of(2099, 12, 31);
        matchRequest.setBegndate(startDate.format(pattern));
        matchRequest.setEndDate(endDate.format(pattern));
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
            if (!fee.entityMatched())
                continue;
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

    @Transactional
    public void uploadPatientPendingSingleFee(UUID feeId) {
        Fee fee = ebeanServer.find(Fee.class, feeId);
        List<FeeUploadReqHY> uploadReqHYList = new ArrayList<>();
        uploadReqHYList.add(fee.toHYUploadDto());
        this.uploadFee(uploadReqHYList);
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
                //.eq("uuid", UUID.fromString("5eac1792-50d5-469d-a4ba-19f9b4089145"))
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

        //下载累计信息
        JSONArray accumulatedList = this.downloadAccumulatedInfo(patientSignIn.getSignInNumber());
        String stringAccumulatedList = accumulatedList.toJSONString();

        PramWrapper wrapper = this.getSettlePram(patientSignIn, "2304");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        JSONObject settleInfo = JSONObject.parseObject(response).getJSONObject("setlinfo");
        SettlementResp settlementResp = JSONObject.toJavaObject(settleInfo, SettlementResp.class);

        SettlementHY settlementHY = settlementResp.toSettlement();
        settlementHY.setFullInfo(response);
        settlementHY.setAccumulated_info(stringAccumulatedList);
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

        if (patientContact.getAddress() == null || patientContact.getAddress().equals(""))
            setlInfo.setConer_addr("未知");
        else
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

    public void cancelYBSignOut(PatientSignIn patientSignIn) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2405");
        wrapper.setInputNodeName("data");
        SignOutReqCancel req = new SignOutReqCancel();
        req.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        req.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        wrapper.setInput(req);
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
    public SettlementValidationOverallResp validateSettlementSummary(SettlementValidationOverall settlementValidationOverall) {
        List<SettlementHY> settlementHYList =
                ebeanServer.find(SettlementHY.class).where()
                        .between("setl_time", settlementValidationOverall.getStmt_begndate(), settlementValidationOverall.getStmt_enddate())
                        .eq("patientSignIn.insuranceType.id", settlementValidationOverall.getInsutypeId())
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

        SettlementValidationOverallResp resp = new SettlementValidationOverallResp();
        if (!jsResult.getString("stmt_rslt").equals("0"))
            resp.setErrorMessage("对账失败：" + jsResult.getString("stmt_rslt_dscr"));

        for (SettlementHY settlementHY : settlementHYList)
            resp.getSettledPatientList().add(settlementHY.getPatientSignIn().toDto());
        return resp;

    }

    public void validateSettlementSummaryDetail(SettlementValidationDetail settlementValidationDetail) {
        String fileQueryNumber = this.uploadFile();

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
        this.downloadSettlementResult(responseString);

    }

    private void downloadSettlementResult(String responseString) {
        JSONObject responseObj = JSONObject.parseObject(responseString).getJSONObject("fileinfo");
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("9102");
        wrapper.setOperator("default");
        wrapper.setInputNodeName("fsDownloadIn");
        wrapper.setDownloadFileName("settlement_validate_result.zip");
        String fileNameToDownload = responseObj.getString("filename");
        String fileQueryNo = responseObj.getString("file_qury_no");


        FileDownload fileDownload = new FileDownload();
        fileDownload.setFile_qury_no(fileQueryNo);
        fileDownload.setFileName(fileNameToDownload);
        fileDownload.setFixmedins_code(this.hospitalCode);
        wrapper.setInput(fileDownload);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);

    }

    private String uploadFile() {

        String inString = "";

//        try {
//            byte[] data = Files.readAllBytes(Paths.get(filePath));
//            inString = Arrays.toString(data);
//        } catch (IOException e) {
//            throw new ApiValidationException("非法文件路径" + filePath);
//        }

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("9101");
        wrapper.setInputNodeName("fsUploadIn");
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFilename("");
        fileUpload.setIn(inString);
        fileUpload.setFixmedins_code(this.hospitalCode);
        wrapper.setInput(fileUpload);
        String responseString = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(responseString).getString("file_qury_no");
    }

    public Object downloadFile(FileDownloadPram fileDownloadPram) {
//        PramWrapper wrapper = new PramWrapper();
//        wrapper.setInfoNumber(fileDownloadPram.getInfoNumber());
//        wrapper.setInputNodeName("data");
//        FileInfoDownload fileInfoDownload = new FileInfoDownload();
//        fileInfoDownload.setVer("0");
//        wrapper.setOperator("default");
//        wrapper.setInput(fileInfoDownload);
//        String responseString = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
//
//        String fileNameToDownload = JSONObject.parseObject(responseString).getString("filename");
//        String fileQueryNo = JSONObject.parseObject(responseString).getString("file_qury_no");
//
//        wrapper = new PramWrapper();
//        wrapper.setInfoNumber("9102");
//        wrapper.setOperator("default");
//        wrapper.setInputNodeName("fsDownloadIn");
//        wrapper.setDownloadFileName(fileDownloadPram.getFileName());
//
//        FileDownload fileDownload = new FileDownload();
//        fileDownload.setFile_qury_no(fileQueryNo);
//        fileDownload.setFileName(fileNameToDownload);
//        fileDownload.setFixmedins_code(this.hospitalCode);
//        wrapper.setInput(fileDownload);
        //responseString = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);

        Object result = new ArrayList<>();
        if (fileDownloadPram.getInfoNumber().equals("1302"))
            result = ebeanServer.find(ChineseMedicine.class).findList();
        else if (fileDownloadPram.getInfoNumber().equals("1301"))
            result = ebeanServer.find(WestMedicine.class).findList();
        else if (fileDownloadPram.getInfoNumber().equals("1306"))
            result = ebeanServer.find(YBItem.class).findList();
        else if (fileDownloadPram.getInfoNumber().equals("1307"))
            result = ebeanServer.find(YBDisease.class).findList();
        return result;
    }

//    private void MockLoad(FileDownloadPram fileDownloadPram) {
//        List<ChineseMedicine> list = ebeanServer.find(ChineseMedicine.class).findList();
//    }


    @Transactional
    public JSONObject downloadSettlement(Integer signInNumber) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class).where().eq("signInNumber", signInNumber).findOne();
        SettlementDownload settlementDownload = new SettlementDownload();
        settlementDownload.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        settlementDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        settlementDownload.setSetl_id(patientSignIn.getSettlementHY().getSetl_id());

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5203");
        wrapper.setOperator("default");
        wrapper.setInputNodeName("data");
        wrapper.setInput(settlementDownload);
        String infoDownloaded = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        patientSignIn.getSettlementHY().setFull_info_download(infoDownloaded);
        ebeanServer.save(patientSignIn.getSettlementHY());
        JSONObject ret = JSONObject.parseObject(infoDownloaded).getJSONObject("setlinfo");
        return ret;
    }

    public JSONArray downloadDepartment() {

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5101");
        wrapper.setOperator("default");
        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("feedetail");
    }

    public JSONArray downloadSigninInfo(Integer signInNumber) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class).where().eq("signInNumber", signInNumber).findOne();
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5201");
        wrapper.setOperator("default");


        SignInDownload signInDownload = new SignInDownload();
        signInDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        signInDownload.setMed_type(patientSignIn.getMedType().getExtraInfo());
        signInDownload.setBegntime("2021-01-01");
        signInDownload.setEndtime("2022-12-31");
        wrapper.setInput(signInDownload);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("mdtrtinfo");
    }

    public JSONArray downloadDiagnose(Integer signInNumber) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class).where().eq("signInNumber", signInNumber).findOne();
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5202");
        wrapper.setOperator("default");


        DiagnoseDownload signInDownload = new DiagnoseDownload();
        signInDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        signInDownload.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        wrapper.setInput(signInDownload);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("diseinfo");
    }

    public JSONArray downloadFee(Integer signInNumber) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class).where().eq("signInNumber", signInNumber).findOne();
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5204");
        wrapper.setOperator("default");


        FeeHyDownload feeHyDownload = new FeeHyDownload();
        feeHyDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        feeHyDownload.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        feeHyDownload.setSetl_id(patientSignIn.getSettlementHY().getSetl_id());
        wrapper.setInput(feeHyDownload);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseArray(response);
    }

    public JSONArray downloadAccumulatedInfo(Integer signInNumber) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class).where().eq("signInNumber", signInNumber).findOne();
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5206");
        wrapper.setOperator("default");

        AccumulatedFeeInfoDownload accumulatedFeeInfoDownload = new AccumulatedFeeInfoDownload();
        accumulatedFeeInfoDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
//        LocalDate currentdate = LocalDate.now();
//        accumulatedFeeInfoDownload.setCum_ym("2020"+ "1");
        wrapper.setInput(accumulatedFeeInfoDownload);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("cuminfo");
    }

    public JSONArray searchSignInByTime(SignInSearchByTime pram) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("5303");
        wrapper.setOperator("default");
        wrapper.setInput(pram);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("data");
    }

    public JSONArray searchCommon(Integer pageNumber, String infoNumber) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber(infoNumber);
        wrapper.setOperator("default");
        SearchCommonPram pram = new SearchCommonPram();
        pram.setPage_num(pageNumber);
        pram.setPage_size(50);
        pram.setUpdt_time("2021-01-01");
        wrapper.setInput(pram);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("data");
    }

    @Transactional
    public void cancelSettlement(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        SettlementHY settlement = patientSignIn.getSettlementHY();
        if (settlement == null)
            throw new ApiValidationException("没有结算信息！");

        patientSignIn.setStatus(PatientSignInStatus.pendingSignOut);
        ebeanServer.save(patientSignIn);
        SettlementCancelReq cancelReq = new SettlementCancelReq();
        cancelReq.setSetl_id(settlement.getSetl_id());
        cancelReq.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        cancelReq.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        ebeanServer.delete(settlement);

        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("2305");
        wrapper.setInputNodeName("data");


        wrapper.setInput(cancelReq);
        this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, Void.class);
    }

    public SettlementSummaryPrint getSettlementSummaryPrintInfo(UUID patientSignInId) {
        SettlementSummaryPrint summaryToPrint = new SettlementSummaryPrint();
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        String fullIdentityInfo = patientSignIn.getCardInfo().getFullInfo();
        JSONArray idetinfo = JSONObject.parseObject(fullIdentityInfo).getJSONArray("idetinfo");
        if (idetinfo != null && idetinfo.size() > 0)
            summaryToPrint.setIdentityType(this.getIdentityTypeDescription(idetinfo.getJSONObject(0).getString("psn_idet_type")));

        JSONObject jsBasicInfo = this.downloadSettlement(patientSignIn.getSignInNumber());
        SettlementBasicInfo basicInfo = jsBasicInfo.toJavaObject(SettlementBasicInfo.class);
        summaryToPrint.setBasicInfo(basicInfo);
        //SettlementBasicInfo basicInfo

        summaryToPrint.setAccumulatedInfo(this.getAccumulatedInfo(patientSignIn));
        summaryToPrint.setFeeSummaryInfo(this.getFeeSummaryInfo(patientSignIn));
        summaryToPrint.setDetailInfo(this.getSettlementDetailInfo(patientSignIn));

        Map<String, String> insuInfo = this.getinsuInfo(patientSignIn);

        if (insuInfo != null) {
            String areaCode = insuInfo.get("insuplc_admdvs");
            Optional<Dictionary> areaOptional = ebeanServer.find(Dictionary.class).where()
                    .eq("group.code", "医保地区")
                    .eq("code", areaCode).findOneOrEmpty();
            String areaName = "未知";
            if(areaOptional.isPresent())
                areaName = areaOptional.get().getName();
            summaryToPrint.setYbAreaCode(areaName);
        }
        return summaryToPrint;
    }


    public JSONArray downloadSettlementDetailInfo(Integer signInNumber) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class).where().eq("signInNumber", signInNumber).findOne();
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("100001");
        wrapper.setOperator("default");
        FeeHyDownload feeHyDownload = new FeeHyDownload();
        feeHyDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        feeHyDownload.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        feeHyDownload.setSetl_id(patientSignIn.getSettlementHY().getSetl_id());
        wrapper.setInput(feeHyDownload);
        wrapper.setInputNodeName("data");
        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);
        return JSONObject.parseObject(response).getJSONArray("data");
    }

    private SettlementDetailInfo getSettlementDetailInfo(PatientSignIn patientSignIn) {
        PramWrapper wrapper = new PramWrapper();
        wrapper.setInfoNumber("100001");
        wrapper.setOperator("default");
        FeeHyDownload feeHyDownload = new FeeHyDownload();
        feeHyDownload.setPsn_no(patientSignIn.getCardInfo().getPatientNumber());
        feeHyDownload.setMdtrt_id(patientSignIn.getYbSignIn().getId());
        feeHyDownload.setSetl_id(patientSignIn.getSettlementHY().getSetl_id());
        wrapper.setInput(feeHyDownload);
        wrapper.setInputNodeName("data");

        String response = this.restTemplate.postForObject(this.interfaceBaseUrl, wrapper, String.class);

        SettlementDetailInfo detailInfo = new SettlementDetailInfo();
        detailInfo.setSettlementDetailList(JSONObject.parseObject(response).getJSONArray("data"));
        //JSONObject.parseObject(response).getJSONArray("data");
//        for (Object obj : JSONObject.parseObject(response).getJSONArray("data")) {
//            JSONObject record = (JSONObject) obj;
//            String code = record.getString("polItemCode");
//            BigDecimal selfPaymentAmount = new BigDecimal(record.getString("selfPayAmt"));
//            String selfPaymentRatio = record.getString("selfPayProp");
//            BigDecimal fundPaymentAmount = new BigDecimal(record.getString("fundPayAmt"));
//            String fundPaymentRatio = record.getString("fundPayProp");
//            BigDecimal total = new BigDecimal(record.getString("polItemPaySum"));
//
//            switch (code) {
//                case "101":
//                    detailInfo.setSelfPayAmount1(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio1(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount1(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio1(fundPaymentRatio);
//                    detailInfo.setLineTotal1(total);
//                    break;
//                case "102":
//                    detailInfo.setSelfPayAmount2(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio2(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount2(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio2(fundPaymentRatio);
//                    detailInfo.setLineTotal2(total);
//                    break;
//                case "103":
//                    detailInfo.setSelfPayAmount3(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio3(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount3(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio3(fundPaymentRatio);
//                    detailInfo.setLineTotal3(total);
//                    break;
//                case "S001":
//                    detailInfo.setSelfPayAmount4(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio4(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount4(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio4(fundPaymentRatio);
//                    detailInfo.setLineTotal4(total);
//                    break;
//                case "C001":
//                    detailInfo.setSelfPayAmount5(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio5(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount5(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio5(fundPaymentRatio);
//                    detailInfo.setLineTotal5(total);
//                    break;
//                case "BS01":
//                    detailInfo.setSelfPayAmount6(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio6(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount6(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio6(fundPaymentRatio);
//                    detailInfo.setLineTotal6(total);
//                    break;
//                case "PS01":
//                    detailInfo.setSelfPayAmount7(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio7(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount7(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio7(fundPaymentRatio);
//                    detailInfo.setLineTotal7(total);
//                case "YB01":
//                    detailInfo.setFundPaymentAmount8(selfPaymentAmount);
//                    detailInfo.setSelfPaymentRatio8(selfPaymentRatio);
//                    detailInfo.setFundPaymentAmount8(fundPaymentAmount);
//                    detailInfo.setFundPaymentRatio8(fundPaymentRatio);
//                    detailInfo.setLineTotal8(total);
//                    break;
//            }
//        }
        return detailInfo;

    }

    private SettlementFeeSummaryInfo getFeeSummaryInfo(PatientSignIn patientSignIn) {
        SettlementFeeSummaryInfo feeSummaryInfo = new SettlementFeeSummaryInfo();
        JSONArray feeList = this.downloadFee(patientSignIn.getSignInNumber());
        String level;
        String type;
        BigDecimal amount;
        BigDecimal fundAmount;
        for (Object obj : feeList) {
            JSONObject feeRecord = (JSONObject) obj;
            level = feeRecord.getString("chrgitm_lv");
            type = feeRecord.getString("med_chrgitm_type");
            amount = (BigDecimal) feeRecord.get("det_item_fee_sumamt");
            fundAmount = (BigDecimal) feeRecord.get("inscp_scp_amt");

            if (level.equals("01")) {
                if (type.equals("01"))
                    feeSummaryInfo.setFeeCWF1(feeSummaryInfo.getFeeCWF1().add(fundAmount));
                else if (type.equals("02"))
                    feeSummaryInfo.setFeeZCF1(feeSummaryInfo.getFeeZCF1().add(fundAmount));
                else if (type.equals("03"))
                    feeSummaryInfo.setFeeJCF1(feeSummaryInfo.getFeeJCF1().add(fundAmount));
                else if (type.equals("04"))
                    feeSummaryInfo.setFeeHYF1(feeSummaryInfo.getFeeHYF1().add(fundAmount));
                else if (type.equals("05"))
                    feeSummaryInfo.setFeeZLF1(feeSummaryInfo.getFeeZLF1().add(fundAmount));
                else if (type.equals("06"))
                    feeSummaryInfo.setFeeSSF1(feeSummaryInfo.getFeeSSF1().add(fundAmount));
                else if (type.equals("07"))
                    feeSummaryInfo.setFeeHLF1(feeSummaryInfo.getFeeHLF1().add(fundAmount));
                else if (type.equals("08"))
                    feeSummaryInfo.setFeeWSCLF1(feeSummaryInfo.getFeeWSCLF1().add(fundAmount));
                else if (type.equals("09"))
                    feeSummaryInfo.setFeeXY1(feeSummaryInfo.getFeeXY1().add(fundAmount));
                else if (type.equals("10"))
                    feeSummaryInfo.setFeeZYYPF1(feeSummaryInfo.getFeeZYYPF1().add(fundAmount));
                else if (type.equals("11"))
                    feeSummaryInfo.setFeeZhongChen1(feeSummaryInfo.getFeeZhongChen1().add(fundAmount));
                else if (type.equals("12"))
                    feeSummaryInfo.setFeeYBZLF1(feeSummaryInfo.getFeeYBZLF1().add(fundAmount));
                else if (type.equals("13"))
                    feeSummaryInfo.setFeeGHF1(feeSummaryInfo.getFeeGHF1().add(fundAmount));
                else if (type.equals("14"))
                    feeSummaryInfo.setFeeQTF1(feeSummaryInfo.getFeeQTF1().add(fundAmount));
                feeSummaryInfo.setFeeTotal1(feeSummaryInfo.getFeeTotal1().add(fundAmount));
            } else if (level.equals("02")) {
                if (type.equals("01"))
                    feeSummaryInfo.setFeeCWF2(feeSummaryInfo.getFeeCWF2().add(amount));
                else if (type.equals("02"))
                    feeSummaryInfo.setFeeZCF2(feeSummaryInfo.getFeeZCF2().add(amount));
                else if (type.equals("03"))
                    feeSummaryInfo.setFeeJCF2(feeSummaryInfo.getFeeJCF2().add(amount));
                else if (type.equals("04"))
                    feeSummaryInfo.setFeeHYF2(feeSummaryInfo.getFeeHYF2().add(amount));
                else if (type.equals("05"))
                    feeSummaryInfo.setFeeZLF2(feeSummaryInfo.getFeeZLF2().add(amount));
                else if (type.equals("06"))
                    feeSummaryInfo.setFeeSSF2(feeSummaryInfo.getFeeSSF2().add(amount));
                else if (type.equals("07"))
                    feeSummaryInfo.setFeeHLF2(feeSummaryInfo.getFeeHLF2().add(amount));
                else if (type.equals("08"))
                    feeSummaryInfo.setFeeWSCLF2(feeSummaryInfo.getFeeWSCLF2().add(amount));
                else if (type.equals("09"))
                    feeSummaryInfo.setFeeXY2(feeSummaryInfo.getFeeXY2().add(amount));
                else if (type.equals("10"))
                    feeSummaryInfo.setFeeZYYPF2(feeSummaryInfo.getFeeZYYPF2().add(amount));
                else if (type.equals("11"))
                    feeSummaryInfo.setFeeZhongCheng2(feeSummaryInfo.getFeeZhongCheng2().add(amount));
                else if (type.equals("12"))
                    feeSummaryInfo.setFeeYBZLF2(feeSummaryInfo.getFeeYBZLF2().add(amount));
                else if (type.equals("13"))
                    feeSummaryInfo.setFeeGHF2(feeSummaryInfo.getFeeGHF2().add(amount));
                else if (type.equals("14"))
                    feeSummaryInfo.setFeeQTF2(feeSummaryInfo.getFeeQTF2().add(amount));

                feeSummaryInfo.setFeeTotal2(feeSummaryInfo.getFeeTotal2().add(amount));
            }

            if (type.equals("01"))
                feeSummaryInfo.setFeeCWF0(feeSummaryInfo.getFeeCWF0().add(amount));
            else if (type.equals("02"))
                feeSummaryInfo.setFeeZCF0(feeSummaryInfo.getFeeZCF0().add(amount));
            else if (type.equals("03"))
                feeSummaryInfo.setFeeJCF0(feeSummaryInfo.getFeeJCF0().add(amount));
            else if (type.equals("04"))
                feeSummaryInfo.setFeeHYF0(feeSummaryInfo.getFeeHYF0().add(amount));
            else if (type.equals("05"))
                feeSummaryInfo.setFeeZLF0(feeSummaryInfo.getFeeZLF0().add(amount));
            else if (type.equals("06"))
                feeSummaryInfo.setFeeSSF0(feeSummaryInfo.getFeeSSF0().add(amount));
            else if (type.equals("07"))
                feeSummaryInfo.setFeeHLF0(feeSummaryInfo.getFeeHLF0().add(amount));
            else if (type.equals("08"))
                feeSummaryInfo.setFeeWSCLF0(feeSummaryInfo.getFeeWSCLF0().add(amount));
            else if (type.equals("09"))
                feeSummaryInfo.setFeeXY0(feeSummaryInfo.getFeeXY0().add(amount));
            else if (type.equals("10"))
                feeSummaryInfo.setFeeZYYPF0(feeSummaryInfo.getFeeZYYPF0().add(amount));
            else if (type.equals("11"))
                feeSummaryInfo.setFeeZhongCheng0(feeSummaryInfo.getFeeZhongCheng0().add(amount));
            else if (type.equals("12"))
                feeSummaryInfo.setFeeYBZLF0(feeSummaryInfo.getFeeYBZLF0().add(amount));
            else if (type.equals("13"))
                feeSummaryInfo.setFeeGHF0(feeSummaryInfo.getFeeGHF0().add(amount));
            else if (type.equals("14"))
                feeSummaryInfo.setFeeQTF0(feeSummaryInfo.getFeeQTF0().add(amount));

            feeSummaryInfo.setFeeTotal0(feeSummaryInfo.getFeeTotal0().add((BigDecimal) feeRecord.get("det_item_fee_sumamt")));
        }
        //feeSummaryInfo.calculateSum();

        return feeSummaryInfo;
    }


    private SettlementAccumulatedInfo getAccumulatedInfo(PatientSignIn patientSignIn) {
        SettlementAccumulatedInfo accumulatedInfo = new SettlementAccumulatedInfo();
        if (patientSignIn.getSettlementHY().getAccumulated_info() == null)
            throw new ApiValidationException("累计信息未下载，取消当前结算信息并重新结算");
        JSONArray accumulatedArrayList = JSONObject.parseArray(patientSignIn.getSettlementHY().getAccumulated_info());
        for (Object obj : accumulatedArrayList) {
            JSONObject historyRecord = (JSONObject) obj;
            String code = historyRecord.getString("cum_type_code");
            String medTypeCode = patientSignIn.getMedType().getExtraInfo();
            String insuranceTypeCode = patientSignIn.getInsuranceType().getExtraInfo();
            if (medTypeCode.equals("2101")) //普通住院
            {
                if (code.equals("C0000_BIZ2101"))
                    accumulatedInfo.setCurrentYearCount((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("S0000_BIZ2101"))
                    accumulatedInfo.setFdlj((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("F0000_BIZ2101"))
                    accumulatedInfo.setTotalFee((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("Q0000_BIZ2101"))
                    accumulatedInfo.setPaidStartLine((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D310101_BIZ2101") && insuranceTypeCode.equals("310"))
                    accumulatedInfo.setFundTongChou((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D390101_BIZ2101") && insuranceTypeCode.equals("390"))
                    accumulatedInfo.setFundTongChou((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D330101_BIZ2101") && insuranceTypeCode.equals("310"))
                    accumulatedInfo.setFundDEJJ((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D390201_BIZ2101") && insuranceTypeCode.equals("390"))
                    accumulatedInfo.setFundDBBXZF((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D610101_BIZ2101"))
                    accumulatedInfo.setFundYLJZ((BigDecimal) historyRecord.get("cum"));

            } else if (medTypeCode.equals("22")) {
                if (code.equals("C0000_BIZ22"))
                    accumulatedInfo.setCurrentYearCount((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("S0000_BIZ22"))
                    accumulatedInfo.setFdlj((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("F0000_BIZ22"))
                    accumulatedInfo.setTotalFee((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("Q0000_BIZ22"))
                    accumulatedInfo.setPaidStartLine((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D310101_BIZ22") && insuranceTypeCode.equals("310")) //职工
                    accumulatedInfo.setFundTongChou((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D390101_BIZ22") && insuranceTypeCode.equals("390")) //城乡
                    accumulatedInfo.setFundTongChou((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D390201_BIZ22") && insuranceTypeCode.equals("390"))
                    accumulatedInfo.setFundDBBXZF((BigDecimal) historyRecord.get("cum"));
                else if (code.equals("D610101_BIZ22"))
                    accumulatedInfo.setFundYLJZ((BigDecimal) historyRecord.get("cum"));
            }

            if (code.equals("TS390201"))
                accumulatedInfo.setFundDBBXHG((BigDecimal) historyRecord.get("cum"));

            //if (code.equals("D330101"))
            //  accumulatedInfo.setFundDEJJ(accumulatedInfo.getFundDEJJ().add((BigDecimal) historyRecord.get("cum")));

//            else if (code.equals("D390201"))
//                accumulatedInfo.setFundDBBXZF(accumulatedInfo.getFundDBBXZF().add((BigDecimal) historyRecord.get("cum")));
//            else if (code.equals("D610101"))
//                accumulatedInfo.setFundYLJZ(accumulatedInfo.getFundYLJZ().add((BigDecimal) historyRecord.get("cum")));

            //if (code.equals("C0000_BIZ2101") || code.equals("C0000_BIZ2102") || code.equals("C0000_BIZ22") || code.equals("C0000_BIZ2106") || code.equals("C0000_BIZ2107") || code.equals("C0000_BIZ52"))
            //accumulatedInfo.setCurrentYearCount(accumulatedInfo.getCurrentYearCount().add((BigDecimal) historyRecord.get("cum")));
            //else if (code.equals("S0000_BIZ2101") || code.equals("S0000_BIZ2102") || code.equals("S0000_BIZ22") || code.equals("S0000_BIZ2106") || code.equals("S0000_BIZ2107") || code.equals("S0000_BIZ52"))
            //accumulatedInfo.setFdlj(accumulatedInfo.getFdlj().add((BigDecimal) historyRecord.get("cum")));
            //else if (code.equals("F0000_BIZ2101") || code.equals("F0000_BIZ2102") || code.equals("F0000_BIZ22") || code.equals("F0000_BIZ2106") || code.equals("F0000_BIZ2107") || code.equals("F0000_BIZ52"))
            // accumulatedInfo.setTotalFee(accumulatedInfo.getTotalFee().add((BigDecimal) historyRecord.get("cum")));
            //else if (code.equals("Q0000_BIZ2101") || code.equals("Q0000_BIZ2102") || code.equals("Q0000_BIZ22") || code.equals("Q0000_BIZ2106") || code.equals("Q0000_BIZ2107") || code.equals("Q0000_BIZ52"))
            //accumulatedInfo.setPaidStartLine(accumulatedInfo.getPaidStartLine().add((BigDecimal) historyRecord.get("cum")));
            //  else if (code.equals("D310101_BIZ2101") || code.equals("D310101_BIZ2102") || code.equals("D310101_BIZ22") || code.equals("D310101_BIZ2107") || code.equals("D310101_BIZ52") || code.equals("D390101_BIZ2101")
            //      || code.equals("D310101_BIZ2106") || code.equals("D390101_BIZ2102") || code.equals("D390101_BIZ22") || code.equals("D390101_BIZ2106") || code.equals("D390101_BIZ2107") || code.equals("D390101_BIZ52"))
            //   accumulatedInfo.setFundTongChou(accumulatedInfo.getFundTongChou().add((BigDecimal) historyRecord.get("cum")));
//            else if (code.equals("Z0000_BIZ2101_LAB101") || code.equals("Z0000_BIZ2102_LAB101") || code.equals("Z0000_BIZ22_LAB101") || code.equals("Z0000_BIZ2106_LAB101") || code.equals("Z0000_BIZ2107_LAB101") || code.equals("Z0000_BIZ52_LAB101")
//                    || code.equals("Z0000_BIZ2101_LAB103") || code.equals("Z0000_BIZ2102_LAB103") || code.equals("Z0000_BIZ22_LAB103") || code.equals("Z0000_BIZ2106_LAB103") || code.equals("Z0000_BIZ2107_LAB103") || code.equals("Z0000_BIZ52_LAB103"))
//                accumulatedInfo.setZcZifei(accumulatedInfo.getZcZifei().add((BigDecimal) historyRecord.get("cum")));
//            else if (code.equals("Z0000_BIZ2101_LAB102") || code.equals("Z0000_BIZ2102_LAB102") || code.equals("Z0000_BIZ22_LAB102") || code.equals("Z0000_BIZ2106_LAB102") || code.equals("Z0000_BIZ2107_LAB102") || code.equals("Z0000_BIZ52_LAB102"))
//                accumulatedInfo.setZcZifu(accumulatedInfo.getZcZifu().add((BigDecimal) historyRecord.get("cum")));

        }
        return accumulatedInfo;
    }

    private String getIdentityTypeDescription(String identityCode) {
        //Todo reafactor later into db.
        if (identityCode.equals("21"))
            return "优抚人员";
        else if (identityCode.equals("2306"))
            return "因病致贫救助对象（含重特大疾病人员）";
        else if (identityCode.equals("2101"))
            return " 因公牺牲军人家属";
        else if (identityCode.equals("2307"))
            return "农村五保户";
        else if (identityCode.equals("2102"))
            return "退出现役的残疾军人";
        else if (identityCode.equals("2308"))
            return "残疾人员";
        else if (identityCode.equals("2103"))
            return "二等乙级伤残军人";
        else if (identityCode.equals("2360"))
            return "地方扩展医疗救助对象人员";
        else if (identityCode.equals("2104"))
            return "一至六级残疾军人";
        else if (identityCode.equals("24"))
            return "军人";
        else if (identityCode.equals("2105"))
            return "老红军";
        else if (identityCode.equals("2401"))
            return "现役军人家属";
        else if (identityCode.equals("2160"))
            return "地方扩展优抚人员";
        else if (identityCode.equals("2460"))
            return "地方扩展军人人员，其他军转人员";
        else if (identityCode.equals("22"))
            return "医疗照顾人员";
        else if (identityCode.equals("31"))
            return "计划生育户";
        else if (identityCode.equals("2201"))
            return "副省级以上在职和退休领导";
        else if (identityCode.equals("32"))
            return "双女结扎户";
        else if (identityCode.equals("2211"))
            return "两院院士";
        else if (identityCode.equals("33"))
            return "三结合户";
        else if (identityCode.equals("2212"))
            return "省直直管优秀专家";
        else if (identityCode.equals("34"))
            return "独女户";
        else if (identityCode.equals("2231"))
            return "国家级劳模";
        else if (identityCode.equals("35"))
            return "放弃生育指标户";
        else if (identityCode.equals("2232"))
            return "享受公务员的省级劳模";
        else if (identityCode.equals("36"))
            return "独生子女户";
        else if (identityCode.equals("2241"))
            return "亚洲以上冠军运动员";
        else if (identityCode.equals("60"))
            return "地方扩展其他特殊待遇人员";
        else if (identityCode.equals("2260"))
            return "地方扩展医疗照顾人员";
        else if (identityCode.equals("6001"))
            return "文革基残保健对象";
        else if (identityCode.equals("23"))
            return "医疗救助人员";
        else if (identityCode.equals("6002"))
            return "文革全残";
        else if (identityCode.equals("2301"))
            return "三无人员";
        else if (identityCode.equals("6003"))
            return "建国前老工人";
        else if (identityCode.equals("2302"))
            return "建档立卡贫困人员";
        else if (identityCode.equals("6004"))
            return "计划生育户";
        else if (identityCode.equals("2303"))
            return "低保救助对象";
        else if (identityCode.equals("6005"))
            return "实行退牧的牧民";
        else if (identityCode.equals("2304"))
            return "特困救助对象";
        else if (identityCode.equals("6006"))
            return "退地农业劳动力";
        else if (identityCode.equals("2305"))
            return "低收入救助对象";
        else if (identityCode.equals("6007"))
            return "城中村";
        else if (identityCode.equals("6008"))
            return "失独父母";
        else
            return "未知";
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
                if (patientSignIn.selfPay())
                    continue;

//                if(patientSignIn.getCurrentBed() == null)
//                    continue;
//                if (!patientSignIn.getCurrentBed().getWardRoom().getWard().getName().equals("一病区"))
//                    continue;

                if (patientSignIn.getYbSignIn() == null)
                    continue;
                try {
                    this.uploadPatientPendingFee(patientSignIn.getUuid());
                    this.preSettle(patientSignIn.getUuid());
                } catch (Exception ex) {
                    //Todo log exception;
                    String test = ex.getMessage();
                }
            }
        } finally {
            this.allFeeUploading = false;
        }

    }

    public List<PatientSignIn> getPatientSignInList(List<UUID> patientSignInIdList) {
        return ebeanServer.find(PatientSignIn.class)
                .where()
                .in("uuid", patientSignInIdList)
                .findList();
    }
}











