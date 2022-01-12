package lukelin.his.dto.yb_hy.Req;

public class SearchCommonPram {
    private String updt_time;

    private Integer page_num;

    private Integer page_size;

    public String getUpdt_time() {
        return updt_time;
    }

    public void setUpdt_time(String updt_time) {
        this.updt_time = updt_time;
    }

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
