package lukelin.his.dto.signin.response;

import io.ebeaninternal.server.lib.util.Str;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TempRecordChartRespDto {
    private List<LocalDateTime> weekDateList;

    private List<Integer> signInDaysList;

    private List<EchartValue> mouthTempValueList;

    private List<EchartValue> armpitTempValueList;

    private List<EchartValue> earTempValueList;

    private List<EchartValue> rectalTempValueList;

    private List<EchartValue> pulseValueList;

    private List<EchartValue> heartBeatValueList;

    private List<EchartValue> breathValueList;

    private List<Double> inVolumeList;

    private List<Double> bowelsNumberList;

    private List<String> urineVolumeList;

    private List<String> referenceList;

    private List<String> bloodPressureList;

    private List<String> weightList;

    private List<String> allergyList;

    private EchartValue signInDate;

    private EchartValue signOutDate;

    private EchartValue departmentChangedDate;

    public EchartValue getDepartmentChangedDate() {
        return departmentChangedDate;
    }

    public void setDepartmentChangedDate(EchartValue departmentChangedDate) {
        this.departmentChangedDate = departmentChangedDate;
    }

    public EchartValue getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(EchartValue signInDate) {
        this.signInDate = signInDate;
    }

    public EchartValue getSignOutDate() {
        return signOutDate;
    }

    public void setSignOutDate(EchartValue signOutDate) {
        this.signOutDate = signOutDate;
    }

    public List<Double> getInVolumeList() {
        return inVolumeList;
    }

    public void setInVolumeList(List<Double> inVolumeList) {
        this.inVolumeList = inVolumeList;
    }

    public List<Double> getBowelsNumberList() {
        return bowelsNumberList;
    }

    public void setBowelsNumberList(List<Double> bowelsNumberList) {
        this.bowelsNumberList = bowelsNumberList;
    }

    public List<String> getUrineVolumeList() {
        return urineVolumeList;
    }

    public void setUrineVolumeList(List<String> urineVolumeList) {
        this.urineVolumeList = urineVolumeList;
    }

    public List<String> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<String> referenceList) {
        this.referenceList = referenceList;
    }

    public List<String> getBloodPressureList() {
        return bloodPressureList;
    }

    public void setBloodPressureList(List<String> bloodPressureList) {
        this.bloodPressureList = bloodPressureList;
    }

    public List<String> getWeightList() {
        return weightList;
    }

    public void setWeightList(List<String> weightList) {
        this.weightList = weightList;
    }

    public List<String> getAllergyList() {
        return allergyList;
    }

    public void setAllergyList(List<String> allergyList) {
        this.allergyList = allergyList;
    }

    public List<EchartValue> getArmpitTempValueList() {
        return armpitTempValueList;
    }

    public void setArmpitTempValueList(List<EchartValue> armpitTempValueList) {
        this.armpitTempValueList = armpitTempValueList;
    }

    public List<EchartValue> getEarTempValueList() {
        return earTempValueList;
    }

    public void setEarTempValueList(List<EchartValue> earTempValueList) {
        this.earTempValueList = earTempValueList;
    }

    public List<EchartValue> getRectalTempValueList() {
        return rectalTempValueList;
    }

    public void setRectalTempValueList(List<EchartValue> rectalTempValueList) {
        this.rectalTempValueList = rectalTempValueList;
    }

    public List<EchartValue> getPulseValueList() {
        return pulseValueList;
    }

    public void setPulseValueList(List<EchartValue> pulseValueList) {
        this.pulseValueList = pulseValueList;
    }

    public List<EchartValue> getHeartBeatValueList() {
        return heartBeatValueList;
    }

    public void setHeartBeatValueList(List<EchartValue> heartBeatValueList) {
        this.heartBeatValueList = heartBeatValueList;
    }

    public List<EchartValue> getBreathValueList() {
        return breathValueList;
    }

    public void setBreathValueList(List<EchartValue> breathValueList) {
        this.breathValueList = breathValueList;
    }

    public List<EchartValue> getMouthTempValueList() {
        return mouthTempValueList;
    }

    public void setMouthTempValueList(List<EchartValue> mouthTempValueList) {
        this.mouthTempValueList = mouthTempValueList;
    }

    public List<Integer> getSignInDaysList() {
        return signInDaysList;
    }

    public void setSignInDaysList(List<Integer> signInDaysList) {
        this.signInDaysList = signInDaysList;
    }

    public List<LocalDateTime> getWeekDateList() {
        return weekDateList;
    }

    public void setWeekDateList(List<LocalDateTime> weekDateList) {
        this.weekDateList = weekDateList;
    }

    public List<LocalDateTime> getWeekDateColumnList() {
        List<LocalDateTime> weekDateColumnList = this.weekDateList.stream().collect(Collectors.toList());
        if (weekDateColumnList.size() > 7)
            weekDateColumnList.remove(7);
        return weekDateColumnList;
    }

}
