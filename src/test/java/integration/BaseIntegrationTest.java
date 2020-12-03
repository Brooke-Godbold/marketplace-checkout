package integration;

import checkout.BasketService;
import checkout.CheckoutService;
import checkout.InventoryService;
import checkout.PromotionService;
import controller.CheckoutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(
        classes = { CheckoutController.class, CheckoutService.class, PromotionService.class, InventoryService.class, BasketService.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration
public class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected CheckoutController checkoutController;

    @Autowired
    protected TestRestTemplate restTemplate;

}
