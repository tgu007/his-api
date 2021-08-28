package lukelin.his.domain.entity.yb;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.entity.Item;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "yb.item_match_download")
public class ItemMatchDownload extends BaseEntity {
    @Column(name = "match_status", nullable = false)
    private String status;

    @Column(name = "error")
    private String error;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "match_reference")
    private String reference;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
