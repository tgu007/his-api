package lukelin.his.domain.entity.basic;

import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.prescription.Prescription;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "basic.frequency")
public class Frequency extends BaseEntity {
    @Column(name = "day_interval")
    private Integer dayInterval;

    @Column(name = "day_quantity")
    private Integer dayQuantity;

    @Column(name = "day_range")
    private Integer dayRange;

    @Column(name = "range_limit")
    private Integer rangeLimit;

    @OneToOne()
    @JoinColumn(name = "frequency_dic_id")
    private Dictionary dictionaryFrequency;

    public Integer getDayRange() {
        return dayRange;
    }

    public void setDayRange(Integer dayRange) {
        this.dayRange = dayRange;
    }

    public Integer getRangeLimit() {
        return rangeLimit;
    }

    public void setRangeLimit(Integer rangeLimit) {
        this.rangeLimit = rangeLimit;
    }

    public Integer getDayInterval() {
        return dayInterval;
    }

    public void setDayInterval(Integer dayInterval) {
        this.dayInterval = dayInterval;
    }

    public Integer getDayQuantity() {
        return dayQuantity;
    }

    public void setDayQuantity(Integer dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public Dictionary getDictionaryFrequency() {
        return dictionaryFrequency;
    }

    public void setDictionaryFrequency(Dictionary dictionaryFrequency) {
        this.dictionaryFrequency = dictionaryFrequency;
    }
}
