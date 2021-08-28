package lukelin.his.service;

import lukelin.his.domain.entity.basic.codeEntity.DepartmentWarehouse;
import lukelin.his.domain.entity.basic.codeEntity.Supplier;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.domain.entity.basic.entity.Medicine;
import lukelin.his.domain.entity.inventory.item.ItemOrder;
import lukelin.his.domain.entity.inventory.item.ItemOrderLine;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrder;
import lukelin.his.domain.entity.inventory.medicine.MedicineOrderLine;
import lukelin.his.domain.enums.Basic.InventoryEntityType;
import lukelin.his.domain.enums.Inventory.OrderStatus;
import lukelin.his.system.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MockDataService extends BaseHisService{

    @Transactional
    public void generateMockMedicineOrder() throws ParseException {
        //List<Medicine>  allMedicineList = ebeanServer.find(Medicine.class).where().eq("enabled", true).findList();
        List<Medicine>  allMedicineList = ebeanServer.find(Medicine.class).where()
                //.eq("type.name", "中草药")
                .eq("enabled", true).findList();
        MedicineOrder order = new MedicineOrder();
        Supplier supplier = ebeanServer.find(Supplier.class).where()
                .eq("type", InventoryEntityType.medicine)
                .eq("name", "测试供应商")
                .findOne();
        order.setSupplier(supplier);
        order.setOrderDate(new Date());
        order.setOrderStatus(OrderStatus.created);
        DepartmentWarehouse pharmacy = this.findById(DepartmentWarehouse.class, "7ddc7447-f593-4deb-83fe-5da6c8d66397");
        order.setToWarehouse(pharmacy);
        order.setReturnOrder(false);
        order.setPaid(true);

        List<MedicineOrderLine> lineList = new ArrayList<>();
        for(Medicine medicine: allMedicineList)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            MedicineOrderLine line = new MedicineOrderLine();
            line.setMedicine(medicine);
            line.setQuantity(new BigDecimal("1000"));
            line.setCost(new BigDecimal("1"));
            line.setBatchText("批文001");
            line.setBatchNumber("批次001");
            line.setInvoiceNumber("发票001");
            line.setUom(medicine.getWarehouseUom());
            line.setExpireDate(sdf.parse("2029-12-31"));
            line.setManufacturerMedicine(medicine.getManufacturerMedicine());
            line.setMedicineSnapshot(medicine.findLatestSnapshot());
            lineList.add(line);
        }
        order.setLineList(lineList);

        ebeanServer.save(order);
        order = this.findById(MedicineOrder.class, order.getUuid());
        order.setOrderNumberCode(Utils.buildDisplayCode(order.getOrderNumber()));
        ebeanServer.update(order);
    }

    @Transactional
    public void generateMockItemOrder() throws ParseException {
        List<Item>  allItemList = ebeanServer.find(Item.class).where().eq("enabled", true).findList();
        ItemOrder order = new ItemOrder();
        Supplier supplier = ebeanServer.find(Supplier.class).where()
                .eq("type", InventoryEntityType.item)
                .eq("name", "物品供应商")
                .findOne();
        order.setSupplier(supplier);
        order.setOrderDate(new Date());
        order.setOrderStatus(OrderStatus.created);
        DepartmentWarehouse warehouse = this.findById(DepartmentWarehouse.class, "244ac7b8-c985-4186-89fd-615352ae9590");
        order.setToWarehouse(warehouse);
        order.setReturnOrder(false);
        order.setPaid(true);

        List<ItemOrderLine> lineList = new ArrayList<>();
        for(Item item: allItemList)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            ItemOrderLine line = new ItemOrderLine();
            line.setItem(item);
            line.setQuantity(new BigDecimal("1000"));
            line.setCost(new BigDecimal("1"));
            line.setBatchNumber("批次001");
            line.setInvoiceNumber("发票001");
            line.setUom(item.getWarehouseUom());
            line.setManufacturerItem(item.getManufacturerItem());
            line.setItemSnapshot(item.findLatestSnapshot());
            lineList.add(line);
        }
        order.setLineList(lineList);

        ebeanServer.save(order);
        order = this.findById(ItemOrder.class, order.getUuid());
        order.setOrderNumberCode(Utils.buildDisplayCode(order.getOrderNumber()));
        ebeanServer.update(order);
    }


}
