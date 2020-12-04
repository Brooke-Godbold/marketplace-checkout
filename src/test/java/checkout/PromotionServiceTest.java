package checkout;

import model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.PromotionUtils;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionServiceTest {

    PromotionService promotionService = new PromotionService();

    @Test
    void calculateItemPromotionsTravelCardTrue() {
        ArrayList<Boolean> promotions = new ArrayList<Boolean>(){{
            add(PromotionUtils.TWO_OR_MORE_TRAVEL_CARD_HOLDERS);
        }};

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(PromotionUtils.TRAVEL_CARD_HOLDER_CODE, "Test Item 1", 15.5));
        items.add(new Item(PromotionUtils.TRAVEL_CARD_HOLDER_CODE, "Test Item 2", 23.4));
        items.add(new Item(3, "Test Item 3", 13.9));

        items = promotionService.calculateItemPromotions(items, promotions);
        for(Item item : items) {
            if (item.getProductCode().equals(PromotionUtils.TRAVEL_CARD_HOLDER_CODE)) {
                assertThat(item.getPrice()).isEqualTo(8.50);
            } else {
                assertThat(item.getPrice()).isNotEqualTo(8.50);
            }
        }
    }

    @Test
    void calculateItemPromotionsTravelCardFalse() {
        ArrayList<Boolean> promotions = new ArrayList<>();

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(PromotionUtils.TRAVEL_CARD_HOLDER_CODE, "Test Item 1", 15.5));
        items.add(new Item(PromotionUtils.TRAVEL_CARD_HOLDER_CODE, "Test Item 2", 15.5));
        items.add(new Item(3, "Test Item 3", 13.9));

        items = promotionService.calculateItemPromotions(items, promotions);
        for(Item item : items) {
            if (item.getProductCode().equals(PromotionUtils.TRAVEL_CARD_HOLDER_CODE)) {
                assertThat(item.getPrice()).isEqualTo(15.5);
            } else {
                assertThat(item.getPrice()).isNotEqualTo(8.50);
            }
        }
    }

    private static Stream<Arguments> providePriceArgs10PercentTrue() {
        return Stream.of(
                Arguments.of(60, 60),
                Arguments.of(60.1, 54.09),
                Arguments.of(10000, 9000),
                Arguments.of(1, 1),
                Arguments.of(0, 0)
        );
    }

    @ParameterizedTest(name = "{index} Price of {0} is evaluated to {1} with 10 Percent Off")
    @MethodSource("providePriceArgs10PercentTrue")
    void calculatePricePromotionsTenPercentTrue(double price, double expected) {
        ArrayList<Boolean> promotions = new ArrayList<Boolean>(){{
            add(PromotionUtils.TEN_PERCENT_OFF);
        }};

        assertThat(promotionService.calculatePricePromotions(price, promotions)).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{index} Price of {0} is evaluated to {0} with no Promotion")
    @ValueSource(doubles = {60, 60.1, 10000, 1, 0})
    void calculatePricePromotionsTenPercentFalse(double price) {
        ArrayList<Boolean> promotions = new ArrayList<>();

        assertThat(promotionService.calculatePricePromotions(price, promotions)).isEqualTo(price);
        assertThat(promotionService.calculatePricePromotions(price, promotions)).isEqualTo(price);
        assertThat(promotionService.calculatePricePromotions(price, promotions)).isEqualTo(price);
        assertThat(promotionService.calculatePricePromotions(price, promotions)).isEqualTo(price);
        assertThat(promotionService.calculatePricePromotions(price, promotions)).isEqualTo(price);
    }
}