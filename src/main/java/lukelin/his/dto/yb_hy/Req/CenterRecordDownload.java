package lukelin.his.dto.yb_hy.Req;

public class CenterRecordDownload {
    private String updt_time;

    private String page_num;

    private String page_size;

    private String insu_admdvs;

    private String list_type;

    public String getUpdt_time() {
        return updt_time;
    }

    public void setUpdt_time(String updt_time) {
        this.updt_time = updt_time;
    }

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getInsu_admdvs() {
        return insu_admdvs;
    }

    public void setInsu_admdvs(String insu_admdvs) {
        this.insu_admdvs = insu_admdvs;
    }

    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }
}
