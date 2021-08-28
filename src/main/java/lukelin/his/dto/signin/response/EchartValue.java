package lukelin.his.dto.signin.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EchartValue {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordedDate;

    private String value;

    private long xAxisIndex;

    public long getxAxisIndex() {
        return xAxisIndex;
    }

    public void setxAxisIndex(long xAxisIndex) {
        this.xAxisIndex = xAxisIndex;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
