package checkout;

import model.Item;
import org.junit.jupiter.api.Test;
import utils.PromotionUtils;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionServiceTest {

    PromotionService promotionService = new PromotionService();

    @Test
    void calculateItemPromotions() {
        ArrayList<Boolean> promotions = new ArrayList<Boolean>(){{
            add(PromotionUtils.TWO_OR_MORE_TRAVEL_CARD_HOLDERS);
        }};

        PromotionUtils.TRAVEL_CARD_HOLDER_CODE = 9;

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(9, "Test Item 1", 15.5));
        items.add(new Item(9, "Test Item 2", 23.4));
        items.add(new Item(3, "Test Item 3", 13.9));

        items = promotionService.calculateItemPromotions(items, promotions);
        for(Item item : items) {
            if (item.getProductCode().equals(9)) {
                assertThat(item.getPrice()).isEqualTo(8.50);
            } else {
                assertThat(item.getPrice()).isNotEqualTo(8.50);
            }
        }
    }

    @Test
    void calculatePricePromotionsTenPercentTrue() {
        ArrayList<Boolean> promotions = new ArrayList<Boolean>(){{
            add(PromotionUtils.TEN_PERCENT_OFF);
        }};

        assertThat(promotionService.calculatePricePromotions(60, promotions)).isEqualTo(60);
        assertThat(promotionService.calculatePricePromotions(60.1, promotions)).isEqualTo(54.09);
        assertThat(promotionService.calculatePricePromotions(10000, promotions)).isEqualTo(9000);
        assertThat(promotionService.calculatePricePromotions(1, promotions)).isEqualTo(1);
        assertThat(promotionService.calculatePricePromotions(0, promotions)).isEqualTo(0);
    }

    @Test
    void calculatePricePromotionsTenPercentFalse() {
        ArrayList<Boolean> promotions = new ArrayList<>();

        assertThat(promotionService.calculatePricePromotions(60, promotions)).isEqualTo(60);
        assertThat(promotionService.calculatePricePromotions(60.1, promotions)).isEqualTo(60.1);
        assertThat(promotionService.calculatePricePromotions(10000, promotions)).isEqualTo(10000);
        assertThat(promotionService.calculatePricePromotions(1, promotions)).isEqualTo(1);
        assertThat(promotionService.calculatePricePromotions(0, promotions)).isEqualTo(0);
    }
}