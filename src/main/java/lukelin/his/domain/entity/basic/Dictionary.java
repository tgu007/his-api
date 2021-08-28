package lukelin.his.domain.entity.basic;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.entity.yb.PreSettlement;
import lukelin.his.domain.entity.yb.drg.SignOutReasonMapping;
import lukelin.his.dto.basic.resp.setup.DictionaryDto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "basic.dic_dictionary")
public class Dictionary implements DtoConvertible<DictionaryDto> {
    public Dictionary(Integer id) {
        this.id = id;
    }

    public Dictionary() {
    }

    @Id
    private Integer id;

    @Column(nullable = false, length = 50)
    private String code;

    private String name;

    @Column(nullable = false, name = "is_default")
    private boolean defaultSelection;

    @Column(name = "sequence")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private DictionaryGroup group;

    @OneToOne(mappedBy = "dictionaryFrequency")
    private Frequency frequencyInfo;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Dictionary parent;

    @Column(name = "search_code")
    private String searchCode;

    @Column(name = "extra_info")
    private String extraInfo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "hisSignOutReason")
    private SignOutReasonMapping drgSignOutReasonMapping;

    public SignOutReasonMapping getDrgSignOutReasonMapping() {
        return drgSignOutReasonMapping;
    }

    public void setDrgSignOutReasonMapping(SignOutReasonMapping drgSignOutReasonMapping) {
        this.drgSignOutReasonMapping = drgSignOutReasonMapping;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public Dictionary getParent() {
        return parent;
    }

    public void setParent(Dictionary parent) {
        this.parent = parent;
    }

    public Frequency getFrequencyInfo() {
        return frequencyInfo;
    }

    public void setFrequencyInfo(Frequency frequencyInfo) {
        this.frequencyInfo = frequencyInfo;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public boolean isDefaultSelection() {
        return defaultSelection;
    }

    public void setDefaultSelection(boolean defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DictionaryGroup getGroup() {
        return group;
    }

    public void setGroup(DictionaryGroup group) {
        this.group = group;
    }


    @Override
    public DictionaryDto toDto() {
        DictionaryDto responseDto = DtoUtils.convertRawDto(this);
        if (this.getParent() != null)
            responseDto.setParent(this.getParent().toDto());
        if (this.getFrequencyInfo() != null)
            responseDto.setFrequency(this.getFrequencyInfo().getDayQuantity());
        if(this.getDrgSignOutReasonMapping() != null)
            responseDto.setDrgSignOutReasonId(this.getDrgSignOutReasonMapping().getSignOutReason().getUuid());
        return responseDto;
    }
}
