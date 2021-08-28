package lukelin.his.dto.conveter;

import lukelin.his.domain.entity.basic.Dictionary;
import lukelin.his.domain.entity.basic.entity.Treatment;
import lukelin.his.domain.entity.basic.ward.Ward;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.PreDefinedPrescription;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.entity.prescription.PrescriptionCardSignature;
import lukelin.his.domain.entity.prescription.PrescriptionGroup;
import lukelin.his.domain.enums.Prescription.PrescriptionCardSignatureType;
import lukelin.his.domain.enums.Prescription.PrescriptionCardType;
import lukelin.his.dto.prescription.response.*;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;
import lukelin.his.system.Utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PrescriptionDtoConverter {
    public static List<PrescriptionListRespDto> toPrescriptionListRespDto(List<Prescription> prescriptionList) {
        List<PrescriptionListRespDto> PrescriptionListRespDtoList = new ArrayList<>();
        Map<UUID, String> groupColourMap = new HashMap<>();
        Integer groupIndex = 0;
        for (Prescription prescription : prescriptionList) {
            PrescriptionListRespDto listRespDto = prescription.toListRespDto();
            //设置组套显示颜色
            if (prescription.getPrescriptionGroup() != null) {
                if (groupColourMap.containsKey(prescription.getPrescriptionGroup().getUuid()))
                    listRespDto.setGroupColour(groupColourMap.get(prescription.getPrescriptionGroup().getUuid()));
                else {
                    String unUsedColour = Utils.getColourList().stream().filter(c -> !groupColourMap.containsValue(c)).findFirst().get();
                    listRespDto.setGroupColour(unUsedColour);
                    groupColourMap.put(prescription.getPrescriptionGroup().getUuid(), unUsedColour);
                    groupIndex++;
                }
                listRespDto.setGroupIndex(groupIndex);

            }
            PrescriptionListRespDtoList.add(listRespDto);
        }
        return PrescriptionListRespDtoList;
    }

    public static List<BaseWardPatientListRespDto> toChangedPrescriptionList(List<Ward> currentPatientList, List<Prescription> changedMedicinePrescriptionList) {
        List<BaseWardPatientListRespDto> dtoList = new ArrayList<>();
        for (Prescription prescription : changedMedicinePrescriptionList) {
            PrescriptionChangedListRespDto dto = prescription.toChangedListRespDto();
            dtoList.add(dto);
        }

        return PatientSignInDtoConverter.toWardPatientList(currentPatientList, dtoList);
    }

    public static List<BaseWardPatientListRespDto> toPendingExecutionPrescriptionList(List<Ward> currentPatientList, List<Prescription> pendingExecutionPrescriptionList) {
        List<BaseWardPatientListRespDto> dtoList = new ArrayList<>();
        for (Prescription prescription : pendingExecutionPrescriptionList) {
            PrescriptionExecutionListRespDto dto = prescription.toExecutionListRespDto();
            dtoList.add(dto);
        }
        return PatientSignInDtoConverter.toWardPatientList(currentPatientList, dtoList);
    }


    public static List<PrescriptionTreatmentNursingCardRespDto> toPatientTreatmentNursingCardList(List<Prescription> prescriptionList, List<PrescriptionCardSignature> signatureList, Date cardDate) {
        List<Treatment> treatmentList = prescriptionList.stream().map(p -> p.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment()).distinct().collect(Collectors.toList());
        List<PrescriptionTreatmentNursingCardRespDto> treatmentCardList = new ArrayList<>();
        for (Treatment treatment : treatmentList) {
            PrescriptionTreatmentNursingCardRespDto newTreatmentCard = new PrescriptionTreatmentNursingCardRespDto();
            newTreatmentCard.setTreatmentName(treatment.getName());
            newTreatmentCard.setTreatmentId(treatment.getUuid());
            treatmentCardList.add(newTreatmentCard);
            List<Prescription> patientPrescriptionList = prescriptionList.stream().filter(p -> p.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment() == treatment).collect(Collectors.toList());

            List<PrescriptionPatientTreatmentNursingCardRespDto> patientTreatmentCardList = new ArrayList<>();
            newTreatmentCard.setPatientList(patientTreatmentCardList);
            for (Prescription prescription : patientPrescriptionList) {
                PrescriptionPatientTreatmentNursingCardRespDto nursingCardRespDto = prescription.getPrescriptionChargeable().getPrescriptionTreatment().toPatientTreatmentCardDto();
                Date prescriptionStartDate = prescription.getStartDate();
                nursingCardRespDto.setFirstDay(false);
                if (prescription.executeOnPrescriptionStartDate()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    if (formatter.format(prescriptionStartDate).equals(formatter.format(cardDate)))
                        nursingCardRespDto.setFirstDay(true);
                }

                nursingCardRespDto.setSignatureOne(PrescriptionDtoConverter.tryFindSignature(signatureList, prescription.getPatientSignIn().getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 1, prescription.getUuid()));
                nursingCardRespDto.setSignatureTwo(PrescriptionDtoConverter.tryFindSignature(signatureList, prescription.getPatientSignIn().getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 2, prescription.getUuid()));
                nursingCardRespDto.setSignatureThree(PrescriptionDtoConverter.tryFindSignature(signatureList, prescription.getPatientSignIn().getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 3, prescription.getUuid()));
                nursingCardRespDto.setSignatureFour(PrescriptionDtoConverter.tryFindSignature(signatureList, prescription.getPatientSignIn().getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 4, prescription.getUuid()));
                patientTreatmentCardList.add(nursingCardRespDto);
            }
        }
        return treatmentCardList;
    }

    public static List<PrescriptionPatientNursingCardRespDto> toPatientNursingCardList(List<Prescription> prescriptionList, PrescriptionCardType prescriptionCardType, List<PrescriptionCardSignature> signatureList, Date cardDate) {
        List<PatientSignIn> patientSignInList = prescriptionList.stream().map(Prescription::getPatientSignIn).distinct().collect(Collectors.toList());
        Comparator<PatientSignIn> wardComparator = Comparator.comparing(p -> p.getCurrentBed().getWardRoom().getWard().getOrder());
        Comparator<PatientSignIn> wardRoomComparator = Comparator.comparing(p -> p.getCurrentBed().getWardRoom().getOrder());
        Comparator<PatientSignIn> bedComparator = Comparator.comparing(p -> p.getCurrentBed().getOrder());
        patientSignInList.sort(wardComparator.thenComparing(wardRoomComparator).thenComparing(bedComparator));

        List<UUID> groupIdList = new ArrayList<>();
        List<PrescriptionPatientNursingCardRespDto> patientNursingCardList = new ArrayList<>();
        Comparator<Prescription> groupComparator = Comparator.comparing(p -> p.getPrescriptionGroup().getUuid());


        for (PatientSignIn patientSignIn : patientSignInList) {
            PrescriptionPatientNursingCardRespDto patientNursingCard = PrescriptionDtoConverter.createNewPatientNursingCard(patientSignIn, prescriptionCardType);
            patientNursingCardList.add(patientNursingCard);
            patientNursingCard.setSignatureOne(PrescriptionDtoConverter.tryFindSignature(signatureList, patientSignIn.getUuid(), PrescriptionCardSignatureType.cardLevel, 1, null));
            patientNursingCard.setSignatureTwo(PrescriptionDtoConverter.tryFindSignature(signatureList, patientSignIn.getUuid(), PrescriptionCardSignatureType.cardLevel, 2, null));


            List<Prescription> patientPrescriptionList = prescriptionList.stream().filter(p -> p.getPrescriptionGroup() == null && p.getPatientSignIn() == patientSignIn).collect(Collectors.toList());
            List<Prescription> groupedPrescriptionList = prescriptionList.stream().filter(p -> p.getPrescriptionGroup() != null && p.getPatientSignIn() == patientSignIn).collect(Collectors.toList());
            groupedPrescriptionList.sort(groupComparator);
            patientPrescriptionList.addAll(groupedPrescriptionList);

            Map<UUID, String> groupColourMap = new HashMap<>();
            Integer groupIndex = 0;
            groupIdList.clear();
            for (Prescription prescription : patientPrescriptionList) {
                PrescriptionNursingCardRespDto nursingCardRespDto = prescription.toNursingCardDto();
                if (nursingCardRespDto.getGroupId() != null) {
                    UUID groupId = nursingCardRespDto.getGroupId();
                    if (groupIdList.contains(groupId)) {
                        nursingCardRespDto.setHideColumn(true);
                        PrescriptionNursingCardRespDto firstDtoInGroup = patientNursingCard.getNursingCardRespDtoList().stream().filter(dto -> dto.getGroupId() == groupId).findFirst().get();
                        firstDtoInGroup.setRowSpan(firstDtoInGroup.getRowSpan() + 1);
                    } else
                        groupIdList.add(groupId);

                    if (groupColourMap.containsKey(prescription.getPrescriptionGroup().getUuid()))
                        nursingCardRespDto.setGroupColour(groupColourMap.get(prescription.getPrescriptionGroup().getUuid()));
                    else {
                        String unUsedColour = Utils.getColourList().stream().filter(c -> !groupColourMap.containsValue(c)).findFirst().get();
                        nursingCardRespDto.setGroupColour(unUsedColour);
                        groupColourMap.put(prescription.getPrescriptionGroup().getUuid(), unUsedColour);
                        groupIndex++;
                    }
                    nursingCardRespDto.setGroupIndex(groupIndex);

                }
                Date prescriptionStartDate = prescription.getStartDate();
                nursingCardRespDto.setFirstDay(false);
                if (prescription.executeOnPrescriptionStartDate()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    if (formatter.format(prescriptionStartDate).equals(formatter.format(cardDate)))
                        nursingCardRespDto.setFirstDay(true);
                }


                nursingCardRespDto.setSignatureOne(PrescriptionDtoConverter.tryFindSignature(signatureList, patientSignIn.getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 1, prescription.getUuid()));
                nursingCardRespDto.setSignatureTwo(PrescriptionDtoConverter.tryFindSignature(signatureList, patientSignIn.getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 2, prescription.getUuid()));
                nursingCardRespDto.setSignatureThree(PrescriptionDtoConverter.tryFindSignature(signatureList, patientSignIn.getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 3, prescription.getUuid()));
                nursingCardRespDto.setSignatureFour(PrescriptionDtoConverter.tryFindSignature(signatureList, patientSignIn.getUuid(), PrescriptionCardSignatureType.prescriptionLevel, 4, prescription.getUuid()));
                patientNursingCard.getNursingCardRespDtoList().add(nursingCardRespDto);
            }
        }
        return patientNursingCardList;
    }

    private static PrescriptionSignatureRespDto tryFindSignature(List<PrescriptionCardSignature> signatureList, UUID patientSignInId, PrescriptionCardSignatureType signatureType, Integer sequence, UUID prescriptionId) {
        Optional<PrescriptionCardSignature> optionalSignature = signatureList.stream()
                .filter(s -> s.getPatientSignIn().getUuid().equals(patientSignInId) && s.getCardSignatureType() == signatureType && s.getSequence() == sequence
                        && (prescriptionId == null || s.getPrescription().getUuid().equals(prescriptionId))
                ).findAny();
        return optionalSignature.map(PrescriptionCardSignature::toDto).orElse(null);
    }

    public static List<PrescriptionPatientNursingCardRespDto> toBottleCardList(List<Prescription> prescriptionList, PrescriptionCardType prescriptionCardType) {
        Comparator<Prescription> wardComparator = Comparator.comparing(p -> p.getPatientSignIn().getCurrentBed().getWardRoom().getWard().getOrder());
        Comparator<Prescription> wardRoomComparator = Comparator.comparing(p -> p.getPatientSignIn().getCurrentBed().getWardRoom().getOrder());
        Comparator<Prescription> bedComparator = Comparator.comparing(p -> p.getPatientSignIn().getCurrentBed().getOrder());
        prescriptionList.sort(wardComparator.thenComparing(wardRoomComparator).thenComparing(bedComparator));

        List<PrescriptionPatientNursingCardRespDto> patientNursingCardList = new ArrayList<>();
        List<Prescription> unGroupedPrescriptionList = prescriptionList.stream().filter(p -> p.getPrescriptionGroup() == null).collect(Collectors.toList());
        List<Prescription> groupedPrescriptionList = prescriptionList.stream().filter(p -> p.getPrescriptionGroup() != null).collect(Collectors.toList());
        List<PrescriptionGroup> prescriptionGroupList = groupedPrescriptionList.stream().map(Prescription::getPrescriptionGroup).distinct().collect(Collectors.toList());

        for (PrescriptionGroup prescriptionGroup : prescriptionGroupList) {
            List<Prescription> sameGroupPrescriptionList = groupedPrescriptionList.stream().filter(p -> p.getPrescriptionGroup() == prescriptionGroup
            ).collect(Collectors.toList());
            PrescriptionDtoConverter.createDropBottleCardList(sameGroupPrescriptionList, prescriptionCardType, patientNursingCardList);
        }

        for (Prescription prescription : unGroupedPrescriptionList) {
            List<Prescription> sameGroupPrescriptionList = new ArrayList<>();
            sameGroupPrescriptionList.add(prescription);
            PrescriptionDtoConverter.createDropBottleCardList(sameGroupPrescriptionList, prescriptionCardType, patientNursingCardList);
        }

        return patientNursingCardList;
    }

    public static List<PrescriptionPatientNursingCardRespDto> toLabBottleCardList(List<Prescription> prescriptionList, PrescriptionCardType prescriptionCardType) {
        Comparator<Prescription> wardComparator = Comparator.comparing(p -> p.getPatientSignIn().getCurrentBed().getWardRoom().getWard().getOrder());
        Comparator<Prescription> wardRoomComparator = Comparator.comparing(p -> p.getPatientSignIn().getCurrentBed().getWardRoom().getOrder());
        Comparator<Prescription> bedComparator = Comparator.comparing(p -> p.getPatientSignIn().getCurrentBed().getOrder());
        prescriptionList.sort(wardComparator.thenComparing(wardRoomComparator).thenComparing(bedComparator));

        List<PrescriptionPatientNursingCardRespDto> patientNursingCardList = new ArrayList<>();
        List<PatientSignIn> patientSignInList = prescriptionList.stream().map(Prescription::getPatientSignIn).distinct().collect(Collectors.toList());
        List<Prescription> noSampleTypePrescription = new ArrayList<>();
        for (PatientSignIn patientSignIn : patientSignInList) {
            List<Prescription> patientPrescriptionList = prescriptionList.stream().filter(p -> p.getPatientSignIn() == patientSignIn).collect(Collectors.toList());
            List<Dictionary> sampleTypeList = patientPrescriptionList.stream().map(p -> p.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getLabSampleType()).distinct().collect(Collectors.toList());
            for (Dictionary sampleType : sampleTypeList) {
                List<Prescription> sameSampleList = patientPrescriptionList.stream().filter(p -> p.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getLabSampleType() == sampleType).collect(Collectors.toList());
                if (sampleType == null) {
                    for (Prescription prescription : sameSampleList) {
                        noSampleTypePrescription.clear();
                        noSampleTypePrescription.add(prescription);
                        PrescriptionDtoConverter.createDropBottleCardList(noSampleTypePrescription, prescriptionCardType, patientNursingCardList);
                    }
                } else {
                    List<Dictionary> sameLabTreatmentTypeList = sameSampleList.stream().map(p -> p.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getLabTreatmentType()).distinct().collect(Collectors.toList());
                    for (Dictionary labTreatmentType : sameLabTreatmentTypeList) {
                        List<Prescription> sameColourPrescriptionList = sameSampleList.stream().filter(p -> p.getPrescriptionChargeable().getPrescriptionTreatment().getTreatment().getLabTreatmentType() == labTreatmentType).collect(Collectors.toList());
                        PrescriptionDtoConverter.createDropBottleCardList(sameColourPrescriptionList, prescriptionCardType, patientNursingCardList);
                    }

                }
            }
        }
        return patientNursingCardList;
    }

    private static void createDropBottleCardList(List<Prescription> sameGroupPrescriptionList, PrescriptionCardType prescriptionCardType, List<PrescriptionPatientNursingCardRespDto> patientMedicineCardList) {
        Prescription firstPrescription = sameGroupPrescriptionList.get(0);
        for (int i = 0; i < firstPrescription.getFrequency().getFrequencyInfo().getDayQuantity(); i++) {
            PrescriptionPatientNursingCardRespDto patientMedicineCard = PrescriptionDtoConverter.createNewPatientNursingCard(firstPrescription.getPatientSignIn(), prescriptionCardType);
            for (Prescription prescription : sameGroupPrescriptionList) {
                PrescriptionNursingCardRespDto medicineCardRespDto = prescription.toNursingCardDto();
                patientMedicineCard.getNursingCardRespDtoList().add(medicineCardRespDto);
            }

            if (prescriptionCardType == PrescriptionCardType.labBottle && patientMedicineCard.getNursingCardRespDtoList().size() > 0) {
                patientMedicineCard.setBottleColour(patientMedicineCard.getNursingCardRespDtoList().get(0).getBottleColour());
                patientMedicineCard.setSampleType(patientMedicineCard.getNursingCardRespDtoList().get(0).getSampleType());
            }
            patientMedicineCardList.add(patientMedicineCard);
        }
    }

    private static PrescriptionPatientNursingCardRespDto createNewPatientNursingCard(PatientSignIn patientSignIn, PrescriptionCardType prescriptionCardType) {
        PrescriptionPatientNursingCardRespDto patientNursingCard = patientSignIn.toPatientNursingCardDto();
        patientNursingCard.setCardTypeName(prescriptionCardType.getDescription());
        patientNursingCard.setNursingCardRespDtoList(new ArrayList<>());
        return patientNursingCard;
    }


    public static List<PrescriptionDescriptionListRespDto> toPrescriptionDescriptionListRespDto(List<Prescription> prescriptionList) {
        List<PrescriptionDescriptionListRespDto> dtoList = new ArrayList<>();
        for (Prescription prescription : prescriptionList)
            dtoList.add(prescription.toPrescriptionDescriptionDto());
        return dtoList;
    }

    public static List<PreDefinedPrescriptionListDto> toPreDefinedPrescriptionGroupDto(List<PreDefinedPrescription> preDefinedList) {
        List<PreDefinedPrescriptionListDto> dtoList = new ArrayList<>();
        for (PreDefinedPrescription preDefinedGroup : preDefinedList)
            dtoList.add(preDefinedGroup.toListDto());
        return dtoList;
    }


}
