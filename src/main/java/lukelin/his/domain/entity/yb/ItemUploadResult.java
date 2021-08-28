package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Item;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "yb.item_upload_result")
public class ItemUploadResult extends BaseEntity {
    @Column(name = "server_code")
    private String serverCode;

    @Column(name = "upload_error")
    private String uploadError;

    @OneToOne()
    @JoinColumn(name = "item_id")
    private Item item;

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }

    public String getUploadError() {
        return uploadError;
    }

    public void setUploadError(String uploadError) {
        this.uploadError = uploadError;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
