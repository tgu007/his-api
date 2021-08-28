package lukelin.his.api;

import io.ebean.Ebean;
import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.DictionaryGroup;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.basic.ward.WardRoom;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.patient_sign_in.*;
import lukelin.his.dto.basic.req.filter.MedicalRecordTypeFilter;
import lukelin.his.dto.basic.req.filter.WardFilterDto;
import lukelin.his.dto.basic.resp.setup.DictionaryGroupDto;
import lukelin.his.dto.basic.resp.template.MedicalRecordTemplateDto;
import lukelin.his.dto.basic.resp.ward.WardDto;
import lukelin.his.dto.conveter.PatientSignInDtoConverter;
import lukelin.his.dto.prescription.request.filter.PatientSignInFilterDto;
import lukelin.his.dto.signin.response.*;
import lukelin.his.dto.signin.request.*;
import lukelin.his.dto.yb.ClientIpInfo;
import lukelin.his.service.BasicService;
import lukelin.his.service.NotificationService;
import lukelin.his.service.PatientSignInService;
import lukelin.his.system.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/patient")
public class PatientSignInApi extends BaseController {
    @Autowired
    private PatientSignInService patientSignInService;

    @Autowired
    private BasicService basicService;

    @Autowired
    private NotificationService notificationService;

    @Value("${qrCode.baseUrl}")
    private String qrCodeBaseUrl;

    @PostMapping("save")
    public UUID savePatient(@RequestBody PatientDto patientDto) {
        return patientSignInService.savePatient(patientDto);
    }

    @PostMapping("all/{pageNum}")
    public PagedDTO<PatientDto> getPatientList(@PathVariable int pageNum, @RequestBody PatientSearchDto patientSearchDto) {
        PagedList<Patient> list = patientSignInService.getPatientList(pageNum, patientSearchDto);
        return pagedResponse(DtoUtils.toDtoList(list.getList()), list);
    }

    @GetMapping("patient_detail_prams")
    public DecoratedDTO<List<DictionaryGroupDto>> getPatientDetailPrams() {
        List<DictionaryGroup> dictionaryGroupList = patientSignInService.getPatientDetailPrams();
        return decoratedResponse(DtoUtils.toDtoList(dictionaryGroupList));
    }

    @GetMapping("sign_in/prams")
    public DecoratedDTO<PatientSignInPramsDto> getPatientSignInPrams() {
        return decoratedResponse(patientSignInService.getPatientSignInPrams());
    }

    @GetMapping("sign_out/prams")
    public DecoratedDTO<PatientSignOutPramsDto> getPatientSignOutPrams() {
        return decoratedResponse(patientSignInService.getPatientSignOutPrams());
    }

    @PostMapping("sign_in/save")
    public DecoratedDTO<PatientSignInRespDto> savePatientSignIn(@RequestBody PatientSignInSaveReqDto patientSignInDto) {
        PatientSignIn patientSignIn = this.patientSignInService.savePatientSignIn(patientSignInDto);
        if (patientSignInDto.getUuid() == null)
            this.notificationService.patientSignInRequest(patientSignIn);
        return decoratedResponse(patientSignIn.toDto());

    }

    @PostMapping("sign_in/confirm/{signInId}")
    public void confirmSignIn(@PathVariable UUID signInId, @RequestBody ClientIpInfo clientIpInfo) {
        PatientSignIn patientSignIn = this.patientSignInService.confirmSignIn(signInId, clientIpInfo);
        this.notificationService.patientSignInRequestChanged(patientSignIn);
        //return decoratedResponse(patientService.signInPatient(patientSignInDto));
    }

    @PostMapping("sign_in/cancel/{signInId}")
    public void cancelSignIn(@PathVariable UUID signInId) {
        PatientSignIn patientSignIn = this.patientSignInService.cancelSignIn(signInId);
        this.notificationService.patientSignInRequestChanged(patientSignIn);
        //return decoratedResponse(patientService.signInPatient(patientSignInDto));
    }

//    @PostMapping("sign_in/all/{pageNum}")
//    public PagedDTO<PatientSignInListRepDto> getPatientSignInList(@PathVariable int pageNum, @RequestBody SearchCodeDto searchCodeDto) {
//        PagedList<PatientSignIn> list = patientService.getPatientSignInList(pageNum, searchCodeDto, true);
//        return pagedResponse(PatientSignInDtoConverter.toPatientSignInListRep(list.getList()), list);
//    }

    @PostMapping("sign_in/list/{pageNum}")
    public PagedDTO<PatientSignInListRespDto> getPatientSignInList(@PathVariable int pageNum, @RequestBody PatientSignInFilterDto signInFilter) {
        PagedList<PatientSignInView> list = patientSignInService.getPatientSignInList(pageNum, signInFilter);
        List<PatientSignInListRespDto> dtoList = null;
        if (signInFilter.getCheck3024())
            dtoList = PatientSignInDtoConverter.toPatientCheck3024Rep(list.getList());
        else
            dtoList = PatientSignInDtoConverter.toPatientSignInListRep(list.getList());
        return pagedResponse(dtoList, list);
    }


//    @PostMapping("sign_in/list/current/by_ward/{wardId}")
//    public DecoratedDTO<List<PatientSignInListRepDto>> getWardPatientList(@PathVariable UUID wardId) {
//        List<Ward> wardList = patientSignInService.getCurrentPatientWardList();
//        List<PatientSignIn> patientSignInList =
//                wardList.stream()
//                .map(Ward::getRoomList).flatMap(wardRoomList -> wardRoomList.stream())
//                .map(WardRoom::getBedList).flatMap(bedList -> bedList.stream())
//                        .map(WardRoomBed::getCurrentSignIn).collect(Collectors.toList());
//        return decoratedResponse(PatientSignInDtoConverter.toPatientSignInListRep(patientSignInList));
//    }

    @PostMapping("sign_in/{uuid}")
    public DecoratedDTO<PatientSignInRespDto> getSignInDetail(@PathVariable UUID uuid) {
        return decoratedResponse(patientSignInService.getPatientSignIn(uuid).toDto());
    }

    @PostMapping("sign_in/assign_bed")
    public UUID assignPatientSignInBed(@RequestBody PatientSignInAssignBedDto patientSignInAssignBedDto) {
        return this.patientSignInService.assignPatientSignInBed(patientSignInAssignBedDto);
    }

    @PostMapping("ward/list")
    public DecoratedDTO<List<WardDto>> getWardList(@RequestBody WardFilterDto wardFilter) {
        //this.initializeBedList();
        List<Ward> wardList = patientSignInService.getWardList(wardFilter);
        return decoratedResponse(DtoUtils.toDtoList(wardList));
    }

    private void initializeBedList() {
        Ward ward = Ebean.find(Ward.class).where().eq("name", "icu").findOne();
        for (WardRoom room : ward.getRoomList()) {
            for (Integer i = 1; i <= 40; i++) {
                WardRoomBed bed = new WardRoomBed();
                bed.setOrder(i);
                bed.setEnabled(true);
                bed.setCode("00" + i);
                bed.setName(i.toString() + "床");
                room.getBedList().add(bed);
            }
        }
        Ebean.save(ward);
    }

    @PostMapping("ward/list/tree")
    public DecoratedDTO<List<TreeWardNodeDto>> getAllWardPatientWardTreeList(@RequestBody WardFilterDto wardFilter) {
        if (wardFilter.getHideEmptyBed() == null)
            wardFilter.setHideEmptyBed(true);
        List<Ward> wardList = patientSignInService.getWardList(wardFilter);
        return decoratedResponse(PatientSignInDtoConverter.toTreeWardNodeList(wardList));
    }

    @PostMapping("sign_in/nursing_record/save")
    public DecoratedDTO<NursingRecordRespDto> saveNursingRecord(@RequestBody NursingRecordSaveDto nursingRecordSaveDto) {
        return decoratedResponse(this.patientSignInService.saveNursingRecord(nursingRecordSaveDto));
    }

    @PostMapping("sign_in/temp_record/save")
    public DecoratedDTO<TempRecordRespDto> saveTempRecord(@RequestBody TempRecordSaveDto tempRecordSaveDto) {
        return decoratedResponse(this.patientSignInService.saveTempRecord(tempRecordSaveDto));
    }

    @PostMapping("sign_in/temp_record/batch/save")
    public DecoratedDTO<List<TempRecordRespDto>> saveTempRecord(@RequestBody List<TempRecordSaveDto> tempRecordSaveDtoList) {
        return decoratedResponse(this.patientSignInService.saveTempRecordList(tempRecordSaveDtoList));
    }

    @PostMapping("sign_in/nursing_record/{patientSignInId}/list")
    public DecoratedDTO<List<NursingRecordRespDto>> getNursingRecordList(@PathVariable UUID patientSignInId, @RequestBody NursingRecordFilter filter) {
        return decoratedResponse(DtoUtils.toDtoList(this.patientSignInService.getNursingRecordList(patientSignInId, filter)));
    }

    @PostMapping("sign_in/temp_record/{patientSignInId}/list")
    public DecoratedDTO<List<TempRecordRespDto>> getTempRecordList(@PathVariable UUID patientSignInId, @RequestBody TempRecordFilter filter) {
        List<TempRecord> tempRecordList = this.patientSignInService.getTempRecordList(patientSignInId, filter);
        List<TempRecordRespDto> respDtoList = DtoUtils.toDtoList(tempRecordList);
        return decoratedResponse(respDtoList);
    }

    @PostMapping("sign_in/nursing_record/{uuid}")
    public DecoratedDTO<NursingRecordRespDto> getNursingRecord(@PathVariable UUID uuid) {
        return decoratedResponse(this.patientSignInService.getNursingRecord(uuid));
    }

    @PostMapping("sign_in/temp_record/{uuid}")
    public DecoratedDTO<TempRecordRespDto> getTempRecord(@PathVariable UUID uuid) {
        TempRecordRespDto dto = this.patientSignInService.getTempRecord(uuid);
        return decoratedResponse(dto);
    }

    @PostMapping("sign_in/{patientSignInId}/weeks")
    public DecoratedDTO<Integer> getPatientSignInWeeks(@PathVariable UUID patientSignInId) {
        PatientSignIn patientSignIn = this.patientSignInService.findById(PatientSignIn.class, patientSignInId);
        return decoratedResponse(patientSignIn.getPatientSignInWeeks());
    }

    @PostMapping("sign_in/temp_record/{patientSignInId}/{weekNumber}/list")
    public DecoratedDTO<TempRecordChartRespDto> getTempRecordByWeekList(@PathVariable UUID patientSignInId, @PathVariable Integer weekNumber) {
        List<LocalDateTime> weekDateList = this.patientSignInService.getPatientSignInWeekDate(patientSignInId, weekNumber);
        List<TempRecord> tempRecordList = this.patientSignInService.getTempRecordByWeekList(patientSignInId, weekDateList);
        PatientSignIn patientSignIn = this.patientSignInService.findById(PatientSignIn.class, patientSignInId);
        TempRecordChartRespDto dto = PatientSignInDtoConverter.toTempRecordChartRespDto(weekDateList, tempRecordList, weekNumber, patientSignIn);
        return decoratedResponse(dto);

    }

    @PostMapping("sign_in/temp_record/{uuid}/delete")
    public void deleteTempRecord(@PathVariable UUID uuid) {
        this.patientSignInService.deleteTempRecord(uuid);
    }

    @PostMapping("sign_in/nursing_record/{uuid}/delete")
    public void deleteNursingRecord(@PathVariable UUID uuid) {
        this.patientSignInService.deleteNursingRecord(uuid);
    }

    @PostMapping("sign_in/sign_out_request/{patientSignInId}")
    public DecoratedDTO<PatientSignOutRequestDto> tryGetSignOutRequest(@PathVariable UUID patientSignInId) {
        PatientSignOutRequest signOutRequest = this.patientSignInService.findOneOrEmptySignOutRequest(patientSignInId);
        PatientSignOutRequestDto dto = new PatientSignOutRequestDto();
        if(signOutRequest != null)
            dto = signOutRequest.toDto();
        return decoratedResponse(dto);
    }

    @PostMapping("sign_in/sign_out_request/save")
    public DecoratedDTO<PatientSignOutRequestDto> saveSignOutRequest(@RequestBody PatientSignOutRequestSaveDto patientSignOutRequestSaveDto) {
        PatientSignOutRequest signOutRequest = this.patientSignInService.saveNewPatientSignOutRequest(patientSignOutRequestSaveDto);
        return decoratedResponse(signOutRequest.toDto());
    }

    @PostMapping("sign_in/sign_out_request/{signOutRequestId}/delete")
    public void deleteSignOutRequest(@PathVariable UUID signOutRequestId) {
        this.patientSignInService.deleteSignOutRequest(signOutRequestId);
    }

    @PostMapping("sign_in/sign_out_request/{signOutRequestId}/submit_validation")
    public DecoratedDTO<PatientSignOutRequestDto> validateSignOutRequest(@PathVariable UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.patientSignInService.validateSignOutRequest(signOutRequestId);
        this.notificationService.patientPrepareSignOut(signOutRequest);
        return decoratedResponse(signOutRequest.toDto());
    }

    @PostMapping("sign_in/sign_out_request/{signOutRequestId}/validation_complete")
    public DecoratedDTO<PatientSignOutRequestDto> validateSignOutRequestComplete(@PathVariable UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.patientSignInService.validateSignOutRequestComplete(signOutRequestId);
        return decoratedResponse(signOutRequest.toDto());
    }

    @PostMapping("sign_in/sign_out_request/{signOutRequestId}/settle")
    public DecoratedDTO<PatientSignOutRequestDto> settleSignOutRequest(@PathVariable UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.patientSignInService.settleSignOutRequest(signOutRequestId);
        return decoratedResponse(signOutRequest.toDto());
    }

    @PostMapping("sign_in/sign_out_request/{signOutRequestId}/disable")
    public void disableAllPrescription(@PathVariable UUID signOutRequestId) {
        this.patientSignInService.disableAllPrescription(signOutRequestId);
    }

    @PostMapping("sign_in/{uuid}/sign_out_request/cancel")
    public void cancelSignOutRequest(@PathVariable UUID uuid) {
        PatientSignIn patientSignIn = this.patientSignInService.cancelSignOutRequest(uuid);
        this.notificationService.patientSignedOutRequestChanged(patientSignIn);
    }

    @PostMapping("sign_in/{patientSignInId}/sign_out_request/confirm")
    public DecoratedDTO<PatientSignInRespDto> confirmSignOutRequest(@PathVariable UUID patientSignInId) {
        PatientSignIn patientSignIn = this.patientSignInService.confirmSignOutRequest(patientSignInId);
        this.notificationService.patientSignedOutRequestChanged(patientSignIn);
        return decoratedResponse(patientSignIn.toDto());
    }

    @PostMapping("{patientSignInId}/medical_record/{typeId}/List")
    public DecoratedDTO<List<MedicalRecordListDto>> getPatientMedicalRecordListByType(@PathVariable UUID patientSignInId, @PathVariable UUID typeId) {
        List<MedicalRecord> medicalRecordList = patientSignInService.getMedicalRecordList(patientSignInId, typeId, null);
        return decoratedResponse(PatientSignInDtoConverter.toMedicalRecordDtoList(medicalRecordList));
    }

    @PostMapping("{patientSignInId}/medical_record/by_template/{templateId}")
    public DecoratedDTO<List<MedicalRecordListDto>> getPatientMedicalRecordListByTemplate(@PathVariable UUID patientSignInId, @PathVariable UUID templateId) {
        List<MedicalRecord> medicalRecordList = patientSignInService.getMedicalRecordList(patientSignInId, null, templateId);
        return decoratedResponse(PatientSignInDtoConverter.toMedicalRecordDtoList(medicalRecordList));
    }

    @PostMapping("medical_record/{medicalRecordId}")
    public DecoratedDTO<MedicalRecordDto> getPatientMedicalRecord(@PathVariable UUID medicalRecordId) {
        return decoratedResponse(patientSignInService.findById(MedicalRecord.class, medicalRecordId).toDto());
    }

    @PostMapping("medical_record/save")
    public DecoratedDTO<UUID> saveMedicalRecord(@RequestBody MedicalRecordSaveDto saveDto) {
        return decoratedResponse(patientSignInService.saveMedicalRecord(saveDto).getUuid());
    }

    @PostMapping("medical_record/lock")
    public DecoratedDTO<MedicalRecordListDto> lockMedicalRecord(@RequestBody MedicalRecordLockDto lockDto) {
        return decoratedResponse(this.patientSignInService.lockMedicalRecord(lockDto).toListDto());
    }

    @PostMapping("medical_record/locked_time/{medicalRecordId}")
    public DecoratedDTO<MedicalRecordLockedRespDto> getLastLockedTime(@PathVariable UUID medicalRecordId) {
        return decoratedResponse(this.patientSignInService.getLockedInfo(medicalRecordId));
    }

    @PostMapping("medical_record/unlock/{medicalRecordId}")
    public void unlockMedicalRecord(@PathVariable UUID medicalRecordId) {
        this.patientSignInService.unlockMedicalRecord(medicalRecordId);
    }

    @PostMapping("medical_record/{medicalRecordId}/delete")
    public void deletePatientMedicalRecord(@PathVariable UUID medicalRecordId) {
        this.patientSignInService.delete(MedicalRecord.class, medicalRecordId);
    }

    @PostMapping("medical_record/type/list/{patientSignInId}")
    public DecoratedDTO<List<PatientMedicalRecordTypeDto>> getMedicalRecordTypeList(@PathVariable UUID patientSignInId, @RequestBody MedicalRecordTypeFilter filterDto) {
        filterDto.setEnabled(true);
        List<MedicalRecordType> medicalRecordTypeList = basicService.getMedicalRecordTypeList(filterDto);
        List<PatientMedicalRecordCount> medicalRecordCountList = this.patientSignInService.getPatientMedicalRecordCountList(patientSignInId);
        return decoratedResponse(PatientSignInDtoConverter.toPatientMedicalRecordTypeList(medicalRecordTypeList, medicalRecordCountList, patientSignInId));
    }

    @PostMapping("medical_record/type/{typeId}/{patientSignInId}/count")
    public Integer getMedicalRecordType(@PathVariable UUID typeId, @PathVariable UUID patientSignInId) {
        List<PatientMedicalRecordCount> patientMedicalRecordCountList = this.patientSignInService.getPatientMedicalRecordListByType(patientSignInId, typeId);
        if (patientMedicalRecordCountList.size() == 0)
            return 0;
        else
            return patientMedicalRecordCountList.get(0).getRecordCount();
    }

    @PostMapping("medical_record/new/from_template")
    public DecoratedDTO<MedicalRecordTemplateDto> newMedicalRecordFromTemplate(@RequestBody NewMedicalRecordPram newMedicalRecordPram) {
        return decoratedResponse(this.patientSignInService.createNewMedicalRecordFromTemplate(newMedicalRecordPram).toDto());
    }

    @PostMapping("medical_record/main/system/update")
    public DecoratedDTO<String> updateMainMedicalRecordSystemValue(@RequestBody MedicalRecordSystemUpdateDto medicalRecordSystemUpdateDto) {
        return decoratedResponse(this.patientSignInService.updateMainMedicalRecordSystemValue(medicalRecordSystemUpdateDto));
    }

    @RequestMapping(value = "{patientSignInId}/qr_code")
    public DecoratedDTO<String> getQRCode(@PathVariable UUID patientSignInId) throws IOException {
        //String qrCodeString = Utils.crateQRCode(patientSignInId.toString(), 100,100);
        String qrCodeString = Utils.crateQRCode(this.qrCodeBaseUrl + patientSignInId.toString(), 100, 100);
        return decoratedResponse(qrCodeString);
    }

    @PostMapping("medical_record/tag/new")
    public DecoratedDTO<List<MedicalRecordTagDto>> getQRCode(@RequestBody MedicalRecordTagSaveDto newTag) {
        List<MedicalRecordTag> tagList = this.patientSignInService.saveNewTag(newTag);
        return decoratedResponse(DtoUtils.toDtoList(tagList));
    }

    @PostMapping("medical_record/tag/delete/{tagId}")
    public DecoratedDTO<List<MedicalRecordTagDto>> deleteMedicalRecordTag(@PathVariable UUID tagId) {
        List<MedicalRecordTag> tagList = this.patientSignInService.deletetMedicalRecordTag(tagId);
        return decoratedResponse(DtoUtils.toDtoList(tagList));
    }

    @PostMapping("medical_record/tag/list/{medicalRecordId}")
    public DecoratedDTO<List<MedicalRecordTagDto>> getMedicalRecordTagList(@PathVariable UUID medicalRecordId) {
        return decoratedResponse(DtoUtils.toDtoList(this.patientSignInService.getMedicalRecordTagList(medicalRecordId)));
    }


    @PostMapping("drg_group/list/all")
    public DecoratedDTO<List<DrgGroupRespDto>> getAllDrgGroup() {
        return decoratedResponse(DtoUtils.toDtoList(this.patientSignInService.getDrgGroupList(null)));
    }

    @PostMapping("drg_group/list/enabled")
    public DecoratedDTO<List<DrgGroupRespDto>> getEnabledDrgGroup() {
        return decoratedResponse(DtoUtils.toDtoList(this.patientSignInService.getDrgGroupList(true)));
    }

    @PostMapping("drg_group/list/save")
    public DecoratedDTO<DrgGroupRespDto> getEnabledDrgGroup(@RequestBody DrgGroupSaveDto saveDto) {
        return decoratedResponse(this.patientSignInService.saveDrgGroup(saveDto).toDto());
    }

    @PostMapping("sign_in/clone/{uuid}")
    public DecoratedDTO<PatientSignInListRespDto> cloneToNewPatientSignIn(@PathVariable UUID uuid) {
        return decoratedResponse(this.patientSignInService.cloneToNewPatientSignIn(uuid).toListDto());
    }

    @PostMapping("{patientSignInId}/bed/sign_out")
    public void signOutCurrentBed(@PathVariable UUID patientSignInId) {
        this.patientSignInService.signOutCurrentBed(patientSignInId);
    }

    @PostMapping("{patientSignInId}/medical_record/last_sign_in/copy")
    public void copyLastSignInMedicalRecord(@PathVariable UUID patientSignInId, @RequestBody List<UUID> toCloneList) {
        this.patientSignInService.copyLastSignInMedicalRecord(patientSignInId, toCloneList);
    }

    @PostMapping("{patientSignInId}/medical_record/last_sign_in/list")
    public DecoratedDTO<List<MedicalRecordListDto>> getLastSignInMedicalRecordList(@PathVariable UUID patientSignInId) {
        UUID lastSignInId =  patientSignInService.getLastSignInId(patientSignInId);
        List<MedicalRecord> medicalRecordList = patientSignInService.getMedicalRecordList(lastSignInId, null, null);
        return decoratedResponse(PatientSignInDtoConverter.toMedicalRecordDtoList(medicalRecordList));
    }
}
