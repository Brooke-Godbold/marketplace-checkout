package integration;

import model.Item;
import model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import utils.RestUtils;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBasketTest extends BaseIntegrationTest {

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void canGetCorrectItemsInBasket() {
        Product product = new Product();
        product.setProductCode(001);

        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity<ArrayList<Item>> basketResponse = RestUtils.getItemListResponse(restTemplate, port, RestUtils.BASKET);
        assertThat(basketResponse.getBody().size()).isEqualTo(1);
        assertThat(basketResponse.getBody().get(0).getProductCode()).isEqualTo(1);

        //TODO: Test with more than 1
    }

    @Test
    public void canGetNoItemsInBasket() {
        ResponseEntity<ArrayList<Item>> basketResponse = RestUtils.getItemListResponse(restTemplate, port, RestUtils.BASKET);
        assertThat(basketResponse.getBody().size()).isEqualTo(0);
    }
}
