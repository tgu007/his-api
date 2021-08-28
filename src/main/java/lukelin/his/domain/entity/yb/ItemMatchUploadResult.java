package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "yb.item_match_upload_result")
public class ItemMatchUploadResult extends BaseEntity{

    @Column(name = "upload_status", nullable = false)
    private String status;

    @Column(name = "upload_error")
    private String error;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
