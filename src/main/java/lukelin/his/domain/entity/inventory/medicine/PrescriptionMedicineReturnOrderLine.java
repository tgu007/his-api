package lukelin.his.domain.entity.inventory.medicine;

import lukelin.common.springboot.dto.DtoConvertible;
import lukelin.common.springboot.dto.DtoUtils;
import lukelin.his.domain.Interfaces.Inventory.CachedTransactionInterface;
import lukelin.his.domain.entity.BaseEntity;
import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.domain.enums.Inventory.PrescriptionMedicineReturnOrderLineStatus;
import lukelin.his.domain.enums.YB.InventoryOrderType;
import lukelin.his.dto.Inventory.resp.medicine.PrescriptionMedicineReturnOrderLineRespDto;
import lukelin.his.dto.yb.inventory.req.OrderHeaderReq;
import lukelin.his.dto.yb.inventory.req.OrderLineDetailReq;
import lukelin.his.dto.yb.inventory.req.OrderLineReq;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "inventory.prescription_medicine_return_order_line")
public class PrescriptionMedicineReturnOrderLine extends BaseEntity implements DtoConvertible<PrescriptionMedicineReturnOrderLineRespDto> {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private PrescriptionMedicineReturnOrder order;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private PrescriptionMedicineReturnOrderLineStatus status;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "sign_in_code", nullable = false)
    private String patientSignInCode;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_order_line_id", nullable = false)
    private PrescriptionMedicineOrderLine originOrderLine;

    @Column(name = "medicine_search_code", nullable = false)
    private String medicineSearchCode;

    public String getMedicineSearchCode() {
        return medicineSearchCode;
    }

    public void setMedicineSearchCode(String medicineSearchCode) {
        this.medicineSearchCode = medicineSearchCode;
    }

    public PrescriptionMedicineReturnOrder getOrder() {
        return order;
    }

    public void setOrder(PrescriptionMedicineReturnOrder order) {
        this.order = order;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public PrescriptionMedicineReturnOrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionMedicineReturnOrderLineStatus status) {
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSignInCode() {
        return patientSignInCode;
    }

    public void setPatientSignInCode(String patientSignInCode) {
        this.patientSignInCode = patientSignInCode;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public PrescriptionMedicineOrderLine getOriginOrderLine() {
        return originOrderLine;
    }

    public void setOriginOrderLine(PrescriptionMedicineOrderLine originOrderLine) {
        this.originOrderLine = originOrderLine;
        this.setMedicineSearchCode(originOrderLine.getMedicineSearchCode());
    }

    @Override
    public PrescriptionMedicineReturnOrderLineRespDto toDto() {
        PrescriptionMedicineReturnOrderLineRespDto dto = DtoUtils.convertRawDto(this);
        dto.setOrderNumber(this.getOrder().getOrderNumberCode());
        dto.setProcessedDate(this.getOrder().getProcessedDate());
        dto.setOriginOrderLine(this.getOriginOrderLine().toDto());
        dto.setOrderQuantity(this.getOriginOrderLine().getQuantity().intValue());
        dto.setOrderQuantityInfo(this.getOriginOrderLine().getMedicine().getDisplayQuantity(WarehouseType.pharmacy, this.getQuantity()));
        return dto;
    }


    public OrderHeaderReq toYBOrderHeader(InventoryOrderType orderType) {
        DepartmentWarehouse defaultWareHouse = this.getOriginOrderLine().getPatientSignIn().getDepartmentTreatment().getDefaultPharmacy();
        OrderHeaderReq req = new OrderHeaderReq();
        req.setCYWDH(this.getUuid().toString());
        req.setKFBH(defaultWareHouse.getWarehouseUploaded().getServerCode());
        req.setYWDM(orderType.toString());
        req.setYWBM(defaultWareHouse.getDepartment().getName());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        req.setFSRQ(df.format(new Date()));
        req.setGYSMC("不适用");
        return req;

    }

    public OrderLineReq toYBOrderLineDto(List<CachedTransactionInterface> newTransactionList, boolean isOut) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        OrderLineReq req = new OrderLineReq();
        req.setCYWDH(this.getUuid().toString());
        List<OrderLineDetailReq> lineDetailList = new ArrayList<>();
        for (CachedTransactionInterface cachedTransactionInterface : newTransactionList)
        {
            CachedMedicineTransaction medicineTransaction = (CachedMedicineTransaction)cachedTransactionInterface;
            OrderLineDetailReq detailReq = medicineTransaction.toYBOrderLineDetailReq(df, isOut);
            lineDetailList.add(detailReq);
        }
        req.setYWMXLB(lineDetailList);
        return req;

    }
}
