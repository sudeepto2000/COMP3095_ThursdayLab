package ca.gbc.orderservice;

import ca.gbc.orderservice.stub.InventoryClientStub;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
class OrderServiceApplicationTests {



    @Test
    void contextLoads() {
    }

    @Test void shouldSubmitOrder(){
        String submitOrderJson = """
                {
                    "skuCode": "samsung_tv_2024",
                    "price": 5000,
                    "quantity": 10
                }
                """;


        //week 10
        //Mock a call to inventory-service
        InventoryClientStub.stubInventoryCall("samsung_tv_2024",10);



    }



}
