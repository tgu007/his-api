package lukelin.his.dto.yb.resp;

import io.ebean.Ebean;
import lukelin.common.util.BeanUtils;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.yb.FeeDownload;
import lukelin.his.domain.entity.yb.FeeDownloadLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FeeDownloadInterfaceResp {
    private String jzxh;

    private UUID patientSignInId;

    private List<FeeDownloadLineInterfaceResp> fyxx;

    private Integer subrownum;

    public String getJzxh() {
        return jzxh;
    }

    public void setJzxh(String jzxh) {
        this.jzxh = jzxh;
    }

    public UUID getPatientSignInId() {
        return patientSignInId;
    }

    public void setPatientSignInId(UUID patientSignInId) {
        this.patientSignInId = patientSignInId;
    }

    public List<FeeDownloadLineInterfaceResp> getFyxx() {
        return fyxx;
    }

    public void setFyxx(List<FeeDownloadLineInterfaceResp> fyxx) {
        this.fyxx = fyxx;
    }

    public Integer getSubrownum() {
        return subrownum;
    }

    public void setSubrownum(Integer subrownum) {
        this.subrownum = subrownum;
    }

    public FeeDownload toEntity() {
        FeeDownload feeDownload = null;
        PatientSignIn patientSignIn = Ebean.find(PatientSignIn.class, this.patientSignInId);
        Optional<FeeDownload> optionalFeeDownload =
                Ebean.find(FeeDownload.class).where()
                        .eq("patientSignIn.uuid", patientSignIn.getUuid()).findOneOrEmpty();
        if (optionalFeeDownload.isPresent()) {
            feeDownload = optionalFeeDownload.get();
            feeDownload.getLineList().clear();
        } else {
            feeDownload = new FeeDownload();
            feeDownload.setPatientSignIn(patientSignIn);
            feeDownload.setJzxh(this.getJzxh());
        }

        feeDownload.setRowNumber(this.subrownum);
        List<FeeDownloadLine> lineList = new ArrayList<>();
        if (this.getFyxx() != null)
            for (FeeDownloadLineInterfaceResp lineDto : this.getFyxx())
                lineList.add(lineDto.toEntity());
        feeDownload.setLineList(lineList);
        return feeDownload;
    }
}
