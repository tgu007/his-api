package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb.fee_download_med_track")
public class FeeDownloadMedTrack extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "fee_line_id", nullable = false)
    private FeeDownloadLine feeDownloadLine;

    private String ypzsm;

    public FeeDownloadLine getFeeDownloadLine() {
        return feeDownloadLine;
    }

    public void setFeeDownloadLine(FeeDownloadLine feeDownloadLine) {
        this.feeDownloadLine = feeDownloadLine;
    }

    public String getYpzsm() {
        return ypzsm;
    }

    public void setYpzsm(String ypzsm) {
        this.ypzsm = ypzsm;
    }
}