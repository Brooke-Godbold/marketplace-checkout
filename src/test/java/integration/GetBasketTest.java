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
        product.setProductCode(1);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        product.setProductCode(2);
        RestUtils.postReturnString(restTemplate, port, RestUtils.SCAN, product);

        ResponseEntity<ArrayList<Item>> basketResponse = RestUtils.getItemListResponse(restTemplate, port, RestUtils.BASKET);

        assertThat(basketResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(basketResponse.getBody().size()).isEqualTo(2);
        assertThat(
                basketResponse.getBody().stream().anyMatch(actualItem -> actualItem.getProductCode().equals(1))
        ).isTrue();
        assertThat(
                basketResponse.getBody().stream().anyMatch(actualItem -> actualItem.getProductCode().equals(2))
        ).isTrue();
    }

    @Test
    public void canGetNoItemsInBasket() {
        ResponseEntity<ArrayList<Item>> basketResponse = RestUtils.getItemListResponse(restTemplate, port, RestUtils.BASKET);
        assertThat(basketResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(basketResponse.getBody().size()).isEqualTo(0);
    }
}
