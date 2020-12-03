package integration;

import model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import utils.RestUtils;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class GetInventoryTest extends BaseIntegrationTest {

    @Test
    public void canGetInventory() {
        ResponseEntity<ArrayList<Item>> inventoryResponse = RestUtils.getItemListResponse(restTemplate, port, RestUtils.INVENTORY);

        checkoutController.getInventory().forEach(expectedItem -> {
            //assertThat(inventoryResponse.getBody()).contains(expectedItem);
        });
    }
}
