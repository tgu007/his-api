package lukelin.his.dto.yb_hy.Req;

public class DeleteMatchReq {
    private String fixmedins_code;

    private String fixmedins_hilist_id;

    private String list_type;

    private String med_list_codg;

    public String getFixmedins_code() {
        return fixmedins_code;
    }

    public void setFixmedins_code(String fixmedins_code) {
        this.fixmedins_code = fixmedins_code;
    }

    public String getFixmedins_hilist_id() {
        return fixmedins_hilist_id;
    }

    public void setFixmedins_hilist_id(String fixmedins_hilist_id) {
        this.fixmedins_hilist_id = fixmedins_hilist_id;
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
}
