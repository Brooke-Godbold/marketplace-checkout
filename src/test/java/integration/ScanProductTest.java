package integration;

import checkout.CheckoutService;
import checkout.InventoryService;
import checkout.PromotionService;
import controller.CheckoutController;
import model.Item;
import model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = { CheckoutController.class, CheckoutService.class, PromotionService.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        )
@EnableAutoConfiguration
public class ScanProductTest {

    @LocalServerPort
    private int port;

    @Autowired
    CheckoutController checkoutController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void CanScanProduct() {
        Product product = new Product();
        product.setProductCode(001);

        ResponseEntity scanResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/scan", product, String.class);
        assertThat(scanResponse.getBody()).isEqualTo("success");

        ResponseEntity<ArrayList<Item>> basketResponse = restTemplate.exchange(
                "http://localhost:" + port + "/basket",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Item>>() {}
        );
        assertThat(basketResponse.getBody().size()).isEqualTo(1);
        assertThat(basketResponse.getBody().get(0).getProductCode()).isEqualTo(1);
    }
}
