package lukelin.his.domain.Interfaces;

import io.ebean.Ebean;
import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentTreatment;
import lukelin.his.domain.entity.basic.ward.WardRoomBed;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.patient_sign_in.PatientSignInBed;

import java.util.*;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

public interface SnapshotSignInInfoInterface {
    void setWardDepartment(DepartmentTreatment wardDepartment);

    void setWardDepartmentName(String wardDepartmentName);

    void setBed(WardRoomBed bed);

    void setWardName(String wardName);


    default void snapshotSignIn(UUID patientSignInId) {
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, patientSignInId);
        WardRoomBed wardRoomBed = patientSignIn.getCurrentBed();
        if (wardRoomBed == null) {
            List<PatientSignInBed> bedList = patientSignIn.getBedList().stream()
                    .sorted(Comparator.comparing(PatientSignInBed::getWhenCreated).reversed()).collect(Collectors.toList());
            if (bedList.size() < 1)
                throw new ApiValidationException("patientSignIn.error.assignBedFirst");
            wardRoomBed = bedList.get(0).getWardRoomBed();
        }
        this.setWardDepartment(patientSignIn.getDepartmentTreatment());
        this.setWardDepartmentName(patientSignIn.getDepartmentTreatment().getDepartment().getName());
        this.setBed(wardRoomBed);
        this.setWardName(wardRoomBed.getWardRoom().getWard().getName());
    }

}
