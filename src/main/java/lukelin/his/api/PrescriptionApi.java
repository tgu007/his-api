package lukelin.his.api;

import io.ebean.Ebean;
import io.ebean.PagedList;
import lukelin.common.sdk.DecoratedDTO;
import lukelin.common.sdk.PagedDTO;
import lukelin.common.springboot.controller.BaseController;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrder;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.*;
import lukelin.his.domain.enums.EntityType;
import lukelin.his.domain.enums.Fee.FeeRecordMethod;
import lukelin.his.domain.enums.Prescription.PrescriptionCardType;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.dto.prescription.request.PrescriptionChangeLogReqDto;
import lukelin.his.dto.conveter.PatientSignInDtoConverter;
import lukelin.his.dto.conveter.PrescriptionDtoConverter;
import lukelin.his.dto.mini_porgram.MiniPatientDto;
import lukelin.his.dto.prescription.request.*;
import lukelin.his.dto.prescription.request.filter.PredefinePrescriptionFilterDto;
import lukelin.his.dto.prescription.request.filter.PrescriptionFilterDto;
import lukelin.his.dto.prescription.request.PrescriptionTreatmentSaveDto;
import lukelin.his.dto.prescription.response.*;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;
import lukelin.his.service.NotificationService;
import lukelin.his.service.PatientSignInService;
import lukelin.his.service.PrescriptionService;
import lukelin.his.service.UserService;
import lukelin.his.system.ConfigBeanProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/prescription")
public class PrescriptionApi extends BaseController {
    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientSignInService patientSignInService;

    @Autowired
    private ConfigBeanProp configBeanProp;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;


    @PostMapping("{prescriptionId}/fee_count")
    public DecoratedDTO<Integer> getPrescriptionFeeCount(@PathVariable UUID prescriptionId) {
        Integer feeCount = this.prescriptionService.getPrescriptionFeeCount(prescriptionId);
        return decoratedResponse(feeCount);
    }

    @PostMapping("medicine/save")
    public void saveMedicinePrescription(@RequestBody PrescriptionMedicineSaveDto medicinePrescriptionSaveDto) {
        this.userService.validateDoctorAgreement(null);
        this.prescriptionService.savePrescription(medicinePrescriptionSaveDto);
    }

    @PostMapping("medicine/detail/{prescriptionId}")
    public DecoratedDTO<PrescriptionMedicineRespDto> getMedicinePrescriptionDetail(@PathVariable UUID prescriptionId) {
        return decoratedResponse(prescriptionService.getMedicinePrescriptionDetail(prescriptionId).toDto());
    }

    @PostMapping("medicine_text/save")
    public void saveMedicineTextPrescription(@RequestBody PrescriptionMedicineTextSaveDto medicineTextPrescriptionSaveDto) {
        this.userService.validateDoctorAgreement(null);
        this.prescriptionService.savePrescription(medicineTextPrescriptionSaveDto);
    }

    @PostMapping("medicine_text/detail/{prescriptionId}")
    public DecoratedDTO<PrescriptionMedicineTextRespDto> getMedicineTextPrescriptionDetail(@PathVariable UUID prescriptionId) {
        return decoratedResponse(prescriptionService.getMedicineTextPrescriptionDetail(prescriptionId).toDto());
    }

    @PostMapping("text/save")
    public void saveTextPrescription(@RequestBody PrescriptionSaveDto textPrescriptionSaveDto) {
        this.userService.validateDoctorAgreement(null);
        this.prescriptionService.savePrescription(textPrescriptionSaveDto);
    }

    @PostMapping("text/detail/{prescriptionId}")
    public DecoratedDTO<PrescriptionRespDto> getTextPrescriptionDetail(@PathVariable UUID prescriptionId) {
        return decoratedResponse(prescriptionService.getTextPrescriptionDetail(prescriptionId).toDto());
    }

    @PostMapping("treatment/save")
    public void saveTreatmentPrescription(@RequestBody PrescriptionTreatmentSaveDto treatmentPrescriptionSaveDto) {
        this.userService.validateDoctorAgreement(null);
        this.prescriptionService.savePrescription(treatmentPrescriptionSaveDto);
    }

    @PostMapping("treatment/detail/{prescriptionId}")
    public DecoratedDTO<PrescriptionTreatmentRespDto> getTreatmentPrescriptionDetail(@PathVariable UUID prescriptionId) {
        return decoratedResponse(prescriptionService.getTreatmentPrescriptionDetail(prescriptionId).toDto());
    }

    //Todo change to paging
    @PostMapping("list")
    public DecoratedDTO<List<PrescriptionListRespDto>> getPatientPrescriptionList(@RequestBody PrescriptionFilterDto filterDto) {
        List<Prescription> prescriptionList = prescriptionService.getPrescriptionList(filterDto);
        List<PrescriptionListRespDto> dtoList = PrescriptionDtoConverter.toPrescriptionListRespDto(prescriptionList);
        return decoratedResponse(dtoList);
    }

    @PostMapping("list/{pageNumber}/{pageSize}")
    public PagedDTO<PrescriptionListRespDto> getPatientPrescriptionList(@RequestBody PrescriptionFilterDto filterDto, @PathVariable int pageNumber, @PathVariable int pageSize) {
        PagedList<Prescription> prescriptionList = prescriptionService.getPagedPrescriptionList(filterDto, pageNumber, pageSize);
        List<PrescriptionListRespDto> dtoList = PrescriptionDtoConverter.toPrescriptionListRespDto(prescriptionList.getList());
        return pagedResponse(dtoList, prescriptionList);
    }

    @PostMapping("list/medical_record")
    public DecoratedDTO<List<PrescriptionDescriptionListRespDto>> getPatientPrescriptionDescriptionList(@RequestBody PrescriptionFilterDto filterDto) {
        List<Prescription> prescriptionList = prescriptionService.getPrescriptionList(filterDto);
        List<PrescriptionDescriptionListRespDto> dtoList = PrescriptionDtoConverter.toPrescriptionDescriptionListRespDto(prescriptionList);
        return decoratedResponse(dtoList);
    }

    @PostMapping("action/submit")
    public void submitPrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.created, PrescriptionStatus.submitted, PrescriptionChangeAction.submit, reqDto);
        this.notificationService.prescriptionSubmitted(prescriptionList);
    }

    @PostMapping("action/approve")
    public void approvePrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.submitted, PrescriptionStatus.approved, PrescriptionChangeAction.approve, reqDto);
        this.notificationService.approvedPrescriptionChanged(prescriptionList);
    }

    @PostMapping("action/reject")
    public void rejectPrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.submitted, PrescriptionStatus.created, PrescriptionChangeAction.reject, reqDto);
        this.notificationService.prescriptionRejected(prescriptionList);
    }

    @PostMapping("action/disable")
    public void disablePrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.approved, PrescriptionStatus.pendingDisable, PrescriptionChangeAction.disable, reqDto);
        this.notificationService.prescriptionPendingDisable(prescriptionList);
    }

    @PostMapping("action/disable_confirm")
    public void confirmDisablePrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.pendingDisable, PrescriptionStatus.disabled, PrescriptionChangeAction.confirmDisable, reqDto);
        this.notificationService.prescriptionConfirmDisable(prescriptionList);
    }


    @PostMapping("action/disabled_restore/{prescriptionId}")
    public void restoreDisabledPrescription(@PathVariable UUID prescriptionId) {
        this.prescriptionService.restoreDisabledPrescription(prescriptionId);
    }

    @PostMapping("action/cancel")
    public void cancelPrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.checkPendingCancelPrescriptionFee(reqDto);
        this.notificationService.approvedPrescriptionChanged(prescriptionList);
        //this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.approved, PrescriptionStatus.canceled, PrescriptionChangeAction.cancel, reqDto);
    }

    @PostMapping("action/delete")
    public void deletePrescription(@RequestBody PrescriptionChangeStatusReqDto reqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.created, PrescriptionStatus.deleted, PrescriptionChangeAction.delete, reqDto);
        this.notificationService.prescriptionDeleted(prescriptionList);
    }

    @PostMapping("group")
    public void setPrescriptionGroup(@RequestBody PrescriptionGroupReqDto prescriptionGroupReqDto) {
        prescriptionService.groupPrescription(prescriptionGroupReqDto);
    }

    @PostMapping("clone")
    public void clonePrescription(@RequestBody PrescriptionCloneReqDto prescriptionCloneReqDto) {
        prescriptionService.clonePrescription(prescriptionCloneReqDto);
    }


    @PostMapping("changed/list")
    public DecoratedDTO<List<BaseWardPatientListRespDto>> getChangedPrescriptionList(@RequestBody PrescriptionFilterDto filterDto) {
        List<Prescription> changedMedicinePrescriptionList = this.prescriptionService.getChangedPrescriptionList(filterDto);
        List<Ward> currentPatientList = this.patientSignInService.getCurrentPatientWardList();
        List<BaseWardPatientListRespDto> changedPrescriptionListResp = PrescriptionDtoConverter.toChangedPrescriptionList(currentPatientList, changedMedicinePrescriptionList);
        return decoratedResponse(changedPrescriptionListResp);
    }

    @PostMapping("execution/list/{includeExecuted}")
    public DecoratedDTO<List<BaseWardPatientListRespDto>> getPendingExecutionPrescriptionList(@PathVariable Boolean includeExecuted,  @RequestBody PrescriptionFilterDto filterDto) {
        List<Prescription> pendingExecutionPrescriptionList = this.prescriptionService.getPendingExecutionPrescriptionList(filterDto, includeExecuted);
        List<Ward> currentPatientList = this.patientSignInService.getCurrentPatientWardList();
        return decoratedResponse(PrescriptionDtoConverter.toPendingExecutionPrescriptionList(currentPatientList, pendingExecutionPrescriptionList));
    }

    @PostMapping("execution/list/execute")
    public void executePrescriptionList(@RequestBody PrescriptionExecutionListReqDto executionListReqDto) {
        List<Prescription> prescriptionList = this.prescriptionService.executePrescriptionList(executionListReqDto);
        this.notificationService.prescriptionExecuted(prescriptionList);
    }

    @PostMapping("medicine/order/pending/list")
    public DecoratedDTO<List<BaseWardPatientListRespDto>> getPendingMedicineOrderList(@RequestBody PrescriptionFilterDto filterDto) {
        List<BaseWardPatientListRespDto> pendingMedicineOrderList = this.prescriptionService.getPendingMedicineOrderList(filterDto);
        List<Ward> currentPatientList = this.patientSignInService.getCurrentPatientWardList();
        return decoratedResponse(PatientSignInDtoConverter.toWardPatientList(currentPatientList, pendingMedicineOrderList));
    }

    @PostMapping("medicine/order/submit")
    public void submitMedicineOrder(@RequestBody PrescriptionMedicineOrderCreateDto prescriptionMedicineOrderCreateDto) {
        PrescriptionMedicineOrder order = this.prescriptionService.createPrescriptionMedicineOrder(prescriptionMedicineOrderCreateDto);
        this.notificationService.medicineOrderSubmitted(order);
    }

    @PostMapping("nursing/card/oral")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getMedicineOralCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{configBeanProp.getUseMethodOral(), configBeanProp.getUseMethodNoseFeed()};
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.oral);

    }

    @PostMapping("nursing/card/treatment")
    public DecoratedDTO<List<PrescriptionTreatmentNursingCardRespDto>> getTreatmentCardList(@RequestBody PrescriptionFilterDto filterDto) {
        filterDto.setTreatmentCardFilter(true);
        List<Prescription> prescriptionList = this.prescriptionService.getNursingCardList(filterDto, EntityType.treatment);
        List<PrescriptionCardSignature> signatureList = this.prescriptionService.getPrescriptionCardSignatureList(PrescriptionCardType.treatment, prescriptionList, filterDto.getCardDate());
        List<PrescriptionTreatmentNursingCardRespDto> patientTreatmentCardDtoList = PrescriptionDtoConverter.toPatientTreatmentNursingCardList(prescriptionList, signatureList, filterDto.getCardDate());
        return decoratedResponse(patientTreatmentCardDtoList);
    }

    private DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> buildDecoratedMedicineCardList(String[] useMethodArray, PrescriptionFilterDto filterDto, PrescriptionCardType cardType) {
        filterDto.setUseMethodList(Arrays.asList(useMethodArray));
        List<Prescription> prescriptionList = this.prescriptionService.getNursingCardList(filterDto, EntityType.medicine);
        List<PrescriptionCardSignature> signatureList = this.prescriptionService.getPrescriptionCardSignatureList(cardType, prescriptionList, filterDto.getCardDate());
        List<PrescriptionPatientNursingCardRespDto> patientMedicineCardDtoList = PrescriptionDtoConverter.toPatientNursingCardList(prescriptionList, cardType, signatureList, filterDto.getCardDate());
        return decoratedResponse(patientMedicineCardDtoList);
    }

    @PostMapping("nursing/card/injection")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getInjectionCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray =
                new String[]{
                        configBeanProp.getUseMethodInjectionMuscle(),
                        configBeanProp.getUseMethodInjectionSkinUnder(),
                        configBeanProp.getUseMethodInjectionSkinIn(),
                        configBeanProp.getUseMethodInjectionVein(),
                };
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.injection);

    }

    @PostMapping("nursing/card/vein_drop")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getVeinDropCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{configBeanProp.getUseMethodVeinDrop()};
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.drop);
    }

    @PostMapping("nursing/card/vein_drop_bottle")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getDropBottleCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{
                configBeanProp.getUseMethodVeinDrop(),
                configBeanProp.getUseMethodBladderWashout(),
                configBeanProp.getUseMethodAerosol(),
                configBeanProp.getUseMethodInjectionMuscle(),
                configBeanProp.getUseMethodInjectionSkinIn(),
                configBeanProp.getUseMethodInjectionSkinUnder(),
                configBeanProp.getUseMethodInjectionVein()
        };
        filterDto.setUseMethodList(Arrays.asList(useMethodArray));
        List<Prescription> prescriptionList = this.prescriptionService.getNursingCardList(filterDto, EntityType.medicine);
        List<PrescriptionPatientNursingCardRespDto> patientMedicineCardDtoList = PrescriptionDtoConverter.toBottleCardList(prescriptionList, PrescriptionCardType.dropBottle);
        return decoratedResponse(patientMedicineCardDtoList);
    }

    @PostMapping("nursing/card/lab_bottle")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getLabBottleCardList(@RequestBody PrescriptionFilterDto filterDto) {
        filterDto.setLabBottleCardFilter(true);
        List<Prescription> prescriptionList = this.prescriptionService.getNursingCardList(filterDto, EntityType.treatment);
        List<PrescriptionPatientNursingCardRespDto> patientMedicineCardDtoList = PrescriptionDtoConverter.toLabBottleCardList(prescriptionList, PrescriptionCardType.labBottle);
        return decoratedResponse(patientMedicineCardDtoList);
    }

    @PostMapping("nursing/card/aerosol")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getAerosolCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{configBeanProp.getUseMethodAerosol()};
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.aerosol);
    }

    @PostMapping("nursing/card/bladder_washout")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getBladderWashoutCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{configBeanProp.getUseMethodBladderWashout()};
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.bladderWashout);
    }

    @PostMapping("nursing/card/nose_feed")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getNoseFeedCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{configBeanProp.getUseMethodNoseFeed()};
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.bladderWashout);
    }


    @PostMapping("nursing/card/external")
    public DecoratedDTO<List<PrescriptionPatientNursingCardRespDto>> getExternalCardList(@RequestBody PrescriptionFilterDto filterDto) {
        String[] useMethodArray = new String[]{configBeanProp.getUseMethodExternal()};
        return this.buildDecoratedMedicineCardList(useMethodArray, filterDto, PrescriptionCardType.external);
    }

    @PostMapping("card/sign")
    public DecoratedDTO<PrescriptionSignatureRespDto> signPrescriptionCard(@RequestBody PrescriptionCardSignSaveDto signSaveDto) {
        return decoratedResponse(this.prescriptionService.signPrescriptionCard(signSaveDto));
    }

    @PostMapping("card/sign/cancel/{signId}")
    public void cancelPrescriptionCardSignature(@PathVariable UUID signId) {
        this.prescriptionService.cancelPrescriptionCardSignature(signId);
    }

    @PostMapping("mini/execution/list/execute")
    public DecoratedDTO<MiniPatientDto> executePrescription(@RequestBody PrescriptionExecutionListReqMiniDto executionListReqDto) {
        executionListReqDto.setFeeRecordMethod(FeeRecordMethod.miniProgram);
        this.prescriptionService.validateRecoveryTreatment(executionListReqDto);
        this.prescriptionService.executePrescriptionList(executionListReqDto);
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, executionListReqDto.getPatientSignInId());
        PrescriptionFilterDto filterDto = new PrescriptionFilterDto();
        filterDto.setEmployeeId(executionListReqDto.getEmployeeId());
        List<UUID> patientSignInIdList = new ArrayList<>();
        patientSignInIdList.add(patientSignIn.getUuid());
        filterDto.setPatientSignInIdList(patientSignInIdList);
        List<Prescription> patientPrescriptionList = this.prescriptionService.getPendingExecutionPrescriptionList(filterDto, false);
        return decoratedResponse(patientSignIn.toMinPatientDto(patientPrescriptionList));
    }

    @PostMapping("change_log/date/change")
    public DecoratedDTO<PrescriptionChangeLogRespDto> changePrescriptionChangeLogDate(@RequestBody PrescriptionChangeLogReqDto reqDto) {
        PrescriptionChangeLog log = this.prescriptionService.updateLogTime(reqDto);
        return decoratedResponse(log.toDto());
    }

    @PostMapping("start_date/batch_update")
    public void updateStartDateInBatch(@RequestBody List<PrescriptionChangeLogReqDto> reqDtoList) {
        this.prescriptionService.updateStartDateInBatch(reqDtoList);
    }

    @PostMapping("treatment/adjust_quantity/set")
    public void setTreatmentPrescriptionAdjustQuantity(@RequestBody TreatmentPrescriptionQuantityAdjust adjustReqDto) {
        this.prescriptionService.setTreatmentPrescriptionAdjustQuantity(adjustReqDto);
    }

    @PostMapping("pre_defined/group/list/{pageNum}/{pageSize}")
    public PagedDTO<PreDefinedPrescriptionListDto> getPredefinedPrescriptionGroupList(@RequestBody PredefinePrescriptionFilterDto filterDto, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PagedList<PreDefinedPrescription> preDefinedList = this.prescriptionService.getPreDefinedPrescriptionList(filterDto, pageNum, pageSize);
        List<PreDefinedPrescriptionListDto> dtoList = PrescriptionDtoConverter.toPreDefinedPrescriptionGroupDto(preDefinedList.getList());
        return pagedResponse(dtoList, preDefinedList);
    }

    @PostMapping("pre_defined/group/add")
    public DecoratedDTO<PreDefinedPrescriptionListDto> addPredefinedPrescriptionGroup(@RequestBody PreDefinedPrescriptionSaveDto saveDto) {
        PreDefinedPrescription preDefinedPrescription = this.prescriptionService.findOrCreatePredefinedPrescription(saveDto);
        return decoratedResponse(preDefinedPrescription.toListDto());
    }

    @PostMapping("pre_defined/group/line_list/save")
    public void savePredefinedPrescriptionLineList(@RequestBody PreDefinedPrescriptionSaveDto saveDto) {
        this.prescriptionService.savePredefinedPrescriptionLineList(saveDto);
    }

    @PostMapping("pre_defined/treatment/group/{groupId}/line/list")
    public DecoratedDTO<List<PreDefinedPrescriptionTreatmentDto>> getTreatmentPreDefinedPrescriptionLineList(@PathVariable UUID groupId) {
        List<PreDefinedPrescriptionTreatment> treatmentList = this.prescriptionService.getTreatmentPreDefinedPrescriptionLineList(groupId);
        return decoratedResponse(DtoUtils.toDtoList(treatmentList));
    }

    @PostMapping("pre_defined/medicine/group/{groupId}/line/list")
    public DecoratedDTO<List<PreDefinedPrescriptionMedicineDto>> getMedicinePreDefinedPrescriptionLineList(@PathVariable UUID groupId) {
        List<PreDefinedPrescriptionMedicine> medicineList = this.prescriptionService.getMedicinePreDefinedPrescriptionLineList(groupId);
        return decoratedResponse(DtoUtils.toDtoList(medicineList));
    }

    @PostMapping("pre_defined/treatment/group/{groupId}/generate_prescription/{patientSignInID}")
    public void generateTreatmentPreDefinedPrescription(@PathVariable UUID groupId, @PathVariable UUID patientSignInID) {
        this.prescriptionService.generatePreDefinedPrescription(groupId, patientSignInID);
    }

    @PostMapping("pre_defined/medicine/group/{groupId}/generate_prescription/{patientSignInID}/{totalNumber}")
    public void generateMedicinePreDefinedPrescription(@PathVariable UUID groupId, @PathVariable UUID patientSignInID, @PathVariable Integer totalNumber) {
        this.prescriptionService.generateMedicinePreDefinedPrescription(groupId, patientSignInID, totalNumber);
    }

    @PostMapping("change_log/user/update")
    public void updateLogUser(@RequestBody PrescriptionChangeLogReqDto changeLog) {
        this.prescriptionService.updateLogUser(changeLog);
    }

    @PostMapping("{prescriptionId}/group/all")
    public DecoratedDTO<List<PrescriptionMedicineRespDto>> getSameGroupPrescriptionList(@PathVariable UUID prescriptionId) {
        List<PrescriptionMedicine> prescriptionMedicineList = prescriptionService.getSameGroupPrescriptionList(prescriptionId);
        return decoratedResponse(DtoUtils.toDtoList(prescriptionMedicineList));
    }
}
