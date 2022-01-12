package lukelin.his.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.ebean.*;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.springboot.service.BaseService;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.account.AutoFee;
import lukelin.his.domain.entity.account.ViewFeeSummary;
import lukelin.his.domain.entity.basic.*;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.codeEntity.Diagnose;
import lukelin.his.domain.entity.basic.template.MedicalRecordTemplate;
import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.basic.ward.WardRoom;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineOrderLine;
import lukelin.his.domain.entity.inventory.medicine.PrescriptionMedicineReturnOrderLine;
import lukelin.his.domain.entity.patient_sign_in.*;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Basic.DepartmentTreatmentType;
import lukelin.his.domain.enums.Basic.UserRoleType;
import lukelin.his.domain.enums.Fee.FeeStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineOrderLineStatus;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineReturnOrderLineStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignInStatus;
import lukelin.his.domain.enums.PatientSignIn.PatientSignOutStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionChangeAction;
import lukelin.his.domain.enums.Prescription.PrescriptionStatus;
import lukelin.his.domain.enums.Prescription.PrescriptionType;
import lukelin.his.dto.basic.req.filter.DepartmentFilterDto;
import lukelin.his.dto.basic.req.filter.EmployeeFilter;
import lukelin.his.dto.basic.req.filter.WardFilterDto;
import lukelin.his.dto.prescription.request.PrescriptionChangeStatusReqDto;
import lukelin.his.dto.prescription.request.filter.PatientSignInFilterDto;
import lukelin.his.dto.signin.response.*;
import lukelin.his.dto.signin.request.*;
import lukelin.his.dto.yb.ClientIpInfo;
import lukelin.his.system.ConfigBeanProp;
import lukelin.his.system.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class PatientSignInService extends BaseService {
    @Autowired
    private ConfigBeanProp configBeanProp;

    @Autowired
    private BasicService basicService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private YBService ybService;

    @Autowired
    private YBServiceHY ybServiceHY;

    @Autowired
    private YBInventoryService ybInventoryService;


    @Autowired
    private NotificationService notificationService;

    @Value("${uploadYBPatient}")
    private Boolean enableYBService;

    @Value("${enableHYYB}")
    private Boolean enableHYYBService;


    public PagedList<Patient> getPatientList(Integer pageNum, PatientSearchDto patientSearchDto) {
        ExpressionList<Patient> el = ebeanServer.find(Patient.class).where();
        if (patientSearchDto.getName() != null)
            el = el.and().like("name", patientSearchDto.getName() + "%");
        if (patientSearchDto.getIdNumber() != null)
            el = el.and().like("idNumber", patientSearchDto.getIdNumber() + "%");
        el = el.orderBy("whenModified desc").where();
        return findPagedList(el, pageNum);
    }

    public UUID savePatient(PatientDto patientDto) {
        Patient patient = patientDto.toEntity();
        if (patient.getUuid() == null) {
            ebeanServer.save(patient);
        } else {
            ebeanServer.update(patient);
        }
        return patient.getUuid();
    }

    //创建病人页面初始数据
    public List<DictionaryGroup> getPatientDetailPrams() {
        List<String> groupCodeList = new ArrayList<>();
        groupCodeList.add(configBeanProp.getEthnic());
        groupCodeList.add(configBeanProp.getIdType());
        groupCodeList.add(configBeanProp.getMarriageStatus());
        groupCodeList.add(configBeanProp.getOccupation());
        groupCodeList.add(configBeanProp.getGender());

        return ebeanServer.find(DictionaryGroup.class)
                .where()
                .in("code", groupCodeList)
                .findList();
    }

    //办理入院初始数据
    @Transactional
    public PatientSignInPramsDto getPatientSignInPrams() {
        PatientSignInPramsDto pramsDto = new PatientSignInPramsDto();
        pramsDto.setCareLevelList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getNursingLevel())));
        pramsDto.setInsuranceTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getInsuranceType())));
        pramsDto.setPatientConditionList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getPatientCondition())));
        pramsDto.setSignInMethodList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getSignInMethod())));
        DepartmentFilterDto departmentFilterDto = new DepartmentFilterDto();
        departmentFilterDto.setDepartmentTreatmentType(DepartmentTreatmentType.ward);
        pramsDto.setDepartmentTreatmentList(DtoUtils.toDtoList(this.basicService.getDepartmentList(departmentFilterDto)));
        EmployeeFilter employeeFilter = new EmployeeFilter();
        employeeFilter.setEnabled(true);
        employeeFilter.setUserRoleType(UserRoleType.doctor);
        pramsDto.setEmployeeList(DtoUtils.toDtoList(this.userService.getEmployeeList(employeeFilter)));
        pramsDto.setDrgGroupList(DtoUtils.toDtoList(this.getDrgGroupList(true)));
        pramsDto.setFromHospitalList(DtoUtils.toDtoList(basicService.getFromHospitalList()));
        pramsDto.setMedTypeList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getMedType())));
        //pramsDto.setInsuranceAreaList(DtoUtils.toDtoList(this.basicService.getDictionaryList(configBeanProp.getInsuranceArea())));
        return pramsDto;
    }

    @Transactional
    public PatientSignOutPramsDto getPatientSignOutPrams() {
        PatientSignOutPramsDto pramsDto = new PatientSignOutPramsDto();
        pramsDto.setSignOutReasonList(DtoUtils.toDtoList(basicService.getDictionaryList(configBeanProp.getSignOutReason())));
        DepartmentFilterDto departmentFilterDto = new DepartmentFilterDto();
        departmentFilterDto.setDepartmentTreatmentType(DepartmentTreatmentType.ward);
        pramsDto.setDepartmentTreatmentList(DtoUtils.toDtoList(this.basicService.getDepartmentList(departmentFilterDto)));
        EmployeeFilter employeeFilter = new EmployeeFilter();
        employeeFilter.setEnabled(true);
        employeeFilter.setUserRoleType(UserRoleType.doctor);
        pramsDto.setEmployeeList(DtoUtils.toDtoList(this.userService.getEmployeeList(employeeFilter)));
        return pramsDto;
    }

    @Transactional
    public PatientSignIn savePatientSignIn(PatientSignInSaveReqDto patientSignInDto) {
        PatientSignIn patientSignIn = null;
        DepartmentTreatment existingDepartment = null;
        if (patientSignInDto.getUuid() != null) {
            patientSignIn = ebeanServer.find(PatientSignIn.class, patientSignInDto.getUuid());
            existingDepartment = patientSignIn.getDepartmentTreatment();
        } else
            patientSignIn = new PatientSignIn();
        patientSignInDto.copyValue(patientSignIn);

        this.userService.validateDoctorAgreement(patientSignIn.getDoctor());
        //if (patientSignIn.getStatus() == PatientSignInStatus.signedOut || patientSignIn.getStatus() == PatientSignInStatus.pendingSignOut)
        if (patientSignIn.getStatus() == PatientSignInStatus.signedOut)
            throw new ApiValidationException("非法的转住院状态");
        //如果为新记录，COPY数据库生成入院号
        List<PatientSignInStatus> invalidStatusList = new ArrayList<>();
        invalidStatusList.add(PatientSignInStatus.signedOut);
        invalidStatusList.add(PatientSignInStatus.canceled);
        if (patientSignInDto.getUuid() == null) {
            Optional<PatientSignIn> existingSignIn = ebeanServer.find(PatientSignIn.class).where()
                    .eq("patient.uuid", patientSignInDto.getPatientId())
                    .notIn("status", invalidStatusList)
                    .findOneOrEmpty();
            if (existingSignIn.isPresent())
                throw new ApiValidationException("此病人已经入院");

            patientSignIn.setStatus(PatientSignInStatus.pendingSignIn);
            ebeanServer.save(patientSignIn);
            patientSignIn = this.findById(PatientSignIn.class, patientSignIn.getUuid());
            patientSignIn.setSignInNumberCode(Utils.buildDisplayCode(patientSignIn.getSignInNumber()));
        } else {
            if (existingDepartment != null && existingDepartment != patientSignIn.getDepartmentTreatment())
                this.changeDepartment(patientSignIn, existingDepartment);

        }
        ebeanServer.update(patientSignIn);

        if (patientSignInDto.getUuid() != null) //如果为新记录，已经从数据库读取过，这里只读取更新的数据
            patientSignIn = this.findById(PatientSignIn.class, patientSignIn.getUuid());


        if (patientSignIn.getStatus() == PatientSignInStatus.signedIn) //更新医保入院信息
        {
            if (patientSignIn.getYbSignIn() != null) {
                if (this.enableYBService)
                    this.ybService.updateSignInInfo(patientSignIn);
                else if (this.enableHYYBService)
                    this.ybServiceHY.updateSignInInfo(patientSignIn);
            } else if (!patientSignIn.selfPay() && this.enableHYYBService)
                this.ybServiceHY.yBSignIn(patientSignIn);
        }

        //this.ybService.savePatientSignIn(patientSignIn, patientSignInDto);

        return patientSignIn;
    }

    private void changeDepartment(PatientSignIn patientSignIn, DepartmentTreatment existingDepartment) {
        if (patientSignIn.getStatus() != PatientSignInStatus.signedIn && patientSignIn.getStatus() != PatientSignInStatus.pendingSignIn)
            throw new ApiValidationException("当前病人入院状态不允许专科");
        PatientSignInDepartmentChange departmentChange = new PatientSignInDepartmentChange();
        departmentChange.setPatientSignIn(patientSignIn);
        departmentChange.setFromDepartment(existingDepartment);
        departmentChange.setToDepartment(patientSignIn.getDepartmentTreatment());
        ebeanServer.save(departmentChange);
        this.disableAutoFeeAndPrescription(patientSignIn);
        //this.signOutCurrentBed(patientSignIn.getUuid());
    }

    public PatientSignIn getPatientSignIn(UUID patientSignInId) {
        return this.findById(PatientSignIn.class, patientSignInId);
    }

    public PagedList<PatientSignInView> getPatientSignInList(Integer pageNum, PatientSignInFilterDto patientSignInFilter) {
        ExpressionList<PatientSignInView> el = ebeanServer.find(PatientSignInView.class).orderBy("whenModified desc").where();

        if (patientSignInFilter.getSearchCode() != null) {
            el = Utils.addSearchExpression(el, patientSignInFilter.getSearchCode(), "patient.name", "signInNumberCode");
        }
        if (patientSignInFilter.getStatusList() != null)
            el = el.in("status", patientSignInFilter.getStatusList());

        if (patientSignInFilter.getDepartmentIdList() != null)
            el = el.in("departmentTreatment.uuid", patientSignInFilter.getDepartmentIdList());

        if (patientSignInFilter.getInsuranceTypeList() != null && patientSignInFilter.getInsuranceTypeList().size() > 0)
            el = el.in("insuranceType.name", patientSignInFilter.getInsuranceTypeList());

        if (patientSignInFilter.getPendingUploadFee() != null) {
            el = el.gt("pendingFeeCount", 0);
        }

        if (patientSignInFilter.getStartDate() != null && patientSignInFilter.getEndDate() != null) {
            el = el.between("signInDate", patientSignInFilter.getStartDate(), patientSignInFilter.getEndDate());
        }

        return findPagedList(el, pageNum);
    }

    @Transactional
    public PatientSignIn confirmSignIn(UUID signInId, ClientIpInfo clientIpInfo) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, signInId);
        if (patientSignIn.getStatus() != PatientSignInStatus.pendingSignIn)
            throw new ApiValidationException("not.valid.sign.in.status");
        patientSignIn.setStatus(PatientSignInStatus.signedIn);
        //patientSignIn.setSignInDate(new Date());
        ebeanServer.update(patientSignIn);

        //判断是否为医保
        if (!patientSignIn.selfPay()) {
            if (this.enableYBService)
                this.ybService.yBSignIn(patientSignIn, clientIpInfo);
            else if (this.enableHYYBService) {
                //this.ybServiceHY.insuTypeCheck(patientSignIn);
                this.ybServiceHY.yBSignIn(patientSignIn);
            }
        }
//
//        else
//            this.ybService.selfSignIn(patientSignIn);
        return patientSignIn;
    }

    @Transactional
    public PatientSignIn cancelSignIn(UUID signInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, signInId);
        //Todo 需改变下面的检查逻辑为是否有费用产生
        this.checkWithPharmacy(patientSignIn);
        if (patientSignIn.getFeeList().stream().anyMatch(f -> f.getFeeStatus() == FeeStatus.confirmed))
            throw new ApiValidationException("已经产生费用，无法取消");

        this.signOutCurrentBed(signInId);

        patientSignIn.setStatus(PatientSignInStatus.canceled);
        if (patientSignIn.getSignInDate() == null)
            patientSignIn.setSignInDate(new Date());
        //patientSignIn.setSignOutDate(new Date());
        ebeanServer.update(patientSignIn);

        if (this.enableHYYBService && !patientSignIn.selfPay()) {
            if (patientSignIn.getYbSignIn() != null)
                this.ybServiceHY.cancelYBSignIn(patientSignIn);
        }
        return patientSignIn;
    }


    @Transactional
    public UUID assignPatientSignInBed(PatientSignInAssignBedDto signInBed) {
        WardRoomBed newBed = this.findById(WardRoomBed.class, signInBed.getBedId());
        if (newBed.getCurrentSignIn() != null)
            throw new ApiValidationException("signIn.error.bed.occupied");

        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, signInBed.getPatientSignInId());
        if (patientSignIn.getDepartmentTreatment().getWardList().stream()
                .noneMatch(w -> w.getUuid().equals(newBed.getWardRoom().getWard().getUuid())))
            throw new ApiValidationException("signIn.error.bed.invalidWard");


        this.signOutCurrentBed(signInBed.getPatientSignInId());

        //分配新床位

        newBed.setCurrentSignIn(patientSignIn);
        patientSignIn.setCurrentBed(newBed);
        ebeanServer.update(patientSignIn);
        ebeanServer.update(newBed);

        //记录转床日志
        PatientSignInBed newPatientSignInBed = signInBed.toEntity();
        ebeanServer.save(newPatientSignInBed);

        if (!patientSignIn.selfPay()) {
            if (this.enableYBService)
                this.ybService.updateSignInInfo(patientSignIn);
            else if (this.enableHYYBService)
                this.ybServiceHY.updateSignInInfo(patientSignIn);
        }
        return newPatientSignInBed.getUuid();
    }

    public void signOutCurrentBed(UUID patientSignInId) {
        //检查此病人是否已经分配床位，若已经分配，则需空出已有床位
        // PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        Optional<PatientSignInBed> existingSignInBed =
                ebeanServer.find(PatientSignInBed.class)
                        .where().and()
                        .eq("patientSignIn.uuid", patientSignInId)
                        .and()
                        .eq("endDate", null)
                        .findOneOrEmpty();

        if (existingSignInBed.isPresent()) {
            PatientSignInBed patientCurrentBed = existingSignInBed.get();
            PatientSignIn patientSignIn = patientCurrentBed.getPatientSignIn();
            WardRoomBed currentBed = patientCurrentBed.getWardRoomBed();
            patientSignIn.setCurrentBed(null);
            patientCurrentBed.setEndDate(new Date());
            currentBed.setCurrentSignIn(null);
            ebeanServer.save(patientSignIn);
            ebeanServer.update(currentBed);
            ebeanServer.update(patientCurrentBed);

        }
        //检查结束
    }

//    public List<Ward> getWardList() {
//        List<Ward> wardList = ebeanServer.find(Ward.class)
//                .fetch("roomList.bedList")
//                .orderBy("order, roomList.order, roomList.bedList.order")
//                .findList();
//
//        return wardList;
//    }

    public List<Ward> getCurrentPatientWardList() {
        WardFilterDto filter = new WardFilterDto();
        filter.setHideEmptyBed(true);
        return this.getWardList(filter);
    }

    public List<Ward> getWardList(WardFilterDto wardFilter) {
        ExpressionList<Ward> el = ebeanServer.find(Ward.class)
                .fetch("roomList.bedList")
                .orderBy("order, roomList.order, roomList.bedList.order")
                .where();

        if (wardFilter.getWardIdList() != null)
            el = el.in("uuid", wardFilter.getWardIdList());

        if (wardFilter.getHideEmptyBed() && wardFilter.getSearchCode() == null)
            el = el.ne("roomList.bedList.currentSignIn.uuid", null)
                    .filterMany("roomList.bedList.currentSignIn")
                    .ne("uuid", null)
                    //.eq("status", PatientSignInStatus.signedIn)
                    .filterMany("roomList.bedList")
                    .ne("currentSignIn.uuid", null)
                    //.eq("currentSignIn.status", PatientSignInStatus.signedIn)
                    .filterMany("roomList")
                    .ne("bedList.currentSignIn.uuid", null);
        //.eq("bedList.currentSignIn.status", PatientSignInStatus.signedIn);

        if (wardFilter.getSearchCode() != null)
            el = el.contains("roomList.bedList.currentSignIn.patient.name", wardFilter.getSearchCode())
                    .filterMany("roomList.bedList.currentSignIn")
                    .contains("patient.name", wardFilter.getSearchCode())
                    .filterMany("roomList.bedList")
                    .contains("currentSignIn.patient.name", wardFilter.getSearchCode())
                    //.eq("currentSignIn.status", PatientSignInStatus.signedIn)
                    .filterMany("roomList")
                    .contains("bedList.currentSignIn.patient.name", wardFilter.getSearchCode());
        return el.findList();
    }

    public NursingRecordRespDto saveNursingRecord(NursingRecordSaveDto nursingRecordSaveDto) {
        NursingRecord nursingRecord = nursingRecordSaveDto.toEntity();
        if (nursingRecord.getUuid() == null) {
            ebeanServer.save(nursingRecord);
        } else {
            ebeanServer.update(nursingRecord);
        }
        return this.findById(NursingRecord.class, nursingRecord.getUuid()).toDto();
    }

    @Transactional
    public List<TempRecordRespDto> saveTempRecordList(List<TempRecordSaveDto> tempRecordSaveDtoList) {
        List<TempRecordRespDto> tempRecordRespDtoList = new ArrayList<>();
        for (TempRecordSaveDto tempRecordSaveDto : tempRecordSaveDtoList)
            tempRecordRespDtoList.add(this.saveTempRecord(tempRecordSaveDto));
        return tempRecordRespDtoList;
    }

    public TempRecordRespDto saveTempRecord(TempRecordSaveDto tempRecordSaveDto) {
        TempRecord tempRecord = tempRecordSaveDto.toEntity();
        if (tempRecord.getUuid() == null) {
            ebeanServer.save(tempRecord);
        } else {
            ebeanServer.update(tempRecord);
        }
        return this.findById(TempRecord.class, tempRecord.getUuid()).toDto();
    }

    public List<NursingRecord> getNursingRecordList(UUID patientSignInId, NursingRecordFilter filter) {
        ExpressionList<NursingRecord> nursingRecordExpressionList =
                ebeanServer.find(NursingRecord.class).where()
                        .eq("patientSignIn.uuid", patientSignInId);
        if (filter.getStartDate() != null && filter.getEndDate() != null)
            nursingRecordExpressionList = nursingRecordExpressionList.between("recordDate", filter.getStartDate(), filter.getEndDate());

        return nursingRecordExpressionList.order("recordDate desc").findList();
    }

    public List<TempRecord> getTempRecordList(UUID patientSignInId, TempRecordFilter filter) {
        ExpressionList<TempRecord> tempRecordExpressionList =
                ebeanServer.find(TempRecord.class).where()
                        .eq("patientSignIn.uuid", patientSignInId);
        if (filter.getStartDate() != null && filter.getEndDate() != null)
            tempRecordExpressionList = tempRecordExpressionList.between("recordDate", filter.getStartDate(), filter.getEndDate());

        List<TempRecord> tempRecordList = tempRecordExpressionList.order("recordDate desc").findList();
        return tempRecordList;
    }

    public NursingRecordRespDto getNursingRecord(UUID uuid) {
        return this.findById(NursingRecord.class, uuid).toDto();
    }

    public TempRecordRespDto getTempRecord(UUID uuid) {
        return this.findById(TempRecord.class, uuid).toDto();
    }

    public List<TempRecord> getTempRecordByWeekList(UUID patientSignInId, List<LocalDateTime> weekDateList) {
        TempRecordFilter filter = new TempRecordFilter();
        filter.setStartDate(Date.from(weekDateList.get(0).atZone(ZoneId.systemDefault()).toInstant()));
        filter.setEndDate(Date.from(weekDateList.get(weekDateList.size() - 1).atZone(ZoneId.systemDefault()).toInstant()));
        return this.getTempRecordList(patientSignInId, filter);
    }

    public List<LocalDateTime> getPatientSignInWeekDate(UUID patientSignInId, Integer weekNumber) {
        List<LocalDateTime> weekDateList = new ArrayList<>();
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        LocalDate localSignInDate = LocalDateTime.ofInstant(patientSignIn.getSignInDate().toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localWeekStartDate = localSignInDate.plusDays(weekNumber * 7);
        for (int i = 0; i <= 7; i++) {
            weekDateList.add(localWeekStartDate.plusDays(i).atTime(LocalTime.MIN));
        }
        return weekDateList;
    }


    public void deleteTempRecord(UUID uuid) {
        ebeanServer.delete(TempRecord.class, uuid);
    }

    public void deleteNursingRecord(UUID uuid) {
        ebeanServer.delete(NursingRecord.class, uuid);
    }

    @Transactional
    public PatientSignOutRequest saveNewPatientSignOutRequest(PatientSignOutRequestSaveDto patientSignOutRequestSaveDto) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignOutRequestSaveDto.getPatientSignInId());
        //this.validatePatientSignInStatus(patientSignIn, PatientSignInStatus.signedIn);


        Date existingSignOutDate = null;
        if (patientSignIn.getSingOutRequest() != null)
            existingSignOutDate = patientSignIn.getSingOutRequest().getSignOutDate();

        PatientSignOutRequest request = patientSignOutRequestSaveDto.toEntity();

        Boolean updatePrescription = false;
        if (existingSignOutDate != null && existingSignOutDate.compareTo(request.getSignOutDate()) != 0)
            updatePrescription = true;

//        List<PatientSignOutStatus> validStatusList = new ArrayList<>();
//        validStatusList.add(PatientSignOutStatus.created);
//        validStatusList.add(PatientSignOutStatus.validation);
//        validStatusList.add(PatientSignOutStatus.validationCompleted);
//        if (!validStatusList.contains(request.getStatus()))
//            throw new ApiValidationException("当前出院状态不允许删除");

        if (patientSignIn.getSingOutRequest() != null && request.getUuid() == null)
            throw new ApiValidationException("已经有出院信息存在");
        ebeanServer.save(request);


        if (updatePrescription) {
            this.updateSignOutPrescription(request);
        }
        return request;
    }

    private void updateSignOutPrescription(PatientSignOutRequest request) {
        Prescription signOutPrescription = request.getSignOutPrescription();
        if (signOutPrescription != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            signOutPrescription.setReference("出院时间：" + sdf.format(request.getSignOutDate()));
            signOutPrescription.setDescription(signOutPrescription.getReference());
            ebeanServer.save(signOutPrescription);
        }
    }


    @Transactional
    public PatientSignOutRequest settleSignOutRequest(UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.findById(PatientSignOutRequest.class, signOutRequestId);
        PatientSignIn patientSignIn = signOutRequest.getPatientSignIn();
        if (signOutRequest.getStatus() != PatientSignOutStatus.validationCompleted)
            throw new ApiValidationException("非法的出院状态");
        this.signOutFeeCheck(patientSignIn);
        this.signOutPrescriptionCheck(patientSignIn);
        //this.disableAutoFeeAndPrescription(patientSignIn);
        signOutRequest.setStatus(PatientSignOutStatus.pendingPayment);

        patientSignIn.setStatus(PatientSignInStatus.pendingSignOut);
        //空出床位
        this.signOutCurrentBed(patientSignIn.getUuid());

        //更新出院医嘱的内容
        //signOutRequest.setSignOutDate(new Date());
        this.updateSignOutPrescription(signOutRequest);

        ebeanServer.save(signOutRequest);
        ebeanServer.save(patientSignIn);


        if (this.enableYBService)
            this.ybService.signOut(patientSignIn);
        if (this.enableHYYBService && !patientSignIn.selfPay())
            this.ybServiceHY.signOut(patientSignIn);
        this.notificationService.patientSignOutRequest(patientSignIn);
        return signOutRequest;
    }


    @Transactional
    public PatientSignOutRequest validateSignOutRequestComplete(UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.findById(PatientSignOutRequest.class, signOutRequestId);
        PatientSignIn patientSignIn = signOutRequest.getPatientSignIn();
        if (signOutRequest.getStatus() != PatientSignOutStatus.validation)
            throw new ApiValidationException("非法的出院状态");
        //停用出院医嘱
        Prescription signOutPrescription = signOutRequest.getSignOutPrescription();
        if (signOutPrescription.getStatus() == PrescriptionStatus.submitted)
            throw new ApiValidationException("请先复核出院医嘱");

//        PrescriptionChangeStatusReqDto reqDto = new PrescriptionChangeStatusReqDto();
//        List<UUID> prescriptionIdList = new ArrayList<>();
//        prescriptionIdList.add(signOutRequest.getSignOutPrescription().getUuid());
//        reqDto.setPrescriptionIdList(prescriptionIdList);
//        this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.approved, PrescriptionStatus.disabled, PrescriptionChangeAction.signOut, reqDto);

        this.signOutFeeCheck(patientSignIn);
        signOutRequest.setStatus(PatientSignOutStatus.validationCompleted);
        ebeanServer.save(signOutRequest);
        return signOutRequest;
    }

    @Transactional
    public void disableAllPrescription(UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.findById(PatientSignOutRequest.class, signOutRequestId);
        PatientSignIn patientSignIn = signOutRequest.getPatientSignIn();
//        if (signOutRequest.getStatus() != PatientSignOutStatus.validationCompleted)
//            throw new ApiValidationException("非法的出院状态");

        this.disableAutoFeeAndPrescription(patientSignIn);
    }

    private void signOutPrescriptionCheck(PatientSignIn patientSignIn) {
        Integer prescriptionCheckCount = ebeanServer.find(Prescription.class).where()
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .eq("status", PrescriptionStatus.submitted)
                .findCount();
        if (prescriptionCheckCount > 0)
            throw new ApiValidationException("有提交中的医嘱");

        prescriptionCheckCount = ebeanServer.find(Prescription.class).where()
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .eq("status", PrescriptionStatus.approved)
                .findCount();
        if (prescriptionCheckCount > 0)
            throw new ApiValidationException("有执行中的医嘱");

        prescriptionCheckCount = ebeanServer.find(Prescription.class).where()
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .eq("status", PrescriptionStatus.pendingDisable)
                .findCount();
        if (prescriptionCheckCount > 0)
            throw new ApiValidationException("有待确认停止的医嘱");
    }


    private void signOutFeeCheck(PatientSignIn patientSignIn) {
        if (this.enableYBService) {
            if (this.ybService.anyPendingUploadFee(patientSignIn.getUuid()))
                throw new ApiValidationException("signIn.error.signOutRequest.notUploadedFee");

            if (this.ybInventoryService.anyPendingUploadFeeInventory(patientSignIn.getUuid()))
                throw new ApiValidationException("存在关于费用的未上传的耗材或药品库存信息，请尝试重新上传医保费用。");
        }


        if (!patientSignIn.selfPay()) {
            if (this.ybService.anyPendingUploadFee(patientSignIn.getUuid()))
                throw new ApiValidationException("signIn.error.signOutRequest.notUploadedFee");

            if (patientSignIn.getPreSettlementHY() == null)
                throw new ApiValidationException("signIn.error.signOutRequest.noPreSettlement");

//            ViewFeeSummary patientFeeInfo = Ebean.find(ViewFeeSummary.class).where().eq("patientSignInId", patientSignIn.getUuid()).findOne();
//            if (patientSignIn.getPreSettlementHY().getMedfee_sumamt().compareTo(patientFeeInfo.getTotalAmount()) != 0)
//                throw new ApiValidationException("医保预结算费用与系统记录不符合，请尝试重新预结");

//            //有没有费用未从中心下载
//            if (!this.ybService.allFeeDownloadedFromYB(patientSignIn.getUuid()))
//                throw new ApiValidationException("有未从医保中心下载的费用，请下载所有的医保中心费用用以核对");
//
//            if (!this.ybService.allFeeValidFromYB(patientSignIn.getUuid()))
//                throw new ApiValidationException("中心费用明细核对出错，请打开医保对账功能，并检查有问题的明细费用");
        }

        this.checkWithPharmacy(patientSignIn);
    }

    private void checkWithPharmacy(PatientSignIn patientSignIn) {
        //查找是否有未处理的领药单
        int pendingMedicineOrderLine = ebeanServer.find(PrescriptionMedicineOrderLine.class).where()
                .or()
                .eq("status", PrescriptionMedicineOrderLineStatus.pending)
                .eq("status", PrescriptionMedicineOrderLineStatus.pendingConfirm)
                .endOr()
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .findCount();

        if (pendingMedicineOrderLine > 0)
            throw new ApiValidationException("signIn.error.signOutRequest.pendingMedicineOrder");

        //查找是否有未处理的退药单
        int pendingMedicineReturnOrderLine = ebeanServer.find(PrescriptionMedicineReturnOrderLine.class).where()
                .eq("status", PrescriptionMedicineReturnOrderLineStatus.pending)
                .eq("originOrderLine.patientSignIn.uuid", patientSignIn.getUuid())
                .findCount();
        if (pendingMedicineReturnOrderLine > 0)
            throw new ApiValidationException("signIn.error.signOutRequest.pendingMedicineReturnOrder");
    }


    @Transactional //病区对费
    public PatientSignOutRequest validateSignOutRequest(UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.findById(PatientSignOutRequest.class, signOutRequestId);
        PatientSignIn patientSignIn = signOutRequest.getPatientSignIn();
        if (signOutRequest.getStatus() != PatientSignOutStatus.created)
            throw new ApiValidationException("非法的出院状态");

        //this.disableAutoFeeAndPrescription(patientSignIn);


        signOutRequest.setSignOutPrescription(this.createSignOutPrescription(patientSignIn));
        signOutRequest.setStatus(PatientSignOutStatus.validation);
        ebeanServer.save(signOutRequest);
        ebeanServer.save(patientSignIn);
        return signOutRequest;
    }

    private void disableAutoFeeAndPrescription(PatientSignIn patientSignIn) {
        //停止自动计费
        List<AutoFee> autoFeeList = ebeanServer.find(AutoFee.class).where()
                .eq("enabled", true)
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .findList();

        for (AutoFee autoFee : autoFeeList)
            this.accountService.enableAutoFee(autoFee.getUuid(), false);

        //停止非医疗自动计费
        List<lukelin.his.domain.entity.Internal_account.AutoFee> internalAutoFeeList = ebeanServer.find(lukelin.his.domain.entity.Internal_account.AutoFee.class).where()
                .eq("enabled", true)
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .findList();
        for (lukelin.his.domain.entity.Internal_account.AutoFee internalAutoFee : internalAutoFeeList) {
            internalAutoFee.setEnabled(false);
            ebeanServer.save(internalAutoFee);
        }

        //处理未停止或者作废的医嘱
        this.processPrescription(patientSignIn, PrescriptionStatus.approved, PrescriptionStatus.pendingDisable);
        this.processPrescription(patientSignIn, PrescriptionStatus.created, PrescriptionStatus.deleted);
        //this.processPrescription(patientSignIn, PrescriptionStatus.submitted, PrescriptionStatus.deleted);
    }


    @Transactional
    public Prescription createSignOutPrescription(PatientSignIn patientSignIn) {
        Prescription prescription = new Prescription();
        prescription.setPatientSignIn(patientSignIn);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date signOutDate = patientSignIn.getSingOutRequest().getSignOutDate();
        prescription.setReference("出院时间：" + sdf.format(signOutDate));
        prescription.setDescription(prescription.getReference());
        prescription.setPrescriptionType(PrescriptionType.Text);
        prescription.setStatus(PrescriptionStatus.created);
        prescription.setDepartment(patientSignIn.getDepartmentTreatment());
        prescription.setOneOff(true);
        ebeanServer.save(prescription);
        PrescriptionChangeStatusReqDto reqDto = new PrescriptionChangeStatusReqDto();
        List<UUID> prescriptionIdList = new ArrayList<>();
        prescriptionIdList.add(prescription.getUuid());
        reqDto.setPrescriptionIdList(prescriptionIdList);
        this.prescriptionService.updatePrescriptionStatus(PrescriptionStatus.created, PrescriptionStatus.submitted, PrescriptionChangeAction.submit, reqDto);
        List<Prescription> prescriptionList = new ArrayList<>();
        prescriptionList.add(prescription);
        //this.notificationService.prescriptionSubmitted(prescriptionList);
        return prescription;

    }

    @Transactional
    public void deleteSignOutRequest(UUID signOutRequestId) {
        PatientSignOutRequest signOutRequest = this.findById(PatientSignOutRequest.class, signOutRequestId);
        List<PatientSignOutStatus> validStatusList = new ArrayList<>();
        validStatusList.add(PatientSignOutStatus.created);
        validStatusList.add(PatientSignOutStatus.validation);
        validStatusList.add(PatientSignOutStatus.validationCompleted);
        if (!validStatusList.contains(signOutRequest.getStatus()))
            throw new ApiValidationException("当前出院状态不允许删除");
        signOutRequest.getDiagnoseSet().clear();
        ebeanServer.delete(signOutRequest);
    }

    private void processPrescription(PatientSignIn patientSignIn, PrescriptionStatus currentStatus, PrescriptionStatus toStatus) {
        List<Prescription> prescriptionList = ebeanServer.find(Prescription.class).where()
                .eq("status", currentStatus)
                .eq("patientSignIn.uuid", patientSignIn.getUuid())
                .findList();

        List<Prescription> pendingDisablePrescriptionList = new ArrayList<>();
        for (Prescription prescription : prescriptionList) {
            PrescriptionStatus prescriptionToStatus = toStatus;
            if (prescriptionToStatus == PrescriptionStatus.pendingDisable) {
                if (prescription.isOneOff())
                    prescriptionToStatus = PrescriptionStatus.disabled; //临时医嘱直接停嘱。
                else
                    pendingDisablePrescriptionList.add(prescription);
            }
            this.prescriptionService.doPrescriptionUpdate(prescription, prescriptionToStatus, PrescriptionChangeAction.signOut);
        }

//        if (pendingDisablePrescriptionList.size() > 0)
//            this.notificationService.addPendingDisablePrescriptionNotification(prescriptionList);
    }

    @Transactional
    public PatientSignIn cancelSignOutRequest(UUID patientSignInId) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        PatientSignOutRequest pendingRequest = patientSignIn.getSingOutRequest();
        if (pendingRequest.getStatus() != PatientSignOutStatus.pendingPayment)
            throw new ApiValidationException("非法的出院状态");

        if (patientSignIn.getStatus() != PatientSignInStatus.pendingSignOut)
            throw new ApiValidationException("非法的住院状态");

        patientSignIn.setStatus(PatientSignInStatus.signedIn);
        pendingRequest.setStatus(PatientSignOutStatus.validation);
        ebeanServer.save(pendingRequest);
        ebeanServer.save(patientSignIn);
        return patientSignIn;
    }

    void validatePatientSignInStatus(PatientSignIn patientSignIn, PatientSignInStatus status) {
        if (patientSignIn.getStatus() != status)
            throw new ApiValidationException("signIn.error.signOutRequest.invalidSignInStatus");
    }

    void validatePatientSignInStatus(UUID patientSignInId, PatientSignInStatus status) {
        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        this.validatePatientSignInStatus(patientSignIn, status);
    }

    @Transactional
    public PatientSignIn confirmSignOutRequest(UUID patientSignInId) {
        //this.signOutCurrentBed(patientSignInId);

        PatientSignIn patientSignIn = this.findById(PatientSignIn.class, patientSignInId);
        PatientSignOutRequest pendingRequest = patientSignIn.getSingOutRequest();
        //if (pendingRequest.getStatus() != PatientSignOutStatus.pendingPayment || patientSignIn.getStatus() != PatientSignInStatus.pendingSignOut)
//            throw new ApiValidationException("非法的出院状态");


        pendingRequest.setStatus(PatientSignOutStatus.signedOut);
        patientSignIn.setStatus(PatientSignInStatus.signedOut);
        ebeanServer.save(pendingRequest);
        ebeanServer.save(patientSignIn);

        if (!patientSignIn.selfPay() && this.enableYBService)
            this.ybService.confirmFinalSettlement(patientSignIn);
        return patientSignIn;
    }

    @Transactional
    public List<MedicalRecord> getMedicalRecordList(UUID patientSignInId, UUID typeId, UUID templateId) {
        ExpressionList<MedicalRecord> el = ebeanServer.find(MedicalRecord.class)
                .select("uuid, name, type, patientSignIn, template, inEdit, inEditBy, inEditWhen, whenModified, whoCreated, whoModified, whenCreated")
                .orderBy("whenCreated desc")
                .where()
                .eq("patientSignIn.uuid", patientSignInId);
        if (typeId != null)
            el = el.eq("type.uuid", typeId);
        if (templateId != null)
            el = el.eq("template.uuid", templateId);

        List<MedicalRecord> medicalRecordList = el.findList();
        MedicalRecordType medicalRecordType = this.findById(MedicalRecordType.class, typeId);
        if (medicalRecordType.isFixedFormat()) {
            List<MedicalRecordTemplate> pendingCreatedTemplateList = medicalRecordType.getTemplateList().stream()
                    .filter(t -> medicalRecordList.stream().noneMatch(m -> m.getTemplate().getUuid().equals(t.getUuid()))).collect(Collectors.toList());
            for (MedicalRecordTemplate template : pendingCreatedTemplateList)
                medicalRecordList.add(template.toMockMedicalRecord(this.findById(PatientSignIn.class, patientSignInId)));
        }
        return medicalRecordList;
    }

    @Transactional
    public MedicalRecordDto saveMedicalRecord(MedicalRecordSaveDto saveDto) {
        MedicalRecord medicalRecord = saveDto.toEntity();
        //检查固定格式Template是否已经存在,只能生成一份病历
        if (medicalRecord.getType().isFixedFormat() && medicalRecord.getUuid() == null) {
            Optional<MedicalRecord> existingRecord = ebeanServer.find(MedicalRecord.class).where()
                    .eq("", saveDto.getPatientSignInId())
                    .eq("", saveDto.getTemplateId())
                    .findOneOrEmpty();
            if (existingRecord.isPresent())
                throw new ApiValidationException("patientSignIn.medicalRecord.type.already.exists");
        }

        if (medicalRecord.getUuid() != null)
            ebeanServer.update(medicalRecord);
        else
            ebeanServer.save(medicalRecord);

        return medicalRecord.toDto();
    }

    public List<PatientMedicalRecordCount> getPatientMedicalRecordCountList(UUID patientSignInId) {
        return ebeanServer.find(PatientMedicalRecordCount.class)
                .where()
                .eq("patientSignInId", patientSignInId)
                .findList();
    }

    public List<PatientMedicalRecordCount> getPatientMedicalRecordListByType(UUID patientSignInId, UUID typeId) {
        return ebeanServer.find(PatientMedicalRecordCount.class)
                .where()
                .eq("patientSignInId", patientSignInId)
                .eq("medicalRecordTypeId", typeId)
                .findList();
    }

    public MedicalRecordTemplate createNewMedicalRecordFromTemplate(NewMedicalRecordPram newMedicalTemplatePram) {
        MedicalRecordTemplate template = ebeanServer.find(MedicalRecordTemplate.class, newMedicalTemplatePram.getTemplateId());
        if (template.getType().isFixedFormat())
            template.setTemplate(this.getReplaceJsonString(template.getTemplate(), newMedicalTemplatePram.getDoctorId(), newMedicalTemplatePram.getPatientSignInId()));
        else
            this.replaceHtmlTemplateReserve(template, newMedicalTemplatePram);
        //template.setTemplate(this.processTemplateHtml(template.getTemplate()));
        return template;
    }

    private String getReplaceJsonString(String jsonString, UUID doctorId, UUID patientSignInId) {
        List<PatientTemplateTagValue> tagValueList = this.getAllTagValueList(doctorId, patientSignInId);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String replaceKey = "replaceKey";
        String valueKey = "value";
        for (Map.Entry<String, Object> mapEntry : jsonObject.entrySet()) {
            if (((Map) mapEntry.getValue()).size() == 0)
                continue;
            Map<String, String> inputMap = (Map) mapEntry.getValue();
            if (inputMap.containsKey(replaceKey)) {
                Optional<PatientTemplateTagValue> optionalTagValue = tagValueList.stream().filter(t -> t.getTagName().equals(inputMap.get(replaceKey))).findFirst();
                if (optionalTagValue.isPresent()) {
                    if (inputMap.containsKey(valueKey))
                        inputMap.replace(valueKey, optionalTagValue.get().getTagValue());
                    else
                        inputMap.put(valueKey, optionalTagValue.get().getTagValue());
                }
            }
        }
        return JSON.toJSONString(jsonObject);
    }

    private void replaceHtmlTemplateReserve(MedicalRecordTemplate template, NewMedicalRecordPram newMedicalTemplatePram) {
        List<PatientTemplateTagValue> tagValueList = this.getAllTagValueList(newMedicalTemplatePram.getDoctorId(), newMedicalTemplatePram.getPatientSignInId());
        Document doc = Jsoup.parse(template.getTemplate());
        Elements inputElementList = doc.select("input");

        for (Element element : inputElementList) {
            String inputValue = element.attr("value");
            String finalInputValue = inputValue;
            Optional<PatientTemplateTagValue> optionalTagValue = tagValueList.stream().filter(t -> t.getTagName().equals(finalInputValue)).findAny();
            if (optionalTagValue.isPresent())
                if (optionalTagValue.get().getTagValue() != null)
                    inputValue = optionalTagValue.get().getTagValue();
                else
                    inputValue = "";
            element.replaceWith(new Element(Tag.valueOf("span"), "").html(inputValue));
        }
        template.setTemplate(doc.body().html());
    }


//    public String processTemplateHtml(String processTemplateHtml) {
//        Document doc = Jsoup.parse(processTemplateHtml);
//        Elements inputElementList = doc.select("input");
//
//        Integer i = 1;
//        for (Element element : inputElementList) {
//            String classValue = "{'replaceableInput': this.formData.input" + i + ".replaceKey && this.showInputWithReplaceKey}";
//            element.attr("[ngClass]", classValue);
//            i++;
//        }
//        return doc.body().html();
//    }

    private List<PatientTemplateTagValue> getAllTagValueList(UUID doctorId, UUID patientSignInId) {
        String sql = "select tagName,tagValue from patient_sign_in.patient_tag_value(?, ?)";
        RawSql rawSql = RawSqlBuilder
                .parse(sql)
                .columnMapping("tagName", "tagName")
                .columnMapping("tagValue", "tagValue")
                .create();
        return ebeanServer.find(PatientTemplateTagValue.class).setRawSql(rawSql)
                .setParameter(1, doctorId)
                .setParameter(2, patientSignInId)
                .findList();
    }

    public String updateMainMedicalRecordSystemValue(MedicalRecordSystemUpdateDto medicalRecordSystemUpdateDto) {
        return this.getReplaceJsonString(medicalRecordSystemUpdateDto.getCurrentPageContent(), medicalRecordSystemUpdateDto.getDoctorId(), medicalRecordSystemUpdateDto.getPatientSignInId());
    }

    @Transactional
    public MedicalRecord lockMedicalRecord(MedicalRecordLockDto lockDto) {
        MedicalRecord medicalRecord = this.getMedicalRecord(lockDto.getMedicalRecordId());
        medicalRecord.setInEdit(true);
        Date lockedTime = new Date();
        medicalRecord.setInEditWhen(lockedTime);
        medicalRecord.setInEditBy(lockDto.getLockedBy());
        ebeanServer.update(medicalRecord);
        //重新读取一次获取最新的更新时间
        return medicalRecord;
    }

    private MedicalRecord getMedicalRecord(UUID medicalRecordId) {
        return ebeanServer.find(MedicalRecord.class)
                .select("uuid, name, type, patientSignIn, template, inEdit, inEditBy, inEditWhen, whenModified, whoCreated, whoModified, whenCreated")
                .where().eq("uuid", medicalRecordId)
                .findOne();
    }

    @Transactional
    public void unlockMedicalRecord(UUID medicalRecordId) {
        MedicalRecord medicalRecord = this.getMedicalRecord(medicalRecordId);
        medicalRecord.setInEdit(false);
        medicalRecord.setInEditWhen(null);
        medicalRecord.setInEditBy(null);
        ebeanServer.update(medicalRecord);
    }

    public MedicalRecordLockedRespDto getLockedInfo(UUID medicalRecordId) {
        MedicalRecord medicalRecord = this.getMedicalRecord(medicalRecordId);
        MedicalRecordLockedRespDto resp = new MedicalRecordLockedRespDto();
        resp.setLockedTime(medicalRecord.getInEditWhen());
        resp.setModifiedBy(medicalRecord.getWhoModified());
        resp.setModifiedWhen(medicalRecord.getWhenModified());
        return resp;
    }

    @Transactional
    public List<MedicalRecordTag> saveNewTag(MedicalRecordTagSaveDto newTagDto) {
        MedicalRecordTag newTag = newTagDto.toEntity();
        ebeanServer.save(newTag);
        return this.getMedicalRecordTagList(newTagDto.getMedicalRecordId());
    }

    @Transactional
    public List<MedicalRecordTag> deletetMedicalRecordTag(UUID tagId) {
        MedicalRecordTag tag = this.findById(MedicalRecordTag.class, tagId);
        MedicalRecord record = tag.getMedicalRecord();
        ebeanServer.delete(tag);
        return record.getTagList().stream().sorted(Comparator.comparing(MedicalRecordTag::getTagTime).reversed()).collect(Collectors.toList());
    }

    public List<MedicalRecordTag> getMedicalRecordTagList(UUID medicalRecordId) {
        return ebeanServer.find(MedicalRecordTag.class).where()
                .eq("medicalRecord.uuid", medicalRecordId)
                .orderBy("tagTime desc")
                .findList();

    }

    public List<DrgGroup> getDrgGroupList(Boolean enabled) {
        ExpressionList<DrgGroup> el = ebeanServer.find(DrgGroup.class).where();
        if (enabled != null)
            el = el.eq("enabled", enabled);
        return el.findList();
    }

    public DrgGroup saveDrgGroup(DrgGroupSaveDto saveDto) {
        DrgGroup drgGroup = saveDto.toEntity();
        ebeanServer.save(drgGroup);
        return drgGroup;
    }

    @Transactional
    public PatientSignIn cloneToNewPatientSignIn(UUID toCloneId) {
        PatientSignIn oldPatientSignIn = this.findById(PatientSignIn.class, toCloneId);
        PatientSignIn newSignIn = new PatientSignIn();
        BeanUtils.copyPropertiesIgnoreNull(this, newSignIn);
        newSignIn.setSignInDate(null);
        newSignIn.setUuid(null);
        newSignIn.setSignInNumber(null);
        newSignIn.setSignInNumberCode(null);
        newSignIn.setStatus(PatientSignInStatus.pendingSignIn);
        newSignIn.setPatient(oldPatientSignIn.getPatient());
        newSignIn.setSignInMethod(oldPatientSignIn.getSignInMethod());
        newSignIn.setNursingLevel(oldPatientSignIn.getNursingLevel());
        newSignIn.setPatientCondition(oldPatientSignIn.getSignInMethod());
        newSignIn.setDepartmentTreatment(oldPatientSignIn.getDepartmentTreatment());
        newSignIn.setDoctor(oldPatientSignIn.getDoctor());
        newSignIn.setInsuranceType(oldPatientSignIn.getInsuranceType());
        Set<Diagnose> diagnoseSet = new HashSet<>();
        diagnoseSet.addAll(oldPatientSignIn.getDiagnoseSet());
        newSignIn.setDiagnoseSet(diagnoseSet);
        newSignIn.setDrgGroup(oldPatientSignIn.getDrgGroup());
        newSignIn.setMedType(oldPatientSignIn.getMedType());
        ebeanServer.save(newSignIn);
        newSignIn = this.findById(PatientSignIn.class, newSignIn.getUuid());
        newSignIn.setSignInNumberCode(Utils.buildDisplayCode(newSignIn.getSignInNumber()));
        ebeanServer.update(newSignIn);
        return newSignIn;
    }

    public PatientSignOutRequest findOneOrEmptySignOutRequest(UUID patientSignInId) {
        Optional<PatientSignOutRequest> optionalPatientSignOutRequest = ebeanServer.find(PatientSignOutRequest.class).where()
                .eq("patientSignIn.uuid", patientSignInId)
                .findOneOrEmpty();

        return optionalPatientSignOutRequest.orElse(null);
    }

    public void copyLastSignInMedicalRecord(UUID patientSignInId, List<UUID> toCloneIdList) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class, patientSignInId);
        List<MedicalRecord> recordList = ebeanServer.find(MedicalRecord.class).where()
                .in("uuid", toCloneIdList)
                .findList();
        List<MedicalRecord> newRecordList = new ArrayList<>();
        for (MedicalRecord medicalRecord : recordList)
            newRecordList.add(medicalRecord.toNewMedicalRecord(patientSignIn));
        ebeanServer.saveAll(newRecordList);
    }

    public UUID getLastSignInId(UUID patientSignInId) {
        PatientSignIn patientSignIn = ebeanServer.find(PatientSignIn.class, patientSignInId);
        Optional<PatientSignIn> lastSignIn =
                patientSignIn.getPatient().getSignInHistoryList().stream()
                        .filter(p -> !p.getUuid().equals(patientSignInId)
                                && p.getStatus() == PatientSignInStatus.signedOut
                        ).max(Comparator.comparing(PatientSignIn::getSignInDate));
        if (!lastSignIn.isPresent()) {
            throw new ApiValidationException("首次入院，无法复制");
        }
        return lastSignIn.get().getUuid();

    }
}
