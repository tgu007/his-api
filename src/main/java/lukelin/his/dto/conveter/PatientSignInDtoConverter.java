package lukelin.his.dto.conveter;

import lukelin.his.domain.entity.basic.template.MedicalRecordType;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.basic.ward.WardRoom;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.patient_sign_in.*;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.PatientSignIn.UrineVolume;
import lukelin.his.dto.mini_porgram.MiniPrescriptionDto;
import lukelin.his.dto.mini_porgram.MiniWardDto;
import lukelin.his.dto.signin.response.*;
import lukelin.his.system.Utils;
import org.apache.tomcat.util.buf.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class PatientSignInDtoConverter {
    public static List<PatientSignInListRespDto> toPatientSignInListRep(List<PatientSignInView> patientSignInList) {
        List<PatientSignInListRespDto> dtoList = new ArrayList<>();
        for (PatientSignInView patientSignIn : patientSignInList) {
            dtoList.add(patientSignIn.toListDto());
        }
        return dtoList;
    }

    public static List<PatientSignInListRespDto> toPatientCheck3024Rep(List<PatientSignInView> patientSignInList) {
        List<PatientSignInListRespDto> dtoList = new ArrayList<>();
        for (PatientSignInView patientSignIn : patientSignInList) {
            dtoList.add(patientSignIn.toCheck3034Dto());
        }
        return dtoList;
    }

    public static List<TreeWardNodeDto> toTreeWardNodeList(List<Ward> wardList) {
        List<TreeWardNodeDto> wardNodeList = new ArrayList<>();
        for (Ward ward : wardList) {
            wardNodeList.add(ward.toTreeWardNodeDto());
        }
        return wardNodeList;
    }

    public static List<BaseWardPatientListRespDto> toWardPatientList(List<Ward> currentPatientList, List<? extends BaseWardPatientListRespDto> baseWardPrescriptionDtoList) {
        List<BaseWardPatientListRespDto> dtoList = new ArrayList<>();
        for (Ward ward : currentPatientList) {
            for (WardRoom room : ward.getRoomList()) {
                for (WardRoomBed bed : room.getBedList()) {
                    Integer groupIndex = 0;
                    List<BaseWardPatientListRespDto> patientBaseWardPrescriptionDtoList = baseWardPrescriptionDtoList.stream().filter(p -> p.getPatientSignInId().equals(bed.getCurrentSignIn().getUuid())).collect(Collectors.toList());
                    if (patientBaseWardPrescriptionDtoList.size() > 0) {
                        for (BaseWardPatientListRespDto patientBaseWardPrescriptionDto : patientBaseWardPrescriptionDtoList) {
                            patientBaseWardPrescriptionDto.setPatientGroupIndex(groupIndex);
                            patientBaseWardPrescriptionDto.setPatientName(bed.getName() + ":" + patientBaseWardPrescriptionDto.getPatientName());
                            patientBaseWardPrescriptionDto.setWardRoom(room.getName());
                            patientBaseWardPrescriptionDto.setWard(ward.getName());
                            dtoList.add(patientBaseWardPrescriptionDto);
                            groupIndex++;
                        }
                    }
                }
            }
        }
        return dtoList;
    }

    public static TempRecordChartRespDto toTempRecordChartRespDto(List<LocalDateTime> weekDateList, List<TempRecord> tempRecordList, Integer weekNumber, PatientSignIn patientSignIn) {
        TempRecordChartRespDto dto = new TempRecordChartRespDto();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat hf = new SimpleDateFormat("HH");
        DateFormat mf = new SimpleDateFormat("mm");
        if (weekNumber == 0) {
            // String inDateChinese = Utils.getChineseDate(df.format(patientSignIn.getSignInDate()));
            String hourChinese = Utils.numberToChineseSimple(hf.format(patientSignIn.getSignInDate()), false);
            hourChinese = formatChineseNumber(hourChinese, false);

            String minuteChinese = Utils.numberToChineseSimple(mf.format(patientSignIn.getSignInDate()), false);
            minuteChinese = formatChineseNumber(minuteChinese, true);
            String inFullChinese = addLineBreak("入院" + hourChinese + "时" + minuteChinese + "分");
            dto.setSignInDate(PatientSignInDtoConverter.toEchartValue(patientSignIn.getSignInDate(), inFullChinese, weekDateList.get(0).toLocalDate()));
        }

        if (patientSignIn.getSingOutRequest() != null) {
            Integer signedInWeekNumber = patientSignIn.getPatientSignInWeeks();
            if (signedInWeekNumber == weekNumber) {
                Date signOutDate = patientSignIn.getSingOutRequest().getSignOutDate();
                //String outDateChinese = Utils.getChineseDate(df.format(patientSignIn.getSignOutDate()));
                String hourChinese = Utils.numberToChineseSimple(hf.format(signOutDate), false);
                hourChinese = formatChineseNumber(hourChinese, false);
                String minuteChinese = Utils.numberToChineseSimple(mf.format(signOutDate), false);
                minuteChinese = formatChineseNumber(minuteChinese, true);
                String outFullChinese = addLineBreak("出院" + hourChinese + "时" + minuteChinese + "分");
                dto.setSignOutDate(PatientSignInDtoConverter.toEchartValue(signOutDate, outFullChinese, weekDateList.get(0).toLocalDate()));
            }
        }

        for (PatientSignInDepartmentChange departmentChange : patientSignIn.getDepartmentChangeList()) {
            Integer daysBetween = Utils.findDaysBetween(patientSignIn.getSignInDate(), departmentChange.getWhenCreated());
            if (daysBetween / 7 == weekNumber) {
                Date departmentChangedDate = departmentChange.getWhenCreated();
                String hourChinese = Utils.numberToChineseSimple(hf.format(departmentChangedDate), false);
                hourChinese = formatChineseNumber(hourChinese, false);
                String minuteChinese = Utils.numberToChineseSimple(mf.format(departmentChangedDate), false);
                minuteChinese = formatChineseNumber(minuteChinese, true);
                String departmentChangeFullChinese = addLineBreak("转入" + hourChinese + "时" + minuteChinese + "分");
                dto.setDepartmentChangedDate(PatientSignInDtoConverter.toEchartValue(departmentChangedDate, departmentChangeFullChinese, weekDateList.get(0).toLocalDate()));
            }
        }
        dto.setWeekDateList(weekDateList);

        List<Integer> signInDaysList = new ArrayList<>();
        for (int i = 1; i <= 7; i++)
            signInDaysList.add(i + 7 * weekNumber);
        dto.setSignInDaysList(signInDaysList);

        List<EchartValue> mouthTempValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getMouthTemp() != null && t.getMouthTemp() > 0).collect(Collectors.toList()))
            mouthTempValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getMouthTemp().toString(), weekDateList.get(0).toLocalDate()));
        dto.setMouthTempValueList(mouthTempValueList);

        List<EchartValue> armpitTempValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getArmpitTemp() != null && t.getArmpitTemp() > 0).collect(Collectors.toList()))
            armpitTempValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getArmpitTemp().toString(), weekDateList.get(0).toLocalDate()));
        dto.setArmpitTempValueList(armpitTempValueList);

        List<EchartValue> earTempValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getEarTemp() != null && t.getEarTemp() > 0).collect(Collectors.toList()))
            earTempValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getEarTemp().toString(), weekDateList.get(0).toLocalDate()));
        dto.setEarTempValueList(earTempValueList);

        List<EchartValue> rectalTempValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getRectalTemp() != null && t.getRectalTemp() > 0).collect(Collectors.toList()))
            rectalTempValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getRectalTemp().toString(), weekDateList.get(0).toLocalDate()));
        dto.setRectalTempValueList(rectalTempValueList);

        List<EchartValue> pulseValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getPulse() != null && t.getPulse() > 0).collect(Collectors.toList()))
            pulseValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getPulse().toString(), weekDateList.get(0).toLocalDate()));
        dto.setPulseValueList(pulseValueList);

        List<EchartValue> heartBeatValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getHeartBeat() != null && t.getHeartBeat() > 0).collect(Collectors.toList()))
            heartBeatValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getHeartBeat().toString(), weekDateList.get(0).toLocalDate()));
        dto.setHeartBeatValueList(heartBeatValueList);

        List<EchartValue> breathValueList = new ArrayList<>();
        for (TempRecord tempRecord : tempRecordList.stream().filter(t -> t.getBreath() != null && t.getBreath() > 0).collect(Collectors.toList()))
            breathValueList.add(PatientSignInDtoConverter.toEchartValue(tempRecord.getRecordDate(), tempRecord.getBreath().toString(), weekDateList.get(0).toLocalDate()));
        dto.setBreathValueList(breathValueList);

        List<Double> numberOrBowels = new ArrayList<>();
        List<Double> inVolume = new ArrayList<>();
        List<String> urineVolume = new ArrayList<>();
        List<String> referenceList = new ArrayList<>();
        List<String> weightList = new ArrayList<>();
        List<String> allergyList = new ArrayList<>();
        List<String> bloodPressureList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Date startDate = Date.from(weekDateList.get(i).atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(weekDateList.get(i + 1).atZone(ZoneId.systemDefault()).toInstant());
            List<TempRecord> dayTempRecordList = tempRecordList.stream().filter(r -> (r.getRecordDate().before(endDate) && r.getRecordDate().after(startDate)) || r.getRecordDate().equals(startDate)).collect(Collectors.toList());
            dayTempRecordList.sort(Comparator.comparing(TempRecord::getRecordDate));
            List<Double> bowelsList = dayTempRecordList.stream().filter(r -> r.getBowels() != null).map(TempRecord::getBowels).collect(Collectors.toList());
            if (bowelsList.size() == 0)
                numberOrBowels.add(null);
            else
                numberOrBowels.add(bowelsList.stream().reduce((double) 0, Double::sum));
            //numberOrBowels.add(dayTempRecordList.stream().filter(r -> r.getBowels() != null).map(TempRecord::getBowels).reduce((double) 0, Double::sum));
            List<Double> inVolumeList = dayTempRecordList.stream().filter(r -> r.getInVolume() != null).map(TempRecord::getInVolume).collect(Collectors.toList());
            if (inVolumeList.size() == 0)
                inVolume.add(null);
            else
                inVolume.add(inVolumeList.stream().reduce((double) 0, Double::sum));

            List<Double> urineVolumeList = dayTempRecordList.stream().filter(r -> r.getUrineVolume() != null).map(TempRecord::getUrineVolume).collect(Collectors.toList());
            if (urineVolumeList.size() == 0) {
                List<String> urineVolumeListSelectionList = dayTempRecordList.stream().filter(r -> r.getUrineVolumeSelection() != null).map(u -> u.getUrineVolumeSelection().toString()).distinct().collect(Collectors.toList());
                String joinedUrineVolumeListSelectionList = org.apache.commons.lang.StringUtils.join(urineVolumeListSelectionList, ',');
                urineVolume.add(joinedUrineVolumeListSelectionList);
            } else
                urineVolume.add(urineVolumeList.stream().reduce((double) 0, Double::sum).toString());

            Character whiteSpace = ' ';
            // urineVolume.add(dayTempRecordList.stream().filter(r -> r.getUrineVolume() != null).map(TempRecord::getUrineVolume).reduce((double) 0, Double::sum));
            List<String> dayReferenceList = dayTempRecordList.stream().filter(r -> r.getReference() != null && !r.getReference().equals("")).map(TempRecord::getReference).collect(Collectors.toList());
            referenceList.add(StringUtils.join(dayReferenceList, whiteSpace));
            List<String> dayWeightList = dayTempRecordList.stream().filter(r -> r.getWeight() != null || (r.getCanNotStand() != null && r.getCanNotStand())).map(TempRecord::getWeightString).collect(Collectors.toList());

            weightList.add(StringUtils.join(dayWeightList, whiteSpace));
            List<String> dayAllergyList = dayTempRecordList.stream().filter(r -> r.getAllergy() != null && !r.getAllergy().equals("")).map(TempRecord::getAllergy).collect(Collectors.toList());
            allergyList.add(StringUtils.join(dayAllergyList, whiteSpace));

            List<String> dayBloodPressureList = dayTempRecordList.stream().filter(r -> !r.getBloodPressureString().equals("")).map(TempRecord::getBloodPressureString).collect(Collectors.toList());
            String bloodPressureString = StringUtils.join(dayBloodPressureList, whiteSpace);

            // bloodPressureString= ("999 &nbsp; &nbsp; 999");
            bloodPressureList.add(bloodPressureString);
        }
        dto.setBowelsNumberList(numberOrBowels);
        dto.setInVolumeList(inVolume);
        dto.setUrineVolumeList(urineVolume);
        dto.setReferenceList(referenceList);
        dto.setAllergyList(allergyList);
        dto.setWeightList(weightList);
        dto.setBloodPressureList(bloodPressureList);
        return dto;
    }

    private static String formatChineseNumber(String timeChinese, boolean keepFirstZero) {
        timeChinese = timeChinese.replace("十零", "十");
        timeChinese = timeChinese.replace("一十", "十");
        if (timeChinese.charAt(0) == '零' && !keepFirstZero)
            timeChinese = timeChinese.substring(1);

        return timeChinese;
    }

    static String addLineBreak(String string) {
        String lineBreakString = "";
        for (int i = 0; i < string.length(); i++) {
            String singleWord = string.substring(i, i + 1);
            lineBreakString += singleWord + "\n";
        }

        int paddingSpaceNumber = 10 - lineBreakString.replace("\n", "").length();
        for (int i = 0; i < paddingSpaceNumber; i++)
            lineBreakString += "\n";
        return lineBreakString;
    }

    public static EchartValue toEchartValue(Date recordedDate, String value, LocalDate weekStartDate) {
        EchartValue echartValue = new EchartValue();
        echartValue.setRecordedDate(recordedDate);
        echartValue.setValue(value);
        int xAxisIndex = 0;
        LocalDateTime localRecordedDate = LocalDateTime.ofInstant(recordedDate.toInstant(), ZoneId.systemDefault());
        int daysBetween = (int) ((localRecordedDate.toLocalDate().toEpochDay() - weekStartDate.toEpochDay()));
        xAxisIndex += daysBetween * 3600 * 24;
        xAxisIndex += localRecordedDate.getHour() * 3600;
        xAxisIndex += localRecordedDate.getMinute() * 60;
        xAxisIndex += localRecordedDate.getSecond();
        echartValue.setxAxisIndex(xAxisIndex);
        return echartValue;
    }

    public static List<MedicalRecordListDto> toMedicalRecordDtoList(List<MedicalRecord> medicalRecordList) {
        List<MedicalRecordListDto> medicalRecordDtoList = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordList) {
            MedicalRecordListDto dto = medicalRecord.toListDto();
            medicalRecordDtoList.add(dto);
        }
        return medicalRecordDtoList;
    }

    public static List<PatientMedicalRecordTypeDto> toPatientMedicalRecordTypeList(List<MedicalRecordType> medicalRecordTypeList, List<PatientMedicalRecordCount> medicalRecordCountList, UUID patientSignInId) {

        List<PatientMedicalRecordTypeDto> dtoList = new ArrayList<>();
        for (MedicalRecordType medicalRecordType : medicalRecordTypeList) {
            PatientMedicalRecordTypeDto dto = medicalRecordType.toPatientRecordDto();
            dto.setPatientSignInId(patientSignInId);
            Optional<PatientMedicalRecordCount> patientMedicalRecordCount = medicalRecordCountList.stream().filter(recType -> recType.getMedicalRecordTypeId().equals(medicalRecordType.getUuid())).findAny();
            dto.setRecordCount(0);
            patientMedicalRecordCount.ifPresent(medicalRecordSummary -> dto.setRecordCount(medicalRecordSummary.getRecordCount()));
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static List<MiniWardDto> toMiniWardList(List<Prescription> prescriptionList) {
        List<MiniWardDto> miniWardList = new ArrayList<>();
        List<Ward> wardList =  prescriptionList.stream().filter(p->p.getPatientSignIn().getCurrentBed() != null)
                .map(p -> p.getPatientSignIn().getCurrentBed().getWardRoom().getWard()).distinct()
                .sorted(comparing(Ward::getOrder)).collect(Collectors.toList());
        for (Ward ward : wardList) {
            List<Prescription> wardPrescriptionList = prescriptionList.stream().filter(p->p.getPatientSignIn().getCurrentBed() != null
                    && p.getPatientSignIn().getCurrentBed().getWardRoom().getWard() == ward).distinct().collect(Collectors.toList());
            MiniWardDto wardDto = ward.toMiniWardDto(wardPrescriptionList);
            miniWardList.add(wardDto);
        }
        return miniWardList;
    }


    public static List<MiniPrescriptionDto> toMiniPrescriptionList(List<Prescription> prescriptionList) {
        List<MiniPrescriptionDto> miniPrescriptionList = new ArrayList<>();
        for (Prescription prescription : prescriptionList)
            miniPrescriptionList.add(prescription.toMiniPrescriptionDto());
        return miniPrescriptionList;
    }
}
