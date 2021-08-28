package lukelin.his.api;

import lukelin.common.springboot.controller.BaseController;
import lukelin.his.service.MockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/mock")
public class MockDataApi extends BaseController {
    @Autowired
    private MockDataService mockDataService;

    @PostMapping("order/medicine")
    public void mockMedicineOrder() throws ParseException {

        this.mockDataService.generateMockMedicineOrder();
    }

    @PostMapping("order/item")
    public void mockItemOrder() throws ParseException {

        this.mockDataService.generateMockItemOrder();
    }
}
