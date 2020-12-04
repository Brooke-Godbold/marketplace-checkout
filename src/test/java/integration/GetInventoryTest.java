package integration;

import model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import utils.RestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GetInventoryTest extends BaseIntegrationTest {

    @Test
    public void canGetInventory() {
        ResponseEntity<ArrayList<Item>> inventoryResponse = RestUtils.getItemListResponse(restTemplate, port, RestUtils.INVENTORY);

        assertThat(inventoryResponse.getStatusCodeValue()).isEqualTo(200);

        checkoutController.getInventory().forEach(expectedItem -> {
            List<Item> matchedItems = inventoryResponse.getBody().stream()
                    .filter(actualItem -> actualItem.getProductCode().equals(expectedItem.getProductCode()))
                    .collect(Collectors.toList());
            assertThat(matchedItems.get(0).getPrice()).isEqualTo(expectedItem.getPrice());
            assertThat(matchedItems.get(0).getName()).isEqualTo(expectedItem.getName());
        });
    }

    @Test
    public void cannotPostToInventory() {
        ResponseEntity<ArrayList<Item>> inventoryResponse = RestUtils.postReturnString(restTemplate, port, RestUtils.INVENTORY, null);
        assertThat(inventoryResponse.getStatusCodeValue()).isEqualTo(405);
    }
}
