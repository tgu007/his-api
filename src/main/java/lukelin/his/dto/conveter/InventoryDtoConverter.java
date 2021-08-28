package lukelin.his.dto.conveter;

import lukelin.his.domain.entity.inventory.item.*;
import lukelin.his.domain.entity.inventory.medicine.*;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.patient_sign_in.PatientSignIn;
import lukelin.his.domain.entity.prescription.Prescription;
import lukelin.his.domain.enums.Basic.WarehouseType;
import lukelin.his.dto.Inventory.resp.NZTransferRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemOrderRequestListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemPartialOrderListRespDto;
import lukelin.his.dto.Inventory.resp.item.ItemTransferListRespDto;
import lukelin.his.dto.Inventory.resp.medicine.*;
import lukelin.his.dto.basic.resp.entity.ItemStockSummaryRespDto;
import lukelin.his.dto.basic.resp.entity.MedicineStockSummaryRespDto;
import lukelin.his.dto.signin.response.BaseWardPatientListRespDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryDtoConverter {
    public static List<ItemOrderRequestListRespDto> toItemOrderRequestListRespDto(List<ItemOrderRequest> requestList) {
        List<ItemOrderRequestListRespDto> dtoList = new ArrayList<>();
        for (ItemOrderRequest request : requestList)
            dtoList.add(request.toListDto());
        return dtoList;
    }

    public static List<MedicineOrderRequestListRespDto> toMedicineOrderRequestListRespDto(List<MedicineOrderRequest> requestList) {
        List<MedicineOrderRequestListRespDto> dtoList = new ArrayList<>();
        for (MedicineOrderRequest request : requestList)
            dtoList.add(request.toListDto());
        return dtoList;
    }


    public static List<ItemOrderListRespDto> toItemOrderListRespDto(List<ItemOrder> itemOrderList) {
        List<ItemOrderListRespDto> orderListRespDtoList = new ArrayList<>();
        for (ItemOrder itemOrder : itemOrderList)
            orderListRespDtoList.add(itemOrder.toListDto());
        return orderListRespDtoList;
    }

    public static List<ItemTransferListRespDto> toItemTransferListRespDto(List<ItemTransfer> itemTransferList) {
        List<ItemTransferListRespDto> transferDtoList = new ArrayList<>();
        for (ItemTransfer itemTransfer : itemTransferList)
            transferDtoList.add(itemTransfer.toListDto());
        return transferDtoList;
    }

    //返回所有物品，并带有库存信息
    public static List<ItemStockSummaryRespDto> toItemAggregatedStockRespDtoList(List<Item> itemList) {
        List<ItemStockSummaryRespDto> itemStockDtoList = new ArrayList<>();
        for (Item item : itemList)
            itemStockDtoList.add(item.toItemStockRespDto());
        return itemStockDtoList;
    }

    public static List<MedicineOrderListRespDto> toMedicineOrderListRespDto(List<MedicineOrder> medicineOrderList) {
        List<MedicineOrderListRespDto> orderListRespDtoList = new ArrayList<>();
        for (MedicineOrder medicineOrder : medicineOrderList)
            orderListRespDtoList.add(medicineOrder.toListDto());
        return orderListRespDtoList;
    }

    public static List<MedicineTransferListRespDto> toMedicineTransferListRespDto(List<MedicineTransfer> medicineTransferList) {
        List<MedicineTransferListRespDto> transferDtoList = new ArrayList<>();
        for (MedicineTransfer medicineTransfer : medicineTransferList)
            transferDtoList.add(medicineTransfer.toListDto());
        return transferDtoList;
    }

    public static List<MedicineStockSummaryRespDto> toMedicineAggregatedStockRespDtoList(List<Medicine> medicineList) {
        return toMedicineAggregatedStockRespDtoList(medicineList, false);
    }

    //返回所有药品，并带有库存信息
    public static List<MedicineStockSummaryRespDto> toMedicineAggregatedStockRespDtoList(List<Medicine> medicineList, Boolean setLastPeriod) {
        List<MedicineStockSummaryRespDto> medicineStockDtoList = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            MedicineStockSummaryRespDto summaryRespDto = medicine.toMedicineStockRespDto();
            //medicine.getStockList().stream().filter(st -> st.getWarehouse().getUuid().equals(summaryRespDto.ge))
            if (setLastPeriod)
                summaryRespDto.setLastPeriodUsage(medicine.getLastPeriodUsage());
            medicineStockDtoList.add(summaryRespDto);
        }
        return medicineStockDtoList;
    }

    public static List<BaseWardPatientListRespDto> toPrescriptionMedicineOrderLineListRespDto(List<PrescriptionMedicineOrderLine> orderLineList) {
        List<BaseWardPatientListRespDto> dtoList = new ArrayList<>();
        for (PrescriptionMedicineOrderLine line : orderLineList) {
            BaseWardPatientListRespDto dto = line.toDto();
            dto.setPatientGroupIndex((int) dtoList.stream().filter(existingDto -> existingDto.getPatientSignInId().equals(dto.getPatientSignInId())).count());
            dtoList.add(dto);
        }

        Comparator<BaseWardPatientListRespDto> wardComparator = Comparator.comparing(BaseWardPatientListRespDto::getBedOrder);
        Comparator<BaseWardPatientListRespDto> wardRoomComparator = Comparator.comparing(BaseWardPatientListRespDto::getRoomOrder);
        Comparator<BaseWardPatientListRespDto> bedComparator = Comparator.comparing(BaseWardPatientListRespDto::getWardOrder);
        Comparator<BaseWardPatientListRespDto> groupComparator = Comparator.comparing(BaseWardPatientListRespDto::getPatientGroupIndex);
        dtoList.sort(wardComparator.thenComparing(wardRoomComparator).thenComparing(bedComparator).thenComparing(groupComparator));
        return dtoList;
    }

    public static List<PrescriptionMedicineOrderMedicineSummaryRespDto> toPrescriptionMedicineOrderLineMedicineSummaryListRespDto(List<PrescriptionMedicineOrderLine> orderLineList) {
        List<PrescriptionMedicineOrderMedicineSummaryRespDto> dtoList = new ArrayList<>();
        for (PrescriptionMedicineOrderLine line : orderLineList) {
            Optional<PrescriptionMedicineOrderMedicineSummaryRespDto> OptionalLineSummaryDto = dtoList.stream().filter(l -> l.getMedicineId().equals(line.getMedicine().getUuid()) && l.getStatus() == line.getStatus()).findFirst();
            PrescriptionMedicineOrderMedicineSummaryRespDto lineSummaryDto = null;
            String patientName = line.getPatientName();
            if (OptionalLineSummaryDto.isPresent()) {
                lineSummaryDto = OptionalLineSummaryDto.get();
                if (!lineSummaryDto.getPatientNameList().contains(patientName))
                    lineSummaryDto.getPatientNameList().add(patientName);
                //lineSummaryDto.setPatientsName(lineSummaryDto.getPatientsName() + "," +patientName);
                //PrescriptionMedicineOrderLineCommonDto commonDto = lineSummaryDto.getOrderLineCommon();
                int existingQuantity = lineSummaryDto.getOrderQuantity();
                lineSummaryDto.setOrderQuantity(existingQuantity + line.getQuantity().intValue());
                //lineSummaryDto.setOrderQuantityInfo(lineSummaryDto.getOrderQuantity() + line.getMedicine().getDepartmentUom().getName());
                lineSummaryDto.setOrderQuantityInfo(line.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(lineSummaryDto.getOrderQuantity())));
            } else {
                lineSummaryDto = new PrescriptionMedicineOrderMedicineSummaryRespDto();
                List<String> patientNameList = new ArrayList<>();
                patientNameList.add(patientName);
                lineSummaryDto.setPatientNameList(patientNameList);
                lineSummaryDto.setMedicineId(line.getMedicine().getUuid());
                //lineSummaryDto.setPatientsName(patientName);
                lineSummaryDto.setOrderLineCommon(line.getPrescriptionMedicine().getPrescriptionChargeable().getPrescription().toOrderLineCommonDto(line.getMedicine()));
                lineSummaryDto.setStatus(line.getStatus());
                lineSummaryDto.setOrderQuantity(line.getQuantity().intValue());
                //lineSummaryDto.setOrderQuantityInfo(line.getQuantity().intValue() + line.getMedicine().getDepartmentUom().getName());
                lineSummaryDto.setOrderQuantityInfo(line.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(lineSummaryDto.getOrderQuantity())));
                dtoList.add(lineSummaryDto);
            }
        }
        return dtoList;
    }

    //for return order
    public static List<BaseWardPatientListRespDto> toPrescriptionMedicineOrderLinePatientSummaryListRespDto(List<PrescriptionMedicineOrderLine> orderLineList) {
        List<BaseWardPatientListRespDto> dtoList = new ArrayList<>();
        for (PrescriptionMedicineOrderLine line : orderLineList) {
            Optional<BaseWardPatientListRespDto> OptionalLineSummaryDto = dtoList.stream()
                    .filter(l -> l.getPatientSignInId().equals(line.getPatientSignIn().getUuid()) && ((PrescriptionMedicineOrderPatientSummaryRespDto) l).getMedicineId() == line.getMedicine().getUuid()).findFirst();
            PrescriptionMedicineOrderPatientSummaryRespDto lineSummaryDto = null;
            if (OptionalLineSummaryDto.isPresent()) {
                lineSummaryDto = (PrescriptionMedicineOrderPatientSummaryRespDto) OptionalLineSummaryDto.get();
                lineSummaryDto.setOrderQuantity(lineSummaryDto.getOrderQuantity() + line.getQuantity().intValue());
                lineSummaryDto.setOrderQuantityInfo(line.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(lineSummaryDto.getOrderQuantity())));
            } else {
                lineSummaryDto = new PrescriptionMedicineOrderPatientSummaryRespDto();
                lineSummaryDto.setMedicineId(line.getMedicine().getUuid());
                lineSummaryDto.setMedicineName(line.getMedicine().getName());
                lineSummaryDto.setPatientName(line.getPatientName());
                lineSummaryDto.setPatientSignInId(line.getPatientSignIn().getUuid());
                lineSummaryDto.setPharmacyModel(line.getMedicine().getDepartmentModel());
                lineSummaryDto.setPharmacyUom(line.getMedicine().getDepartmentUom().getName());
                List<PrescriptionMedicineOrderLineRespDto> orderLineDtoList = new ArrayList<>();
                lineSummaryDto.setOrderLineList(orderLineDtoList);
                lineSummaryDto.setOrderQuantity(line.getQuantity().intValue());
                lineSummaryDto.setOrderQuantityInfo(line.getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(lineSummaryDto.getOrderQuantity())));
                dtoList.add(lineSummaryDto);
            }
            lineSummaryDto.getOrderLineList().add(line.toDto());
        }
        return dtoList;
    }

    public static List<PrescriptionMedicineReturnOrderMedicineSummaryRespDto> toPrescriptionMedicineReturnOrderLineSummaryListRespDto(List<PrescriptionMedicineReturnOrderLine> returnOrderLineList) {
        List<PrescriptionMedicineReturnOrderMedicineSummaryRespDto> dtoList = new ArrayList<>();
        for (PrescriptionMedicineReturnOrderLine line : returnOrderLineList) {
            Optional<PrescriptionMedicineReturnOrderMedicineSummaryRespDto> OptionalLineSummaryDto = dtoList.stream()
                    .filter(l -> l.getMedicineId().equals(line.getOriginOrderLine().getMedicine().getUuid())).findFirst();
            PrescriptionMedicineReturnOrderMedicineSummaryRespDto lineSummaryDto = null;
            if (OptionalLineSummaryDto.isPresent()) {
                lineSummaryDto = OptionalLineSummaryDto.get();
                lineSummaryDto.setOrderQuantity(lineSummaryDto.getOrderQuantity() + line.getQuantity().intValue());
                lineSummaryDto.setOrderQuantityInfo(line.getOriginOrderLine().getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(lineSummaryDto.getOrderQuantity())));
            } else {
                lineSummaryDto = new PrescriptionMedicineReturnOrderMedicineSummaryRespDto();
                lineSummaryDto.setMedicineId(line.getOriginOrderLine().getMedicine().getUuid());
                lineSummaryDto.setMedicineName(line.getOriginOrderLine().getMedicine().getName());
                lineSummaryDto.setOrderQuantity(line.getQuantity().intValue());
                lineSummaryDto.setPharmacyModel(line.getOriginOrderLine().getMedicine().getDepartmentModel());
                lineSummaryDto.setPharmacyUom(line.getOriginOrderLine().getMedicine().getDepartmentUom().getName());
                lineSummaryDto.setOrderQuantityInfo(line.getOriginOrderLine().getMedicine().getDisplayQuantity(WarehouseType.pharmacy, new BigDecimal(lineSummaryDto.getOrderQuantity())));

                List<PrescriptionMedicineReturnOrderLineRespDto> orderLineDtoList = new ArrayList<>();
                lineSummaryDto.setOrderLineList(orderLineDtoList);
                dtoList.add(lineSummaryDto);
            }
            lineSummaryDto.getOrderLineList().add(line.toDto());
        }
        return dtoList;
    }


    public static List<NZTransferRespDto> toMedicineNZTransferListDto(List<MedicineOrderRequestLine> lineList) {
        List<NZTransferRespDto> transferList = new ArrayList<>();
        for (MedicineOrderRequestLine requestLine : lineList)
            transferList.add(requestLine.toNZTransferDto());
        return transferList;
    }

    public static List<NZTransferRespDto> toItemNZTransferListFromRequestLine(List<ItemOrderRequestLine> lineList) {
        List<NZTransferRespDto> transferList = new ArrayList<>();
        for (ItemOrderRequestLine requestLine : lineList)
            transferList.add(requestLine.toNZTransferDto());
        return transferList;
    }

    public static List<MedicinePartialOrderListRespDto> toMedicinePartialOrderListRespDto(List<MedicinePartialOrder> orderList) {
        List<MedicinePartialOrderListRespDto> dtoList = new ArrayList<>();
        for (MedicinePartialOrder order : orderList)
            dtoList.add(order.toListDto());
        return dtoList;
    }

    public static List<ItemPartialOrderListRespDto> toItemPartialOrderListRespDto(List<ItemPartialOrder> orderList) {
        List<ItemPartialOrderListRespDto> dtoList = new ArrayList<>();
        for (ItemPartialOrder order : orderList)
            dtoList.add(order.toListDto());
        return dtoList;
    }

    public static List<NZTransferRespDto> toItemNZTransferListFromOrderLine(List<ItemOrderLine> lineList) {
        List<NZTransferRespDto> transferList = new ArrayList<>();
        for (ItemOrderLine orderLine : lineList)
            transferList.add(orderLine.toNZTransferDto());
        return transferList;
    }

    public static List<NZTransferRespDto> toMedicineNZTransferListFromOrderLine(List<MedicineOrderLine> lineList) {
        List<NZTransferRespDto> transferList = new ArrayList<>();
        for (MedicineOrderLine orderLine : lineList)
            transferList.add(orderLine.toNZTransferDto());
        return transferList;
    }

    public static List<PrescriptionMedicineUsageRespDto> toPrescriptionMedicineUsageRespDto(List<PrescriptionMedicineOrderLine> orderLineList, UUID warehouseId) {
        List<PrescriptionMedicineUsageRespDto> respDtoList = new ArrayList<>();
        PrescriptionMedicineUsageRespDto respDto = null;
        for(PrescriptionMedicineOrderLine orderLine: orderLineList)
        {
            Optional<PrescriptionMedicineUsageRespDto> optionalDto  = respDtoList.stream().filter(rl -> rl.getMedicine().getUuid().equals(orderLine.getMedicine().getUuid())).findAny();
            if(optionalDto.isPresent()) {
                respDto = optionalDto.get();
                respDto.setUsageQuantity(respDto.getUsageQuantity().add(orderLine.getQuantity()));
            }
            else {
                respDto = new PrescriptionMedicineUsageRespDto();
                respDto.setMedicine(orderLine.getMedicine().toDto());
                respDto.setUsageQuantity(orderLine.getQuantity());
                respDto.setMedicineEntity(orderLine.getMedicine());
                respDtoList.add(respDto);
            }
        }

        for(PrescriptionMedicineUsageRespDto usageRespDto: respDtoList) {
            Medicine medicine = usageRespDto.getMedicineEntity();
            usageRespDto.setUsageQuantityInfo(usageRespDto.getMedicineEntity().getDisplayQuantity(WarehouseType.pharmacy, usageRespDto.getUsageQuantity()));
            Optional<CachedMedicineStock> optionalStock = medicine.getStockList().stream().filter(s -> s.getWarehouse().getUuid().equals(warehouseId)).findAny();
            if(optionalStock.isPresent())
            {
                usageRespDto.setStockQuantity(optionalStock.get().getMinUomQuantity());
                usageRespDto.setStockQuantityInfo(medicine.getDisplayQuantity(WarehouseType.pharmacy, usageRespDto.getStockQuantity()));
            }
            else
                usageRespDto.setStockQuantity(BigDecimal.ZERO);
        }
        return respDtoList;
    }
}
