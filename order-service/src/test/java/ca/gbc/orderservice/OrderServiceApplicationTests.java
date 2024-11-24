package ca.gbc.orderservice;

import io.restassured.RestAssured;
import ca.gbc.orderservice.stub.InventoryClientStub;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");
//            .withDatabaseName("order-service")
//            .withUsername("admin")
//            .withPassword("password");

    @LocalServerPort
    private Integer port;

    static {
        postgreSQLContainer.start();
//        System.out.println("PostgreSQL container started: " + postgreSQLContainer.isRunning());
    }

    @BeforeEach
    void setup(){
        RestAssured.baseURI = "http://localhost";
//        if(port != null){
//            RestAssured.port = port;
//            System.out.println("Using port: " + port);
//        } else {
//            throw new IllegalStateException("Port is not initialized!");
//        }
        RestAssured.port = port;
    }

    @Test void shouldSubmitOrder(){
        String submitOrderJson = """
                {
                    "orderNumber": "test1",
                    "skuCode": "samsung_tv_2024",
                    "price": 5000,
                    "quantity": 10
                }
                """;


        //week 10
        //Mock a call to inventory-service
        InventoryClientStub.stubInventoryCall("samsung_tv_2024",10);

        var responseBodyString = RestAssured.given()
                .contentType("application/json")
                .body(submitOrderJson)
                .when()
                .post("api/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();

        assertThat(responseBodyString, Matchers.is("Order placed successfully"));


    }



}
