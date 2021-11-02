package lukelin.his.dto.yb_hy.Req;

import java.util.Date;

public class MatchRequest {
    private String fixmedins_hilist_id;

    private String fixmedins_hilist_name;

    private String list_type;

    private String med_list_codg;

    private String begndate;

    private String endDate;

    public String getFixmedins_hilist_id() {
        return fixmedins_hilist_id;
    }

    public void setFixmedins_hilist_id(String fixmedins_hilist_id) {
        this.fixmedins_hilist_id = fixmedins_hilist_id;
    }

    public String getFixmedins_hilist_name() {
        return fixmedins_hilist_name;
    }

    public void setFixmedins_hilist_name(String fixmedins_hilist_name) {
        this.fixmedins_hilist_name = fixmedins_hilist_name;
    }

    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }

    public String getMed_list_codg() {
        return med_list_codg;
    }

    public void setMed_list_codg(String med_list_codg) {
        this.med_list_codg = med_list_codg;
    }

    public String getBegndate() {
        return begndate;
    }

    public void setBegndate(String begndate) {
        this.begndate = begndate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
